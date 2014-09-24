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

import org.databene.contiperf.ExecutionConfig;
import org.databene.contiperf.PerformanceRequirement;
import org.databene.stat.LatencyCounter;

/**
 * Abstract parent class for {@link ReportModule}s, which provides 
 * {@link ReportContext} handling and empty implementations of the 
 * other {@link ReportModule} methods.<br/>
 * <br/>
 * Created: 16.01.2011 08:07:21
 * @since 2.0.0
 * @author Volker Bergmann
 */
public abstract class AbstractReportModule implements ReportModule {

	protected ReportContext context;
	
	public void setContext(ReportContext context) {
		this.context = context;
	}

	public String getReportReferenceLabel(String serviceId) {
		return null;
	}
	
	public String getReportReference(String serviceId) {
		return null;
	}

	public void starting(String serviceId) {
		// ignored
	}
	
	public void invoked(String serviceId, int latency, long startTime) {
		// ignored
	}
	
	/** implements backwards-compatibility of inheritors of older versions of this class */
	public void completed(String serviceId, LatencyCounter[] counters, ExecutionConfig executionConfig, PerformanceRequirement requirement) {
		completed(serviceId, counters, requirement);
	}

	/** 
	 * Implements a method which has been removed from the interface but implemented by children 
	 * of this class, probably using the Override annotation, which would cause compiler errors if 
	 * the method were removed.
	 */
	public void completed(String serviceId, LatencyCounter[] counters, PerformanceRequirement requirement) {
		// ignored
	}
	
}
