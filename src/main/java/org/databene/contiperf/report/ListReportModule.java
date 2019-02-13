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

import java.util.ArrayList;
import java.util.List;

import org.databene.contiperf.ExecutionConfig;
import org.databene.contiperf.PerformanceRequirement;
import org.databene.stat.LatencyCounter;

/**
 * {@link ReportModule} that stores all invocation information in {@link List}s.<br><br>
 * Created: 16.01.2011 14:36:48
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class ListReportModule extends AbstractReportModule {
	
	private List<InvocationLog> invocations;
	private List<InvocationSummary> summaries;
	
	public ListReportModule() {
		this.invocations = new ArrayList<InvocationLog>();
		this.summaries = new ArrayList<InvocationSummary>();
    }

	@Override
	public void invoked(String id, int latency, long startTime) {
	    invocations.add(new InvocationLog(id, latency, startTime));
    }

	@Override
	public void completed(String id, LatencyCounter[] counters, ExecutionConfig executionConfig, PerformanceRequirement requirement) {
	    summaries.add(new InvocationSummary(id, counters[0].duration(), counters[0].sampleCount(), counters[0].getStartTime()));
    }

	public List<InvocationLog> getInvocations() {
		return invocations;
	}
	
	public List<InvocationSummary> getSummaries() {
		return summaries;
	}
	
}
