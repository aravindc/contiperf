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

import org.databene.contiperf.Clock;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.clock.CpuClock;
import org.databene.contiperf.clock.SystemClock;
import org.databene.contiperf.clock.UserClock;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests the usage of multiple {@link Clock}s.<br><br>
 * Created: 24.05.2012 09:30:45
 * @since 2.2.0
 * @author Volker Bergmann
 */
public class MultiClockTest {
	
	@Rule public ContiPerfRule rule = new ContiPerfRule();
	
	@Test
	@PerfTest(invocations = 10, clocks = { SystemClock.class, UserClock.class, CpuClock.class })
	@Required(throughput = 2, totalTime = 10000, max = 1000, 
			average = 300, median = 301, percentiles = "55:302,77:303", percentile90 = 304)
	public void testMultipleClocks() throws Exception {
		Thread.sleep(200);
	}
	
}
