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
package org.databene.contiperf.log;

import org.databene.contiperf.ExecutionLogger;
import org.databene.contiperf.report.ConsoleReportModule;

/**
 * {@link ExecutionLogger} implementation which writes the execution log to the console.<br><br>
 * Created: 12.10.09 08:13:06
 * @since 1.0
 * @author Volker Bergmann
 * @deprecated replaced with {@link ConsoleReportModule}
 */
@Deprecated
public class ConsoleExecutionLogger implements ExecutionLogger {

    public void logSummary(String id, long elapsedTime, long invocationCount, long startTime) {
	    System.out.println(id + ',' + elapsedTime + ',' + invocationCount + ',' + 1000000);
    }

	public void logInvocation(String id, int latency, long startTime) {
	    System.out.println(id + ',' + latency + ',' + 1000000);
    }

}
