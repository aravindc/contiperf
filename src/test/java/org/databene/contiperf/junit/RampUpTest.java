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

import static org.junit.Assert.assertTrue;

import org.databene.contiperf.PerfTest;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests the ramp-up behavior of ContiPerf.<br/><br/>
 * Created: 06.04.2012 16:27:37
 * @since 2.1.0
 * @author Volker Bergmann
 */
public class RampUpTest {

	@Rule public ContiPerfRule rule = new ContiPerfRule();
	
	
	
	private static long firstInvCountMillis = -1;
	private static long lastInvCountMillis = -1;
	
	@Test
	@PerfTest(invocations = 3, threads = 3, rampUp = 500)
	public void testRampUp_count() throws InterruptedException {
		long currentTimeMillis = System.currentTimeMillis();
		if (firstInvCountMillis == -1)
			firstInvCountMillis = currentTimeMillis;
		lastInvCountMillis = currentTimeMillis;
		System.out.println("testRampUp_count()");
		Thread.sleep(2000);
	}

	@AfterClass
	public static void verifyRampUp_count() {
		String message = "expected an accumulated ramp-up and execution time of at least 980 ms, " +
				"but measured " + (lastInvCountMillis - firstInvCountMillis) + " ms";
		assertTrue(message, lastInvCountMillis - firstInvCountMillis > 980);
	}
	
	
	
	private static long firstInvDurationMillis = -1;
	private static long lastInvDurationMillis = -1;
	
	@Test
	@PerfTest(duration = 1000, threads = 3, rampUp = 500)
	public void testRampUp_duration() throws InterruptedException {
		long currentTimeMillis = System.currentTimeMillis();
		if (firstInvDurationMillis == -1)
			firstInvDurationMillis = currentTimeMillis;
		lastInvDurationMillis = currentTimeMillis;
		System.out.println("testRampUp_duration()");
		Thread.sleep(300);
	}

	@AfterClass
	public static void verifyRampUp_duration() {
		String message = "expected an accumulated ramp-up and execution time of at least 1800 ms, " +
				"but measured " + (lastInvDurationMillis - firstInvDurationMillis) + " ms";
		assertTrue(message, lastInvDurationMillis - firstInvDurationMillis > 1800);
	}
	
}
