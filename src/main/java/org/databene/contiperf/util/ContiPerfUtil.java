/*
 * Copyright (C) 2011-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.databene.contiperf.util;

import java.io.Closeable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.databene.contiperf.Clock;
import org.databene.contiperf.ExecutionConfig;
import org.databene.contiperf.PercentileRequirement;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.PerfTestConfigurationError;
import org.databene.contiperf.PerfTestException;
import org.databene.contiperf.PerfTestExecutionError;
import org.databene.contiperf.PerformanceRequirement;
import org.databene.contiperf.Required;
import org.databene.contiperf.clock.SystemClock;

/**
 * Provides I/O utility methods.<br><br>
 * Created: 18.10.09 07:43:54
 * @since 1.0
 * @author Volker Bergmann
 */
public class ContiPerfUtil {

	public static void close(Closeable resource) {
	    if (resource != null) {
	    	try {
	    		resource.close();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
    }

	public static PerfTestException executionError(Throwable e) {
		Throwable result = e;
		if (result instanceof InvocationTargetException)
			result = result.getCause();
		if (result instanceof PerfTestException)
			return (PerfTestException) result;
		else
			return new PerfTestExecutionError(result);
    }

	public static ExecutionConfig mapPerfTestAnnotation(PerfTest annotation) {
		if (annotation != null)
			return new ExecutionConfig(annotation.invocations(), annotation.threads(), 
					annotation.duration(), clocks(annotation), annotation.rampUp(), annotation.warmUp(), 
					annotation.cancelOnViolation(), 
					annotation.timer(), annotation.timerParams() /*, annotation.timeout()*/);
		else
			return null;
    }

	private static Clock[] clocks(PerfTest annotation) {
		Class<? extends Clock>[] clockClasses = annotation.clocks();
		if (clockClasses.length == 0)
			return new Clock[] { new SystemClock() };
		Clock[] clocks = new Clock[clockClasses.length];
		for (int i = 0; i < clockClasses.length; i++)
			clocks[i] = clock(clockClasses[i]);
		return clocks;
	}

	private static Clock clock(Class<? extends Clock> clockClass) {
		try {
			return clockClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Error creating clock " + clockClass.getName(), e);
		}
	}

	public static PerformanceRequirement mapRequired(Required annotation) {
	    if (annotation == null)
	    	return null;
	    int throughput = annotation.throughput();

		int average = annotation.average();
		int max = annotation.max();
		int totalTime = annotation.totalTime();
		
		List<PercentileRequirement> percTmp = new ArrayList<PercentileRequirement>();
		int median = annotation.median();
		if (median > 0)
			percTmp.add(new PercentileRequirement(50, median));
		int percentile90 = annotation.percentile90();
		if (percentile90 > 0)
			percTmp.add(new PercentileRequirement(90, percentile90));
		int percentile95 = annotation.percentile95();
		if (percentile95 > 0)
			percTmp.add(new PercentileRequirement(95, percentile95));
		int percentile99 = annotation.percentile99();
		if (percentile99 > 0)
			percTmp.add(new PercentileRequirement(99, percentile99));

		PercentileRequirement[] customPercs = parsePercentiles(annotation.percentiles());
		for (PercentileRequirement percentile : customPercs)
			percTmp.add(percentile);
		PercentileRequirement[] percs = new PercentileRequirement[percTmp.size()];
		percTmp.toArray(percs);
		return new PerformanceRequirement(average, max, totalTime, percs, throughput);
    }

	public static PercentileRequirement[] parsePercentiles(String percentilesSpec) {
		if (percentilesSpec == null || percentilesSpec.length() == 0)
			return new PercentileRequirement[0];
		String[] assignments = percentilesSpec.split(",");
		PercentileRequirement[] reqs = new PercentileRequirement[assignments.length];
		for (int i = 0; i < assignments.length; i++)
			reqs[i] = parsePercentile(assignments[i]);
	    return reqs;
    }

	private static PercentileRequirement parsePercentile(String assignment) {
	    String[] parts = assignment.split(":");
	    if (parts.length != 2)
	    	throw new PerfTestConfigurationError("Ilegal percentile syntax: " + assignment);
	    int base  = Integer.parseInt(parts[0].trim());
	    int limit = Integer.parseInt(parts[1].trim());
		return new PercentileRequirement(base, limit);
    }
	
}
