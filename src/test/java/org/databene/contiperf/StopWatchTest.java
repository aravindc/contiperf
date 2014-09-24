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

import static org.junit.Assert.*;

import org.databene.stat.CounterRepository;
import org.databene.stat.LatencyCounter;
import org.junit.After;
import org.junit.Test;

/**
 * Tests the {@link StopWatch}.<br/><br/>
 * Created: 14.01.2011 11:33:37
 * @since 1.08
 * @author Volker Bergmann
 */
public class StopWatchTest {

	private static final String NAME = "StopWatchTest";
	
	@After
	public void tearDown() {
		CounterRepository.getInstance().clear();
	}
	
	@Test
	public void testSingleCall() throws InterruptedException {
		sleepTimed(50);
		assertEquals(1, getCounter().sampleCount());
	}

	@Test
	public void testSubsequentCalls() throws InterruptedException {
		sleepTimed(50);
		sleepTimed(50);
		sleepTimed(50);
		LatencyCounter counter = getCounter();
		assertEquals(3, counter.sampleCount());
		assertTrue(counter.minLatency() >= 39);
		assertTrue(counter.minLatency() < 100);
		assertTrue(counter.averageLatency() >= 39);
		assertTrue(counter.averageLatency() < 100);
	}

	@Test
	public void testParallelCalls() throws InterruptedException {
		Thread[] threads = new Thread[20];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread() {
				@Override
				public void run() {
					try {
						for (int i = 0; i < 20; i++)
							sleepTimed(50);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			};
		}
		for (int i = 0; i < threads.length; i++)
			threads[i].start();
		for (int i = 0; i < threads.length; i++)
			threads[i].join();
		LatencyCounter counter = getCounter();
		assertEquals(400, counter.sampleCount());
		assertTrue(counter.minLatency() >= 39);
		assertTrue(counter.minLatency() < 100);
		assertTrue(counter.averageLatency() >= 39);
		assertTrue(counter.averageLatency() < 100);
	}
	
	@Test(expected = RuntimeException.class)
	public void testMultiStop() {
		StopWatch watch = new StopWatch(NAME);
		watch.stop();
		watch.stop();
	}

	private static void sleepTimed(int delay) throws InterruptedException {
		StopWatch watch = new StopWatch(NAME);
		Thread.sleep(delay);
		watch.stop();
	}
	
	private static LatencyCounter getCounter() {
		return CounterRepository.getInstance().getCounter(NAME);
	}

}
