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
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests the {@link ParallelRunner}.<br/><br/>
 * Created: 07.04.2012 17:36:35
 * @since 2.1.0
 * @author Volker Bergmann
 */
@RunWith(ParallelRunner.class)
public class ParallelRunnerTest {
	
	@Rule public ContiPerfRule rule = new ContiPerfRule();
	
	private static volatile long test1First = -1;
	private static volatile long test1Last = -1;
	
	private static volatile long test2First = -1;
	private static volatile long test2Last = -1;
	
	@Test
	@PerfTest(duration = 2000, threads = 3, timer = ConstantTimer.class, timerParams = { 1200 })
	public void test1() throws Exception {
		long currentTime = System.currentTimeMillis();
		if (test1First == -1)
			test1First = currentTime;
		test1Last = currentTime;
		System.out.println("test1 - " + Thread.currentThread() + " - " + (currentTime - Math.min(test1First, test2First)));
	}
	
	@Test
	@PerfTest(duration = 3000, threads = 2, timer = ConstantTimer.class, timerParams = { 700 })
	public void test2() throws Exception {
		long currentTime = System.currentTimeMillis();
		if (test2First == -1)
			test2First = currentTime;
		test2Last = currentTime;
		System.out.println("test2 - " + Thread.currentThread() + " - " + (currentTime - Math.min(test1First, test2First)));
	}
	
	@Test
	public void test3() throws Exception {
		// empty method for testing
	}
	
	@AfterClass
	public static void verifyParallelExecution() {
		assertTrue(
			(test1First <= test2First && test2First <= test1Last) ||
			(test2First <= test1First && test1First <= test2Last)
		);
	}
	
}
