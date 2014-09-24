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

import java.util.concurrent.atomic.AtomicInteger;

import org.databene.contiperf.PerfTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.model.InitializationError;

/**
 * Tests {@link ContiPerfSuiteRunner} processing.<br/><br/>
 * Created: 02.05.2010 09:18:31
 * @since 1.05
 * @author Volker Bergmann
 */
public class ContiPerfSuiteTest {
	
	@Before
	public void setUp() {
		uCount.set(0);
		cCount.set(0);
		//pCount.set(0);
	}
	
	@Test
	public void test() {
		// Empty test method
	}
	
	@Test
	public void testUnconfiguredSuiteForUnconfiguredTest() throws InitializationError {
		runSuite(UnconfiguredSuiteForUnconfiguredTest.class);
		assertEquals(1, uCount.get());
		assertEquals(0, cCount.get());
	}

	@Test
	public void testConfiguredSuiteForUnconfiguredTest() throws InitializationError {
		runSuite(ConfiguredSuiteForUnconfiguredTest.class);
		assertEquals(11, uCount.get());
		assertEquals(0, cCount.get());
	}

	@Test
	public void testUnconfiguredSuiteForConfiguredMethodTest() throws InitializationError {
		runSuite(UnconfiguredSuiteForConfiguredMethodTest.class);
		assertEquals(0, uCount.get());
		assertEquals(2, cCount.get());
	}

	@Test
	public void testConfiguredSuiteForConfiguredMethodTest() throws InitializationError {
		runSuite(ConfiguredSuiteForConfiguredMethodTest.class);
		assertEquals(0, uCount.get());
		assertEquals(2, cCount.get());
	}

	@Test
	public void testUnconfiguredSuiteForConfiguredClassTest() throws InitializationError {
		runSuite(UnconfiguredSuiteForConfiguredClassTest.class);
		assertEquals(0, uCount.get());
		assertEquals(3, cCount.get());
	}

	@Test
	public void testConfiguredSuiteForConfiguredClassTest() throws InitializationError {
		runSuite(ConfiguredSuiteForConfiguredClassTest.class);
		assertEquals(0, uCount.get());
		assertEquals(3, cCount.get());
	}

	@Test
	public void testUnconfiguredSuiteForConfiguredClassAndMethodTest() throws InitializationError {
		runSuite(UnconfiguredSuiteForConfiguredClassAndMethodTest.class);
		assertEquals(0, uCount.get());
		assertEquals(7, cCount.get());
	}

	@Test
	public void testConfiguredSuiteForConfiguredClassAndMethodTest() throws InitializationError {
		runSuite(ConfiguredSuiteForConfiguredClassAndMethodTest.class);
		assertEquals(0, uCount.get());
		assertEquals(7, cCount.get());
	}

/*	
	@Test
	public void testConfiguredSuiteForParameterizedTest() throws InitializationError {
		runSuite(ConfiguredSuiteForParametrizedTest.class);
		assertEquals(0, uCount.get());
		assertEquals(0, cCount.get());
		assertEquals(9, pCount.get());
	}
*/
	// helper methods --------------------------------------------------------------------------------------------------

	private static void runSuite(Class<?> testClass) throws InitializationError {
	    ContiPerfSuiteRunner suite = new ContiPerfSuiteRunner(testClass);
		RunNotifier notifier = new RunNotifier();
		suite.run(notifier);
    }

	// tested classes and their invocation counters --------------------------------------------------------------------
	
	@RunWith(ContiPerfSuiteRunner.class)
	@SuiteClasses(UnconfiguredTest.class)
	public static class UnconfiguredSuiteForUnconfiguredTest {
		// no implementation useful for test suite
	}
	
	@RunWith(ContiPerfSuiteRunner.class)
	@SuiteClasses(UnconfiguredTest.class)
	@PerfTest(invocations = 11, threads = 2)
	public static class ConfiguredSuiteForUnconfiguredTest {
		// no implementation useful for test suite
	}
	
	@RunWith(ContiPerfSuiteRunner.class)
	@SuiteClasses(ConfiguredMethodTest.class)
	public static class UnconfiguredSuiteForConfiguredMethodTest {
		// no implementation useful for test suite
	}
	
	@RunWith(ContiPerfSuiteRunner.class)
	@SuiteClasses(ConfiguredMethodTest.class)
	@PerfTest(invocations = 13, threads = 2)
	public static class ConfiguredSuiteForConfiguredMethodTest {
		// no implementation useful for test suite
	}
	
	@RunWith(ContiPerfSuiteRunner.class)
	@SuiteClasses(ConfiguredClassTest.class)
	public static class UnconfiguredSuiteForConfiguredClassTest {
		// no implementation useful for test suite
	}
	
	@RunWith(ContiPerfSuiteRunner.class)
	@SuiteClasses(ConfiguredClassTest.class)
	@PerfTest(invocations = 17, threads = 2)
	public static class ConfiguredSuiteForConfiguredClassTest {
		// no implementation useful for test suite
	}
	
	@RunWith(ContiPerfSuiteRunner.class)
	@SuiteClasses(ConfiguredClassAndMethodTest.class)
	public static class UnconfiguredSuiteForConfiguredClassAndMethodTest {
		// no implementation useful for test suite
	}
	
	@RunWith(ContiPerfSuiteRunner.class)
	@SuiteClasses(ConfiguredClassAndMethodTest.class)
	@PerfTest(invocations = 19, threads = 2)
	public static class ConfiguredSuiteForConfiguredClassAndMethodTest {
		// no implementation useful for test suite
	}
	
/* 
	@RunWith(ContiPerfSuite.class)
	@SuiteClasses(ParameterizedTest.class)
	@PerfTest(invocations = 3, threads = 2)
	public static class ConfiguredSuiteForParameterizedTest {
	}
*/
	static volatile AtomicInteger uCount = new AtomicInteger();
	static volatile AtomicInteger cCount = new AtomicInteger();
//	static volatile AtomicInteger pCount = new AtomicInteger();
	
	
	public static class UnconfiguredTest {
		@Test
		public void test() throws Exception {
			uCount.incrementAndGet();
		}
	}

	
	
	public static class ConfiguredMethodTest extends AbstractContiPerfTest {
		@Test
		@PerfTest(invocations = 2)
		public void test() throws Exception {
			cCount.incrementAndGet();
		}
	}
	
	
	
	@PerfTest(invocations = 3)
	public static class ConfiguredClassTest extends AbstractContiPerfTest {
		@Test
		public void test() throws Exception {
			cCount.incrementAndGet();
		}
	}
	
	
	
	@PerfTest(invocations = 5)
	public static class ConfiguredClassAndMethodTest extends AbstractContiPerfTest {
		@Test
		@PerfTest(invocations = 7)
		public void test() throws Exception {
			cCount.incrementAndGet();
		}
	}
	
}
