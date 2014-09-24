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

import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link ReportModule} implementation that counts the number of invocations.<br/><br/>
 * Created: 16.01.2011 16:03:10
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class InvocationCountingReportModule extends AbstractReportModule {

	AtomicLong invocationCount = new AtomicLong();
	
	@Override
	public void invoked(String serviceId, int latency, long startTime) {
		invocationCount.incrementAndGet();
	}

	public long getInvocationCount() {
		return invocationCount.get();
	}

}
