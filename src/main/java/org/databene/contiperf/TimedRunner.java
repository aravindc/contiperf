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

import org.databene.contiperf.util.ContiPerfUtil;

/**
 * Calls the invoker for a certain amount of time.<br><br>
 * Created: 15.04.2010 23:13:52
 * @since 1.03
 * @author Volker Bergmann
 */
public class TimedRunner extends AbstractInvocationRunner {

    private long duration;
    private ArgumentsProvider argsProvider;
    private Invoker invoker;

    public TimedRunner(Invoker invoker, ArgumentsProvider argsProvider, 
    		WaitTimer waitTimer, long duration) {
    	super(waitTimer);
	    this.invoker = invoker;
	    this.argsProvider = argsProvider;
	    this.duration = duration;
    }

	public void run() {
		try {
		    long start = System.currentTimeMillis();
		    long endTime = start + duration;
		    do {
	    	    invoker.invoke(argsProvider.next());
	    	    sleep();
		    } while (System.currentTimeMillis() < endTime);
		} catch (Exception e) {
			throw ContiPerfUtil.executionError(e);
		}
    }

	public void close() {
	    invoker = null;
    }
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + duration + " ms)";
	}

}
