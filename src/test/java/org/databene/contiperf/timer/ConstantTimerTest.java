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
package org.databene.contiperf.timer;

import static org.junit.Assert.*;

import org.databene.contiperf.WaitTimer;
import org.junit.Test;

/**
 * Tests the {@link ConstantTimer}.<br/><br/>
 * Created: 06.04.2012 18:10:41
 * @since 2.1.0
 * @author Volker Bergmann
 */
public class ConstantTimerTest {

	@Test
	public void testEmptyInitialization() throws Exception {
		WaitTimer timer = ConstantTimer.class.newInstance();
		timer.init(new double[0]);
		for (int i = 0; i < 1000; i++)
			assertEquals(1000, timer.getWaitTime());
	}
	
	@Test
	public void testNormalInitialization() throws Exception {
		WaitTimer timer = ConstantTimer.class.newInstance();
		timer.init(new double[] { 123 });
		for (int i = 0; i < 1000; i++)
			assertEquals(123, timer.getWaitTime());
	}
	
	@Test
	public void testTooManyParams() throws Exception {
		WaitTimer timer = ConstantTimer.class.newInstance();
		timer.init(new double[] { 234, 456 });
		for (int i = 0; i < 1000; i++)
			assertEquals(234, timer.getWaitTime());
	}
	
}
