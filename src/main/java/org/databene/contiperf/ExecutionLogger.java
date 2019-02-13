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
package org.databene.contiperf;

import org.databene.contiperf.report.LoggerModuleAdapter;
import org.databene.contiperf.report.ReportModule;

/**
 * Deprecated Observer interface for ContiPerf 1.x.<br><br>
 * Created: 12.10.2009 08:11:23
 * @since 1.0
 * @author Volker Bergmann
 * @deprecated Replaced with {@link ReportModule}. 
 * When using a predefined ExecutionLogger, replace it with the corresponding ReportModule.
 * If the old version was
 * <pre>
 *     @Rule public ContiPerfRule = new ContiPerfRule(new ConsoleExecutionLogger());
 * </pre>
 * the new version would be
 * <pre>
 *     @Rule public ContiPerfRule = new ContiPerfRule(new ConsoleReportModule());
 * </pre>
 * Custom ExecutionLogger implementations still can be used by wrapping them with a {@link LoggerModuleAdapter}.
 * If the old version was
 * <pre>
 *     @Rule public ContiPerfRule = new ContiPerfRule(new MyCustomLogger());
 * </pre>
 * the new version would be
 * <pre>
 *     @Rule public ContiPerfRule = new ContiPerfRule(new LoggerModuleAdapter(new MyCustomLogger()));
 * </pre>
 */
@Deprecated
public interface ExecutionLogger {
	void logInvocation(String id, int latency, long startTime);
	void logSummary(String id, long elapsedTime, long invocationCount, long startTime);
}
