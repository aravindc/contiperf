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

import org.databene.contiperf.ExecutionLogger;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.log.FileExecutionLoggerTestUtil;
import org.databene.contiperf.report.LoggerModuleAdapter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Tests proper ExecutionLogger handling.<br/><br/>
 * Created: 22.05.2010 18:36:37
 * @since 1.05
 * @author Volker Bergmann
 */
@SuppressWarnings("deprecation")
public class ExecutionLoggerConfigTest extends AbstractContiPerfTest {
	
	static ExecutionLogger usedLogger;

	@Override
    @Before
	public void setUp() {
		super.setUp();
		usedLogger = null;
		FileExecutionLoggerTestUtil.resetInvocationCount();
	}

	

	// testing explicit simple test case execution logger --------------------------------------------------------------
	
	@Test
	public void testConfigured() throws Exception {
		runTest(ConfiguredTest.class);
		assertEquals(ExecutionTestLogger.class, usedLogger.getClass());
		assertEquals(1, ((ExecutionTestLogger) usedLogger).id);
		assertEquals(4, ((ExecutionTestLogger) usedLogger).invocations);
	}

	public static class ConfiguredTest {
		
		@Rule public ContiPerfRule rule = new ContiPerfRule(new ExecutionTestLogger(1));

		@Test
		@PerfTest(invocations = 4)
		public void test() {
			usedLogger = ((LoggerModuleAdapter) rule.getContext().getReportModules().get(0)).getLogger();
		}
	}
	
	
	
	// testing explicit suite execution logger -------------------------------------------------------------------------
	
	@Test
	public void testConfiguredSuite() throws Exception {
		runTest(ConfiguredSuite.class);
		assertEquals(ExecutionTestLogger.class, usedLogger.getClass());
		assertEquals(3, ExecutionTestLogger.latestInstance.id);
		assertEquals(3, ExecutionTestLogger.latestInstance.invocations);
	}

	@RunWith(ContiPerfSuiteRunner.class)
	@SuiteClasses(UnconfiguredTest.class)
	@PerfTest(invocations = 6)
	public static class ConfiguredSuite {
		public ExecutionLogger el = new ExecutionTestLogger(3);
	}

	public static class UnconfiguredTest {
		
		@Rule public ContiPerfRule rule = new ContiPerfRule();
		
		@Test
		@PerfTest(invocations = 3)
		public void test() {
			usedLogger = ((LoggerModuleAdapter) rule.getContext().getReportModules().get(0)).getLogger();
		}
	}

}
