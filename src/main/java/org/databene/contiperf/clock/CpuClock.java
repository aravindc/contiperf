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
package org.databene.contiperf.clock;

import java.lang.management.ManagementFactory;

import org.databene.contiperf.Clock;

/**
 * {@link Clock} implementation which provides the CPU time of the current thread
 * via {@link java.lang.management.ThreadMXBean#getCurrentThreadCpuTime()}<br/><br/>
 * Created: 23.05.2012 07:53:29
 * @since 2.2.0
 * @author Volker Bergmann
 */
public class CpuClock extends AbstractClock {

	public static final String NAME = "cpu";

	public CpuClock() {
		super(NAME);
	}

	public long getTime() {
		return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime() / 1000000;
	}
	
}
