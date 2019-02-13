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
 * Replaces the {@link org.databene.contiperf.ExecutionLogger} interface of ContiPerf 1 and adds 
 * context access and inter-module referencing features.<br><br>
 * Created: 16.01.2011 07:44:56
 * @since 2.0.0
 * @author Volker Bergmann
 */
public interface ReportModule {
	void setContext(ReportContext context);
	String getReportReferenceLabel(String serviceId);
	String getReportReference(String serviceId);
	
	void starting(String serviceId);
	void invoked(String serviceId, int latency, long startTime);
	public void completed(String serviceId, LatencyCounter[] counters, ExecutionConfig executionConfig, PerformanceRequirement requirement);
}
