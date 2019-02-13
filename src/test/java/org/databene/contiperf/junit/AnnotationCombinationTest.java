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
import org.databene.contiperf.Required;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Tests the combination of a {@link Required} annotation in a plain test class 
 * and a {@link PerfTest} in a suite that contains the former test.<br><br>
 * Created: 24.05.2010 06:42:31
 * @since 1.05
 * @author Volker Bergmann
 */
public class AnnotationCombinationTest extends AbstractContiPerfTest {

	// testing suite that matches the requirements ---------------------------------------------------------------------
	
	@Test
	public void testSuccessfulSuiteWithExecutionConfig() throws Exception {
        runTest(SucessfulSuiteWithExecutionConfig.class);
        assertFalse(failed);
	}
	
	@RunWith(ContiPerfSuiteRunner.class)
	@SuiteClasses(TestWithRequirements.class)
	@PerfTest(invocations = 2)
	public static class SucessfulSuiteWithExecutionConfig {
		// no content appropriate for suite
	}
	
	// testing suite that misses the requirements ----------------------------------------------------------------------
	
	@Test
	public void testFailingSuiteWithExecutionConfig() throws Exception {
        runTest(FailingSuiteWithExecutionConfig.class);
        assertTrue(failed);
	}
	
	@RunWith(ContiPerfSuiteRunner.class)
	@SuiteClasses(TestWithRequirements.class)
	@PerfTest(invocations = 5)
	public static class FailingSuiteWithExecutionConfig {
		// no content appropriate for suite
	}
	
	// simple test class with performance requirements annotation ------------------------------------------------------
	
	public static class TestWithRequirements {
		@Test
		@Required(totalTime = 300)
		public void test() throws Exception {
			Thread.sleep(80);
		}
	}
	
}
