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

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.atomic.AtomicInteger;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.report.ListReportModule;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Tests the {@link ContiPerfRule}.<br><br>
 * Created: 29.03.2010 12:35:53
 * @since 1.0
 * @author Volker Bergmann
 */
public class ContiPerfRuleTest {

	@Test
	public void testDefault() throws Throwable {
		TestBean target = check("plain");
		assertEquals(1, target.plainCount.get());
	}

	@Test
	public void testInvocationCount() throws Throwable {
		TestBean target = check("five");
		assertEquals(5, target.fiveCount.get());
	}

	@Test
	public void testDuration() throws Throwable {
		TestBean target = check("duration1000");
		int count = target.duration1000Count.get();
		assertTrue("Expected at least 7 invocations, but had only " + count, count > 7);
	}

	@Test
	public void testMedianSuccessful() throws Throwable {
		check("median100Successful");
	}

	@Test(expected = PerformanceRequirementFailedError.class)
	public void testMedianFailed() throws Throwable {
		check("median1Failed");
	}

	@Test
	public void testAverageSuccessful() throws Throwable {
		check("average100Successful");
	}

	@Test(expected = PerformanceRequirementFailedError.class)
	public void testAverageFailed() throws Throwable {
		check("average1Failed");
	}

	@Test
	public void testMaxSuccessful() throws Throwable {
		check("max100Successful");
	}

	@Test
	public void testThroughputSuccessful() throws Throwable {
		check("throughputSuccessful");
	}
	
	@Test(expected = PerformanceRequirementFailedError.class)
	public void testThroughputFailed() throws Throwable {
		check("throughputFailed");
	}
	
	@Test
	public void testTotalTimeSuccessful() throws Throwable {
		check("totalTimeSuccessful");
	}
	
	@Test(expected = PerformanceRequirementFailedError.class)
	public void testTotalTimeFailed() throws Throwable {
		check("totalTimeFailed");
	}
	
	@Test
	public void testPercentileSuccessful() throws Throwable {
		check("percentileSuccessful");
	}
	
	@Test(expected = PerformanceRequirementFailedError.class)
	public void testPercentileFailed() throws Throwable {
		check("percentileFailed");
	}
	
	@Test
	public void testThreads3() throws Throwable {
		TestBean target = check("threads3");
		assertEquals(10, target.threads3IC.get());
		assertEquals(3, target.threads3TC.getThreadCount());
	}
	
	@Test(expected = RuntimeException.class)
	public void testThreads3Failed() throws Throwable {
		check("threads3Failed");
	}
	
	@Test(expected = PerformanceRequirementFailedError.class)
	public void testCancelOnViolationDefault() throws Throwable {
		TestBean test = new TestBean();
		try {
			check(test, "cancelOnViolationDefault");
		} catch (PerformanceRequirementFailedError e) {
			int count = test.cancelOnViolationDefaultCount.get();
			assertEquals(3, count);
			throw e;
		}
	}
	
	@Test(expected = PerformanceRequirementFailedError.class)
	public void testCancelOnViolation() throws Throwable {
		TestBean test = new TestBean();
		try {
			check(test, "cancelOnViolation");
		} catch (PerformanceRequirementFailedError e) {
			int count = test.cancelOnViolationCount.get();
			assertEquals(2, count);
			throw e;
		}
	}
	
	@Test(expected = PerformanceRequirementFailedError.class)
	public void testDontCancelOnViolation() throws Throwable {
		TestBean test = new TestBean();
		try {
			check(test, "dontCancelOnViolation");
		} catch (PerformanceRequirementFailedError e) {
			int count = test.dontCancelOnViolationCount.get();
			assertEquals(3, count);
			throw e;
		}
	}
	
	// helper methods --------------------------------------------------------------------------------------------------

	private static TestBean check(String methodName) throws NoSuchMethodException, Throwable {
	    return check(new TestBean(), methodName);
    }
	
	private static TestBean check(TestBean target, String methodName) throws NoSuchMethodException, Throwable {
	    ContiPerfRule rule = new ContiPerfRule(new ListReportModule());
		Method method = TestBean.class.getDeclaredMethod(methodName, new Class<?>[0]);
		Statement base = new InvokerStatement(target, method);
		FrameworkMethod fwMethod = new FrameworkMethod(method);
		Statement perfTestStatement = rule.apply(base, fwMethod, target);
		perfTestStatement.evaluate();
	    return target;
    }
	
	
	
	public static class TestBean {
		
		public AtomicInteger plainCount = new AtomicInteger();
		
		public void plain() {
			plainCount.incrementAndGet();
		}
		
		public AtomicInteger fiveCount = new AtomicInteger();
		
		@PerfTest(invocations = 5)
		public void five() {
			fiveCount.incrementAndGet();
		}
		
		public AtomicInteger duration1000Count = new AtomicInteger();
		@PerfTest(duration = 1000)
		public void duration1000() throws Exception {
			Thread.sleep(100);
			duration1000Count.incrementAndGet();
		}

		@PerfTest(invocations = 5)
		@Required(median = 100)
		public void median100Successful() {
			// no implementation
		}

		@PerfTest(invocations = 5)
		@Required(median = 1)
		public void median1Failed() throws InterruptedException {
			Thread.sleep(10);
		}

		@PerfTest(invocations = 5)
		@Required(average = 100)
		public void average100Successful() {
			// no implementation
		}

		@PerfTest(invocations = 5)
		@Required(average = 1)
		public void average1Failed() throws InterruptedException {
			Thread.sleep(10);
		}
		
		@Required(max = 100)
		public void max100Successful() {
			// no implementation
		}

		@PerfTest(invocations = 10)
		@Required(throughput = 10)
		public void throughputSuccessful() throws InterruptedException {
			Thread.sleep(10);
		}
		
		@PerfTest(invocations = 10)
		@Required(throughput = 150)
		public void throughputFailed() throws InterruptedException {
			Thread.sleep(10);
		}
		
		@PerfTest(invocations = 10)
		@Required(totalTime = 500)
		public void totalTimeSuccessful() throws InterruptedException {
			Thread.sleep(10);
		}
		
		@PerfTest(invocations = 10)
		@Required(totalTime = 50)
		public void totalTimeFailed() throws InterruptedException {
			Thread.sleep(10);
		}
		
		@PerfTest(invocations = 10)
		@Required(percentiles = "90:50")
		public void percentileSuccessful() throws InterruptedException {
			Thread.sleep(10);
		}
		
		@PerfTest(invocations = 10)
		@Required(percentiles = "90:5")
		public void percentileFailed() throws InterruptedException {
			Thread.sleep(10);
		}
		
		ThreadCounter threads3TC = new ThreadCounter();
		public AtomicInteger threads3IC = new AtomicInteger();
		@PerfTest(invocations = 10, threads = 3)
		public void threads3() throws InterruptedException {
			threads3TC.get();
			threads3IC.incrementAndGet();
			Thread.sleep(100);
		}

		@PerfTest(invocations = 10, threads = 3)
		public void threads3Failed() throws ParseException {
			throw new ParseException("", 0);
		}
		
		public AtomicInteger cancelOnViolationDefaultCount = new AtomicInteger();
		@PerfTest(invocations = 3)
		@Required(max = 200)
		public void cancelOnViolationDefault() throws InterruptedException {
			int n = cancelOnViolationDefaultCount.incrementAndGet();
			Thread.sleep(n * 150);
		}
		
		public AtomicInteger cancelOnViolationCount = new AtomicInteger();
		@Required(max = 200)
		@PerfTest(invocations = 3, cancelOnViolation = true)
		public void cancelOnViolation() throws InterruptedException {
			int n = cancelOnViolationCount.incrementAndGet();
			Thread.sleep(n * 150);
		}
		
		public AtomicInteger dontCancelOnViolationCount = new AtomicInteger();
		@Required(max = 200)
		@PerfTest(invocations = 3, cancelOnViolation = false)
		public void dontCancelOnViolation() throws InterruptedException {
			int n = dontCancelOnViolationCount.incrementAndGet();
			Thread.sleep(n * 150);
		}
		
	}
	
	
	
	public static class InvokerStatement extends Statement {
		
		public TestBean target;
		public Method method;
		
		public InvokerStatement(TestBean target, Method method) {
	        this.target = target;
	        this.method = method;
        }

		@Override
        public void evaluate() throws Throwable {
	        method.invoke(target);
        }
	}

}
