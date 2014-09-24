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

import java.util.Random;

import org.databene.contiperf.Required;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Unrepeatable;
import org.databene.contiperf.report.EmptyReportModule;
import org.junit.Rule;
import org.junit.Test;

public class SmokeTest {
	
	@Rule public ContiPerfRule rule = new ContiPerfRule(new EmptyReportModule());
	
	@Test
	public void simpleTest() throws Exception {
		Thread.sleep(100);
	}

	
	@Unrepeatable @Test
	public void unrepeatableTest() throws Exception {
		Thread.sleep(100);
	}

	
	@Test(timeout = 250)
	@PerfTest(invocations = 5)
	@Required(max = 1200, percentile90 = 220)
	public void detailedTest() throws Exception {
		Thread.sleep(200);
	}

	@Test(timeout = 250)
	@PerfTest(duration = 2000)
	@Required(throughput = 4)
	public void continuousTest() throws Exception {
		Thread.sleep(200);
	}


	@Test(timeout = 300)
	@PerfTest(invocations = 5)
	@Required(max = 250, percentiles = "90:210,95:220")
	public void complexTest() throws Exception {
		Thread.sleep(200);
	}

	
	Random random = new Random();
	
	@Test
	@PerfTest(invocations = 1000, threads = 2)
	public void threadTest() throws Exception {
		Thread.sleep(random.nextInt(20));
	}

}
