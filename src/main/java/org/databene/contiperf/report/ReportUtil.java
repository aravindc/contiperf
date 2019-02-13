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
package org.databene.contiperf.report;

import org.databene.contiperf.PercentileRequirement;
import org.databene.contiperf.PerformanceRequirement;
import org.databene.stat.LatencyCounter;

/**
 * Utility class for report modules.<br><br>
 * Created: 25.01.2011 19:52:59
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class ReportUtil {

	public static boolean success(LatencyCounter counter, PerformanceRequirement requirement) {
		boolean success = averageVerdict(counter, requirement) != Verdict.FAILURE;
		success &= (maxVerdict(counter, requirement) != Verdict.FAILURE);
		success &= (throughputVerdict(counter, requirement) != Verdict.FAILURE);
		success &= (totalTimeVerdict(counter, requirement) != Verdict.FAILURE);
		if (requirement != null) {
			PercentileRequirement[] percentileRequirements = requirement.getPercentileRequirements();
			for (PercentileRequirement percentileRequirement : percentileRequirements)
				success &= (percentileVerdict(counter, percentileRequirement) != Verdict.FAILURE);
		}
		return success;
	}

	public static Verdict totalTimeVerdict(LatencyCounter counter, PerformanceRequirement requirement) {
		if (requirement == null || requirement.getTotalTime() < 0)
			return Verdict.IGNORED;
		return (counter.duration() <= requirement.getTotalTime() ? Verdict.SUCCESS : Verdict.FAILURE);
	}

	public static Verdict maxVerdict(LatencyCounter counter, PerformanceRequirement requirement) {
		if (requirement == null || requirement.getMax() < 0)
			return Verdict.IGNORED;
		return (counter.maxLatency() <= requirement.getMax() ? Verdict.SUCCESS : Verdict.FAILURE);
	}

	public static Verdict throughputVerdict(LatencyCounter counter, PerformanceRequirement requirement) {
		if (requirement == null || requirement.getThroughput() < 0)
			return Verdict.IGNORED;
		return (counter.throughput() >= requirement.getThroughput() ? Verdict.SUCCESS : Verdict.FAILURE);
	}

	public static Verdict averageVerdict(LatencyCounter counter, PerformanceRequirement requirement) {
		if (requirement == null || requirement.getAverage() < 0)
			return Verdict.IGNORED;
		return (counter.averageLatency() <= requirement.getAverage() ? Verdict.SUCCESS : Verdict.FAILURE);
	}

	public static Verdict percentileVerdict(LatencyCounter counter, PercentileRequirement requirement) {
		if (requirement == null || requirement.getMillis() < 0)
			return Verdict.IGNORED;
		return percentileVerdict(counter, requirement.getPercentage(), (long) requirement.getMillis());
	}

	public static Verdict percentileVerdict(LatencyCounter counter, int percentage, Long requiredMillis) {
		if (requiredMillis == null || requiredMillis < 0)
			return Verdict.IGNORED;
		return (counter.percentileLatency(percentage) <= requiredMillis ? Verdict.SUCCESS : Verdict.FAILURE);
	}

}
