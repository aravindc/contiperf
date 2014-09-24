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

import java.util.concurrent.atomic.AtomicLong;

import org.databene.contiperf.util.ContiPerfUtil;

/**
 * Calls the invoker a fixed number of times.<br/><br/>
 * Created: 22.10.2009 06:30:28
 * @since 1.0
 * @author Volker Bergmann
 */
public class CountRunner extends AbstractInvocationRunner {

    private ArgumentsProvider argsProvider;
    private Invoker invoker;
    private AtomicLong invocationsLeft;

    public CountRunner(Invoker invoker, ArgumentsProvider argsProvider, 
    		WaitTimer waitTimer, AtomicLong invocationsLeft) {
    	super(waitTimer);
	    this.invoker = invoker;
	    this.argsProvider = argsProvider;
	    this.invocationsLeft = invocationsLeft;
    }

    public void run() {
    	try {
    		while (invocationsLeft.getAndDecrement() > 0) {
	    	    invoker.invoke(argsProvider.next());
	    	    sleep();
			}
    	} catch (Exception e) {
    		throw ContiPerfUtil.executionError(e);
    	}
    }

	public void close() {
	    invoker = null;
    }
    
}
