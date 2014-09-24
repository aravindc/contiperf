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
import org.databene.contiperf.ExecutionLogger;
import org.databene.contiperf.PerformanceRequirement;
import org.databene.stat.LatencyCounter;

/**
 * Adapter class which makes implementors of the old {@link ExecutionLogger} interface 
 * available in ContiPerf 2.<br/><br/> 
 * If you are migrating to ContiPerf, usages of the predefined 
 * ContiPerf {@link ExecutionLogger}s should be replaced with their {@link ReportModule}
 * counterpart. For example, if the old version was
 * <pre>
 *     @Rule public ContiPerfRule = new ContiPerfRule(new ConsoleExecutionLogger());
 * </pre>
 * the new version would be
 * <pre>
 *     @Rule public ContiPerfRule = new ContiPerfRule(new ConsoleReportModule());
 * </pre>
 * <br/>
 * Custom ExecutionLogger implementations still can be used by wrapping them with a {@link LoggerModuleAdapter}.
 * If the old version was
 * <pre>
 *     @Rule public ContiPerfRule = new ContiPerfRule(new MyCustomLogger());
 * </pre>
 * the new version would be
 * <pre>
 *     @Rule public ContiPerfRule = new ContiPerfRule(new LoggerModuleAdapter(new MyCustomLogger()));
 * </pre>
 * <br/><br/>
 * Created: 16.01.2011 08:06:47
 * @since 2.0.0
 * @author Volker Bergmann
 */
@SuppressWarnings("deprecation")
public class LoggerModuleAdapter extends AbstractReportModule {
	
	protected ExecutionLogger logger;
	
	public LoggerModuleAdapter(ExecutionLogger logger) {
		this.logger = logger;
	}

	@Override
	public void invoked(String serviceId, int latency, long startTime) {
		logger.logInvocation(serviceId, latency, startTime);
	}

	@Override
	public void completed(String serviceId, LatencyCounter[] counters, ExecutionConfig executionConfig, PerformanceRequirement requirement) {
		logger.logSummary(serviceId, counters[0].duration(), counters[0].sampleCount(), counters[0].getStartTime());
	}

	public ExecutionLogger getLogger() {
		return logger;
	}
	
}
