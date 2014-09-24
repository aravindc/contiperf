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

import org.databene.contiperf.WaitTimer;

/**
 * {@link WaitTimer} implementation that provides a wait time uniformly distributed between a min and a max value.<br/><br/>
 * Created: 06.04.2012 17:13:42
 * @since 2.1.0
 * @author Volker Bergmann
 */
public class RandomTimer extends AbstractTimer {
	
	private int min =  500;
	private int range = 1001;
	private java.util.Random random = new java.util.Random();
	
	public void init(double[] params) {
		checkParamCount(2, params);
		if (params.length > 0)
			min = (int) params[0];
		if (params.length > 1)
			range = (int) params[1] - min + 1;
	}

	public int getWaitTime() {
		return min + random.nextInt(range);
	}

}
