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

import org.databene.contiperf.Clock;

/**
 * Abstract implementation of the {@link Clock} interface. 
 * Inherit custom {@link Clock} implementations from this 
 * class in order to have a better future compatibility.<br/><br/>
 * Created: 24.05.2012 08:06:01
 * @since 2.2.0
 * @author Volker Bergmann
 */
public abstract class AbstractClock implements Clock {

	private String name;

	public AbstractClock(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
