/*
 * (c) Copyright 2009-2010 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU Lesser General Public License (LGPL), Eclipse Public License (EPL) 
 * and the BSD License.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
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
public class CountRunner implements InvocationRunner {

    private ArgumentsProvider argsProvider;
    private Invoker invoker;
    private AtomicLong invocationsLeft;
    private boolean yield;

    public CountRunner(Invoker invoker, ArgumentsProvider argsProvider, AtomicLong invocationsLeft, boolean yield) {
	    this.invoker = invoker;
	    this.argsProvider = argsProvider;
	    this.invocationsLeft = invocationsLeft;
	    this.yield  = yield;
    }

    public void run() {
    	try {
    		while (invocationsLeft.getAndDecrement() > 0) {
	    	    invoker.invoke(argsProvider.next());
	    	    if (yield)
	    	    	Thread.yield();
			}
    	} catch (Exception e) {
    		throw ContiPerfUtil.executionError(e);
    	}
    }

	public void close() {
	    invoker = null;
    }
    
}
