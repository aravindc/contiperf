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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.databene.contiperf.ExecutionConfig;
import org.databene.contiperf.PerformanceRequirement;
import org.databene.stat.LatencyCounter;

/**
 * {@link ReportModule} which creates a CSV file that reports how often (2nd column) 
 * which latency (1st column) was measured.<br/><br/>
 * Created: 16.01.2011 19:22:23
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class CSVLatencyReportModule extends AbstractReportModule {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private File file;
	OutputStream out;

	public CSVLatencyReportModule() {
		this.file = null;
    }



	// ReportModule interface implementation ---------------------------------------------------------------------------
	
	@Override
	public String getReportReferenceLabel(String serviceId) {
		return (serviceId == null ? null : "Latency distribution as CSV");
	}
	
	@Override
	public String getReportReference(String serviceId) {
		return (serviceId == null ? null : filename(serviceId));
	}
	
	@Override
	public void starting(String serviceId) {
		file = new File(context.getReportFolder(), filename(serviceId));
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			writeHeader(serviceId, out);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void completed(String serviceId, LatencyCounter[] counters, ExecutionConfig executionConfig, PerformanceRequirement requirement) {
		writeStats(serviceId, counters);
		try {
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing " + file, e);
		}
    }



	// helper methods --------------------------------------------------------------------------------------------------

	private static void writeHeader(String serviceId, OutputStream out) {
        String line = "latency,sampleCount" + LINE_SEPARATOR;
		try {
			out.write(line.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeStats(String serviceId, LatencyCounter[] counters) {
		try {
			LatencyCounter counter = counters[0];
			for (long i = counter.minLatency(); i <= counter.maxLatency(); i++) {
				String line = i + "," + counter.getLatencyCount(i) + LINE_SEPARATOR;
		        out.write(line.getBytes());
			}
        } catch (IOException e) {
	        e.printStackTrace();
        }
	}
	
	private static String filename(String serviceId) {
		return serviceId + ".stat.csv";
	}
	
}
