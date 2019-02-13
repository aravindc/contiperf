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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.databene.contiperf.ExecutionConfig;
import org.databene.contiperf.PerformanceRequirement;
import org.databene.contiperf.util.ContiPerfUtil;
import org.databene.stat.LatencyCounter;

/**
 * Writes summary information of the ContiPerf to a CSV file.<br><br>
 * Created: 16.01.2011 11:03:46
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class CSVSummaryReportModule extends AbstractReportModule {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private static Set<File> usedFiles = new HashSet<File>();

	private File file;

	public CSVSummaryReportModule() {
		this.file = null;
    }



	// ReportModule interface implementation ---------------------------------------------------------------------------
	
	@Override
	public String getReportReferenceLabel(String serviceId) {
		return (serviceId == null ? "CSV Summary" : null);
	}
	
	@Override
	public String getReportReference(String serviceId) {
		return (serviceId == null ? filename() : null);
	}
	
	@Override
	public void starting(String serviceId) {
		synchronized (usedFiles) {
			file = new File(context.getReportFolder(), filename());
			if (!usedFiles.contains(file) && file.exists()) {
				if (!file.delete())
					throw new RuntimeException("Previous file version could not be deleted: " + file);
				usedFiles.add(file);
			}
			if (!file.exists())
				writeHeader(serviceId);
		}
	}

	@Override
	public void completed(String serviceId, LatencyCounter[] counters, ExecutionConfig executionConfig, PerformanceRequirement requirement) {
		writeStats(serviceId, counters);
    }



	// helper methods --------------------------------------------------------------------------------------------------

	private void writeHeader(String serviceId) {
		OutputStream out = null;
		try {
	        out = new FileOutputStream(file, true);
	        String line = "serviceId,startTime,duration,invocations,min,average,median,90%,95%,99%,max" + LINE_SEPARATOR;
			out.write(line.getBytes());
        } catch (IOException e) {
	        e.printStackTrace();
        } finally {
	        ContiPerfUtil.close(out);
        }
	}

	private void writeStats(String serviceId, LatencyCounter[] counters) {
		OutputStream out = null;
		try {
	        out = new FileOutputStream(file, true);
	        DecimalFormat decForm = new DecimalFormat("0.#", DecimalFormatSymbols.getInstance(Locale.US));
	        decForm.setGroupingUsed(false);
	        LatencyCounter mainCounter = counters[0];
			String avg = decForm.format(mainCounter.averageLatency());
			String message = serviceId + ',' + mainCounter.getStartTime() + ',' + 
	        	mainCounter.duration() + ',' + mainCounter.sampleCount() + ',' + 
	        	mainCounter.minLatency() + ',' + avg + ',' + 
	        	mainCounter.percentileLatency(50) + ',' + mainCounter.percentileLatency(90) + ',' + 
	        	mainCounter.percentileLatency(95) + ',' + mainCounter.percentileLatency(99) + ',' + 
	        	mainCounter.maxLatency() + LINE_SEPARATOR;
	        out.write(message.getBytes());
        } catch (IOException e) {
	        e.printStackTrace();
        } finally {
	        ContiPerfUtil.close(out);
        }
	}
	
	private static String filename() {
		return "summary.csv";
	}
	
}
