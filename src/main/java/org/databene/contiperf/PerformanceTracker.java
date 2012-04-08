/*
 * (c) Copyright 2009-2012 by Volker Bergmann. All rights reserved.
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

import java.io.PrintWriter;

import org.databene.contiperf.report.ReportContext;
import org.databene.contiperf.report.ReportModule;
import org.databene.contiperf.util.InvokerProxy;
import org.databene.stat.LatencyCounter;

/**
 * {@link InvokerProxy} that provides performance tracking features.<br/><br/>
 * Created: 22.10.2009 16:36:43
 * @since 1.0
 * @author Volker Bergmann
 */
public class PerformanceTracker extends InvokerProxy {
	
    private PerformanceRequirement requirement;
    private int warmUp;
    private boolean cancelOnViolation;
    private ReportContext context;
    
    private LatencyCounter counter;
    private boolean counterStarted;
    private long warmUpFinishedTime;

	public PerformanceTracker(Invoker target, PerformanceRequirement requirement, ReportContext context) {
	    this(target, requirement, 0, true, context);
    }

	public PerformanceTracker(Invoker target, PerformanceRequirement requirement, 
			int warmUp, boolean cancelOnViolation, ReportContext context) {
	    super(target);
	    this.requirement = requirement;
	    this.warmUp = warmUp;
	    this.cancelOnViolation = cancelOnViolation;
	    this.setContext(context);
	    this.counter = null;
	    this.counterStarted = false;
	    this.warmUpFinishedTime = -1;
    }
	
	// interface -------------------------------------------------------------------------------------------------------

	public void setContext(ReportContext context) {
		this.context = context;
	}
	
    public LatencyCounter getCounter() {
	    return counter;
    }

	public void startCounter() {
		reportStart();
    	int max = (requirement != null ? requirement.getMax() : -1);
    	counter = new LatencyCounter(target.toString(), max >= 0 ? max : 1000);
    	counter.start();
    	counterStarted = true;
	}
	
	@Override
    public Object invoke(Object[] args) throws Exception {
	    long callStart = System.nanoTime();
		if (warmUpFinishedTime == -1)
	    	warmUpFinishedTime = callStart + warmUp * 1000000;
	    checkState(callStart);
		Object result = super.invoke(args);
	    int latency = (int) ((System.nanoTime() - callStart) / 1000000);
	    if (counterStarted)
	    	counter.addSample(latency);
	    reportInvocation(latency, callStart);
	    if (requirement != null && requirement.getMax() >= 0 && latency > requirement.getMax() && cancelOnViolation)
	    	context.fail("Method " + getId() + " exceeded time limit of " + 
	    			requirement.getMax() + " ms running " + latency + " ms");
	    return result;
	}

	private synchronized void checkState(long callStart) {
		if (callStart >= warmUpFinishedTime && !counterStarted)
			startCounter();
	}
	
	public boolean isCounterStarted() {
		return counterStarted;
	}
	
	public void stopCounter() {
		if (!isCounterStarted())
			throw new RuntimeException("Trying to stop counter before it was started");
    	counter.stop();
    	counter.printSummary(new PrintWriter(System.out));
    	reportCompletion();
    	if (requirement != null)
    		checkRequirements(counter.duration());
    	this.counterStarted = false;
	}

	public void clear() {
		counter = null;
	}

	// helper methods --------------------------------------------------------------------------------------------------
	
	private void reportStart() {
		for (ReportModule module : context.getReportModules())
			module.starting(getId());
	}

	private void reportInvocation(int latency, long callStart) {
		for (ReportModule module : context.getReportModules())
			module.invoked(getId(), latency, callStart);
	}

	private void reportCompletion() {
		for (ReportModule module : context.getReportModules())
			module.completed(getId(), counter, requirement);
	}

	private void checkRequirements(long elapsedMillis) {
	    long requiredMax = requirement.getMax();
    	if (requiredMax >= 0) {
    		if (counter.maxLatency() > requiredMax)
    			context.fail("The maximum latency of " + 
    					requiredMax + " ms was exceeded, Measured: " + counter.maxLatency() + " ms");
    	}
	    long requiredTotalTime = requirement.getTotalTime();
    	if (requiredTotalTime >= 0) {
    		if (elapsedMillis > requiredTotalTime)
    			context.fail("Test run " + getId() + " exceeded timeout of " + 
    				requiredTotalTime + " ms running " + elapsedMillis + " ms");
    	}
    	int requiredThroughput = requirement.getThroughput();
    	if (requiredThroughput > 0 && elapsedMillis > 0) {
    		long actualThroughput = counter.sampleCount() * 1000 / elapsedMillis;
    		if (actualThroughput < requiredThroughput)
    			context.fail("Test " + getId() + " had a throughput of only " + 
        				actualThroughput + " calls per second, required: " + requiredThroughput + " calls per second");
    	}
    	int requiredAverage = requirement.getAverage();
		if (requiredAverage >= 0 && counter.averageLatency() > requiredAverage)
			context.fail("Average execution time of " + getId() + " exceeded the requirement of " + 
					requiredAverage + " ms, measured " + counter.averageLatency() + " ms");
    	for (PercentileRequirement percentile : requirement.getPercentileRequirements()) {
    		long measuredLatency = counter.percentileLatency(percentile.getPercentage());
			if (measuredLatency > percentile.getMillis())
				context.fail(percentile.getPercentage() + "-percentile of " + getId() + " exceeded the requirement of " + 
    					percentile.getMillis() + " ms, measured " + measuredLatency + " ms");
    	}
    }

}
