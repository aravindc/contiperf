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

import static org.junit.Assert.assertEquals;

import org.databene.contiperf.WaitTimer;
import org.junit.Test;

/**
 * Tests the '{@link None}' wait timer.<br><br>
 * Created: 06.04.2012 18:18:34
 * @since 2.1.0
 * @author Volker Bergmann
 */
public class NoneTest {

	@Test
	public void testEmptyInitialization() throws Exception {
		WaitTimer timer = None.class.newInstance();
		timer.init(new double[0]);
		for (int i = 0; i < 1000; i++)
			assertEquals(0, timer.getWaitTime());
	}
	
}
