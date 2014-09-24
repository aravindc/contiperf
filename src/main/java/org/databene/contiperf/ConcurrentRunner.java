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
 * Runs several {@link Runnable}s concurrently. 
 * If a {@link Throwable} is encountered, execution of all threads is canceled.<br/><br/>
 * Created: 15.04.2010 23:42:30
 * @since 1.03
 * @author Volker Bergmann
 */
public class ConcurrentRunner implements InvocationRunner {

	private String name;
	private InvocationRunner[] runners;
	private int rampUp;
	
	public ConcurrentRunner(String name, InvocationRunner[] runners, int rampUp) {
	    this.name = name;
	    this.runners = runners;
	    this.rampUp = rampUp;
    }

    public void run() {
		CPThreadGroup threadGroup = new CPThreadGroup(name);
	    Thread[] threads = new Thread[runners.length];
	    for (int i = 0; i < runners.length; i++)
	        threads[i] = new Thread(threadGroup, runners[i]);
	    for (int i = 0; i < runners.length; i++) {
	    	Thread thread = threads[i];
	    	thread.start();
	    	if (rampUp > 0 && i < runners.length - 1)
	    		sleepForRampUpTime();
	    }
	    try {
	        for (Thread thread : threads)
	        	thread.join();
        } catch (InterruptedException e) {
        	// if the thread group has an exception, that one is more interesting
        	if (threadGroup.throwable == null)
        		throw new PerfTestExecutionError(e); // interruption without throwable cause
        }
        // The thread group encountered a Throwable, report it to the caller
    	if (threadGroup.throwable != null)
    		throw ContiPerfUtil.executionError(threadGroup.throwable);
    }
    
    private void sleepForRampUpTime() {
		try {
			Thread.sleep(rampUp);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/** 
     * Implements the {@link ThreadGroup#uncaughtException(Thread, Throwable)} method
     * interrupting the execution of all threads in case of a {@link Throwable} and
     * memorizing the {@link Throwable}.
     */
    class CPThreadGroup extends ThreadGroup {
    	
    	Throwable throwable;

		public CPThreadGroup(String name) {
	        super(name);
        }
    	
		@Override
		public void uncaughtException(Thread thread, Throwable throwable) {
		    if (this.throwable == null)
		    	this.throwable = throwable;
		    interrupt();
		}
    }

	public void close() {
		for (InvocationRunner runner : runners)
			runner.close();
	    runners = null;
    }
    
}
