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
package org.databene.contiperf.junit;

import org.databene.contiperf.ExecutionLogger;

/**
 * Helper class for testing ExecutionLogger handling.<br><br>
 * Created: 22.05.2010 19:01:22
 * @since 1.05
 * @author Volker Bergmann
 */
@SuppressWarnings("deprecation")
public class ExecutionTestLogger implements ExecutionLogger {
	
	final int id;
	int invocations;
	static ExecutionTestLogger latestInstance;
	
	public ExecutionTestLogger(int id) {
	    this.id = id;
	    latestInstance = this;
    }

	public synchronized void logInvocation(String id, int latency, long startTime) {
		invocations++;
    }

	public void logSummary(String id, long elapsedTime, long invocationCount, long startTime) {
		// empty implementation
    }

}
