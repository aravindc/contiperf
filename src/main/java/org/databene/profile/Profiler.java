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

import java.util.List;

/**
 * Organizes {@link Profile}s in a tree structure.<br><br>
 * Created: 19.05.2011 09:01:32
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class Profiler {
	
	private static final Profiler DEFAULT_INSTANCE = new Profiler("default", 1);

	private long granularity;
	private Profile rootProfile;
	
	public Profiler(String name, long granularity) {
		this.granularity = granularity;
		this.rootProfile = new Profile(name, null);
	}
	
	public static Profiler defaultInstance() {
		return DEFAULT_INSTANCE;
	}
	
	public Profile getRootProfile() {
		return rootProfile;
	}
	
	public void addSample(List<String> path, long duration) {
		int depth = path.size();
		Profile profile = rootProfile;
		for (int i = 0; i < depth; i++)
			profile = profile.getOrCreateSubProfile(path.get(i));
		profile.addSample((int) (duration / granularity));
	}

	public void printSummary() {
		printRecursively(rootProfile, "");
	}

	private void printRecursively(Profile profile, String indent) {
		System.out.println(indent + profile.toString());
		for (Profile subProfile : profile.getSubProfiles())
			printRecursively(subProfile, indent + "  ");
	}

}
