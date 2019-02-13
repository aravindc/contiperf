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

import org.databene.contiperf.clock.SystemClock;
import org.databene.contiperf.timer.None;

/**
 * Holds the execution configuration for a single test.<br><br>
 * Created: 18.10.2009 06:31:25
 * @since 1.0
 * @author Volker Bergmann
 */
public class ExecutionConfig {
	
	private int invocations;
	private int duration;
	private Clock[] clocks;
	private int rampUp;
	private int warmUp;
	private int threads;
	WaitTimer waitTimer;
	private boolean cancelOnViolation;
	// TODO v2.x private int timeout;
	
	public ExecutionConfig(int invocations) {
	    this(invocations, 1, -1, new Clock[] { new SystemClock() }, 0, 0, false, None.class, new double[0] /*, -1*/);
    }

	public ExecutionConfig(int invocations, int threads, int duration, Clock[] clocks, int rampUp, int warmUp, boolean cancelOnViolation,
			Class<? extends WaitTimer> waitTimerClass, double[] waitParams /*, int timeout*/) {
	    this.invocations = invocations;
	    this.threads = threads;
	    this.duration = duration;
	    this.clocks = clocks;
	    this.rampUp = rampUp;
	    this.warmUp = warmUp;
	    this.cancelOnViolation = cancelOnViolation;
	    try {
			waitTimer = (WaitTimer) waitTimerClass.newInstance();
			waitTimer.init(waitParams);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	    //this.timeout = timeout;
    }

	public int getInvocations() {
		return invocations;
	}

	public void setInvocations(int invocations) {
    	this.invocations = invocations;
    }
	
	public int getThreads() {
		return threads;
	}

	public int getDuration() {
		return duration;
	}
	
	public int getRampUp() {
		return rampUp;
	}
	
	public int getWarmUp() {
		return warmUp;
	}
	
	public WaitTimer getWaitTimer() {
		return waitTimer;
	}
	
/* 
	public int getTimeout() {
		return timeout;
	}
*/
	public boolean isCancelOnViolation() {
		return cancelOnViolation;
	}

	@Override
	public String toString() {
	    return (invocations > 0 ? invocations + " invocations" : "Running" + duration + " ms") + 
	    	" with " + threads + " threads";
	}

	public Clock[] getClocks() {
		return clocks;
	}

}
