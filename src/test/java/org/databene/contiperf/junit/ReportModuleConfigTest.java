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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.report.HtmlReportModule;
import org.databene.contiperf.report.ReportContext;
import org.databene.contiperf.report.ReportModule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Integration test for ContiPerf's {@link ReportModule} support.<br><br>
 * Created: 16.01.2011 14:06:10
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class ReportModuleConfigTest extends AbstractContiPerfTest {
	
	static ReportContext usedContext;

	@Override
    @Before
	public void setUp() {
		super.setUp();
		usedContext = null;
	}

	// testing default execution logger --------------------------------------------------------------------------------

	@Test
	public void testUnconfiguredTest() throws Exception {
		runTest(UnconfiguredTest.class);
		assertNotNull(usedContext.getReportModule(HtmlReportModule.class));
	}

	public static class UnconfiguredTest {
		
		@Rule public ContiPerfRule rule = new ContiPerfRule();
		
		@Test
		@PerfTest(invocations = 3)
		public void test() {
			usedContext = rule.context;
		}
	}

	// testing explicit simple test case execution logger --------------------------------------------------------------

	@Test
	public void testParamConfigured() throws Exception {
		runTest(ParamConfiguredTest.class);
		ExecutionTestModule logger = usedContext.getReportModule(ExecutionTestModule.class);
		assertEquals(1, logger.id);
		assertEquals(4, logger.invocations);
	}

	public static class ParamConfiguredTest {
		
		@Rule public ContiPerfRule rule = new ContiPerfRule(new ExecutionTestModule(1));

		@Test
		@PerfTest(invocations = 4)
		public void test() {
			usedContext = rule.context;
		}
	}

	@Test
	public void testAttributeConfigured() throws Exception {
		runTest(AttributeConfiguredTest.class);
		ExecutionTestModule logger = usedContext.getReportModule(ExecutionTestModule.class);
		assertEquals(2, logger.id);
		assertEquals(5, logger.invocations);
	}

	public static class AttributeConfiguredTest {
		
		public ReportModule module = new ExecutionTestModule(2);
		@Rule public ContiPerfRule rule = new ContiPerfRule();

		@Test
		@PerfTest(invocations = 5)
		public void test() {
			usedContext = rule.context;
		}
	}

	
	
	// testing explicit suite execution logger -------------------------------------------------------------------------
	
	@Test
	public void testConfiguredSuite() throws Exception {
		runTest(ConfiguredSuite.class);
		assertNotNull("ExecutionTestModule was not used", ExecutionTestModule.latestInstance);
		assertEquals("ExecutionTestModule was not properly initialized:", 4, ExecutionTestModule.latestInstance.id);
		assertEquals("ExecutionTestModule was not called properly:", 3, ExecutionTestModule.latestInstance.invocations);
	}

	@RunWith(ContiPerfSuiteRunner.class)
	@SuiteClasses(UnconfiguredTest.class)
	public static class ConfiguredSuite {
		public ReportModule el = new ExecutionTestModule(4);
	}

}
