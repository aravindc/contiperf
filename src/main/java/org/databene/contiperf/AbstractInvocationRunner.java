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
package org.databene.contiperf;

/**
 * Abstract helper class which provides common functionality for {@link InvocationRunner}s.<br><br>
 * Created: 06.04.2012 17:38:28
 * @since 2.1.0
 * @author Volker Bergmann
 */
public abstract class AbstractInvocationRunner implements InvocationRunner {
	
	private WaitTimer wait;

	protected AbstractInvocationRunner(WaitTimer wait) {
		this.wait = wait;
	}
	
	protected void sleep() {
		int waitTime = wait.getWaitTime();
		if (waitTime > 0) {
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
}
