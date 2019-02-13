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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Helper class for measuring concurrency.<br><br>
 * Created: 16.04.2010 00:01:25
 * @since 1.03
 * @author Volker Bergmann
 */
public class ThreadCounter extends ThreadLocal<ThreadCounter> {
	
	private AtomicInteger count = new AtomicInteger();
	
	@Override
	protected ThreadCounter initialValue() {
		count.incrementAndGet();
	    return this;
	}

	public int getThreadCount() {
		return count.get();
	}
	
}
