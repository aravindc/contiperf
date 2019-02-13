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

import static org.junit.Assert.*;

import java.util.List;

import org.databene.contiperf.Clock;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.clock.AbstractClock;
import org.databene.contiperf.report.InvocationLog;
import org.databene.contiperf.report.ListReportModule;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests the usage of custom {@link Clock}s.<br><br>
 * Created: 24.05.2012 07:36:24
 * @since 2.2.0
 * @author Volker Bergmann
 */
public class CustomClockTest {
	
	@Rule public ContiPerfRule rule = new ContiPerfRule(new ListReportModule());
	
	@Test
	@PerfTest(invocations = 10, clocks = { ConstantClock.class })
	public void test() throws InterruptedException {
		Thread.sleep(50);
	}
	
	public static class ConstantClock extends AbstractClock {
		public ConstantClock() {
			super("constantClock");
		}

		public long getTime() {
			return 35;
		}
	}
	
	@After
	public void verify() {
		ListReportModule report = rule.getContext().getReportModule(ListReportModule.class);
		List<InvocationLog> invocations = report.getInvocations();
		assertEquals(10, invocations.size());
		for (InvocationLog log : invocations)
			assertEquals( 0, log.latency);
	}
	
}
