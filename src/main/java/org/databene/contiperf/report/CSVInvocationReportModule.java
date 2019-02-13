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
package org.databene.contiperf.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.databene.contiperf.ExecutionConfig;
import org.databene.contiperf.PerformanceRequirement;
import org.databene.stat.LatencyCounter;

/**
 * {@link ReportModule} that creates a CSV file with one line per invocation, 
 * which reports the measured latency in the first column and the start time in the 
 * second one.<br><br>
 * Created: 16.01.2011 17:05:11
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class CSVInvocationReportModule extends AbstractReportModule {
	
	private static final String FILE_SUFFIX = ".inv.csv";
	
	private PrintWriter out;
	
	@Override
	public String getReportReferenceLabel(String serviceId) {
		return "Invocations as CSV";
	}
	
	@Override
	public String getReportReference(String serviceId) {
		return (serviceId != null ? filename(serviceId) : null);
	}

	@Override
	public void starting(String serviceId) {
		createFile(serviceId);
	}
	
	@Override
	public synchronized void invoked(String serviceId, int latency, long startTime) {
		out.print(latency);
		out.print(',');
		out.println(startTime);
	}

	@Override
	public void completed(String serviceId, LatencyCounter[] counters, ExecutionConfig executionConfig, PerformanceRequirement requirement) {
		if (out != null)
			out.close();
	}
	
	private void createFile(String serviceId) {
		try {
			String filename = filename(serviceId);
			out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			out.println("latency,startTimeNanos");
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	private String filename(String serviceId) {
		return context.getReportFolder() + File.separator + serviceId + FILE_SUFFIX;
	}

}
