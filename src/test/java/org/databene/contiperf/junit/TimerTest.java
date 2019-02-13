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

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.timer.ConstantTimer;
import org.databene.contiperf.timer.RandomTimer;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests initialization and application of timers.<br><br>
 * Created: 06.04.2012 17:57:32
 * @since 2.1.0
 * @author Volker Bergmann
 */
public class TimerTest {

	@Rule public ContiPerfRule rule = new ContiPerfRule();
	
	private long recentMillis = -1;

	@Test
	@PerfTest(invocations = 6, timer = ConstantTimer.class, timerParams = { 200 })
	public void testConstant() {
		long currentMillis = System.currentTimeMillis();
		System.out.println("testConstant()");
		if (recentMillis != -1) {
			long elapsedMillis = currentMillis - recentMillis;
			assertTrue("expected a delay of at least 180 ms, but measured " + elapsedMillis + " ms", elapsedMillis > 180);
			assertTrue("expected a delay of at most 220 ms, but measured " + elapsedMillis + " ms", elapsedMillis < 220);
		}
		recentMillis = currentMillis;
	}
	
	
	
	private long randomStartMillis = -1;

	@Test
	@PerfTest(invocations = 20, threads = 3, rampUp = 1000, timer = RandomTimer.class, timerParams = { 200, 400 })
	public void testRandom() {
		long currentMillis = System.currentTimeMillis();
		if (randomStartMillis == -1)
			randomStartMillis = currentMillis;
		long offset = currentMillis - randomStartMillis;
		System.out.println("testRandom(" + Thread.currentThread().getName() + ", " + offset + ")");
	}
	
}
