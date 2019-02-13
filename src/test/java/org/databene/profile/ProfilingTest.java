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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the profiling feature.<br><br>
 * Created: 21.07.2011 08:30:37
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class ProfilingTest {
	
	private static String originalSetting;

	@BeforeClass
	public static void saveOriginalSetting() {
		originalSetting = System.getProperty("profile");
	}

	@AfterClass
	public static void restoreOriginalSetting() {
		System.setProperty("profile", (originalSetting != null ? originalSetting : "false"));
	}

	@Test
	public void testFalse() {
		System.setProperty("profile", "false");
		assertFalse(Profiling.isEnabled());
	}
	
	@Test
	public void testEmpty() {
		System.setProperty("profile", "");
		assertTrue(Profiling.isEnabled());
	}
	
}
