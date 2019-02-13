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
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Verifies that before and after methods are executed only once for each test method.<br><br>
 * Created: 10.09.2011 14:43:43
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class BeforeAfterTest {

	private int beforeCount = 0;
	private int afterCount = 0;
	
	@Rule public ContiPerfRule rule = new ContiPerfRule();
	
	@Before
	public void before() {
		System.out.println("before()");
		beforeCount++;
	}
	
	@After
	public void after() {
		System.out.println("after()");
		afterCount++;
	}
	
	@Test
	@PerfTest(invocations = 1000)
	public void test() {
		assertTrue("method before() may only be called once, but was called " + beforeCount + " times", 
				beforeCount == 1);
		assertTrue(afterCount == 0);
	}
	
}
