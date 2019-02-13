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
package org.databene.profile;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Tests the {@link Profiler}.<br><br>
 * Created: 19.05.2011 09:43:47
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class ProfilerTest {
	
	@Test
	public void test() {
		
		// given
		Profiler profiler = new Profiler("test", 100);
		
		//when
		List<String> path = new ArrayList<String>();
		profiler.addSample(path, 1000);
		path.add("sub1");
		profiler.addSample(path, 200);
		path.remove(path.size() - 1);
		path.add("sub2");
		profiler.addSample(path, 300);
		profiler.addSample(path, 400);
		
		// then
		Profile rootProfile = profiler.getRootProfile();
		assertEquals(1, rootProfile.getInvocationCount());
		assertEquals(10., rootProfile.getAverageLatency(), 0.01);
		assertEquals(10, rootProfile.getTotalLatency());

		Profile sub1Profile = rootProfile.getOrCreateSubProfile("sub1");
		assertEquals(1, sub1Profile.getInvocationCount());
		assertEquals(2., sub1Profile.getAverageLatency(), 0.01);
		assertEquals(2, sub1Profile.getTotalLatency());

		Profile sub2Profile = rootProfile.getOrCreateSubProfile("sub2");
		assertEquals(2, sub2Profile.getInvocationCount());
		assertEquals(3.5, sub2Profile.getAverageLatency(), 0.01);
		assertEquals(7, sub2Profile.getTotalLatency());
		
		profiler.printSummary();
	}

}
