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
 * Defines performance requirements on a test.<br><br>
 * Created: 18.10.2009 06:21:57
 * @since 1.0
 * @author Volker Bergmann
 */
public class PerformanceRequirement {

	private int average;
	private int max;
	private int totalTime;
	private int throughput;

	private PercentileRequirement[] percentiles;

	public PerformanceRequirement() {
	    this(-1, -1, -1, new PercentileRequirement[0], -1);
    }

	public PerformanceRequirement(int average, int max, int totalTime, PercentileRequirement[] percentiles,
            int throughput) {
	    this.average = average;
	    this.max = max;
	    this.totalTime = totalTime;
	    this.percentiles = percentiles;
	    this.throughput = throughput;
    }

    public int getAverage() {
    	return average;
    }

    public int getMax() {
    	return max;
    }

	public void setMax(int max) {
	    this.max = max;
    }
	
    public int getTotalTime() {
    	return totalTime;
    }

    public PercentileRequirement[] getPercentileRequirements() {
    	return percentiles;
    }
    
	public void setPercentileValues(PercentileRequirement[] percentiles) {
	    this.percentiles = percentiles;
    }

    public int getThroughput() {
    	return throughput;
    }
    
	public void setPercentiles(String percentilesSpec) {
	    setPercentileValues(ContiPerfUtil.parsePercentiles(percentilesSpec));
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("average=").append(average);
		builder.append(", max=").append(max);
		builder.append(", totalTime=").append(totalTime);
		builder.append(", throughput=").append(throughput);
		builder.append(", percentiles=").append(percentiles);
		return builder.toString();
	}

}
