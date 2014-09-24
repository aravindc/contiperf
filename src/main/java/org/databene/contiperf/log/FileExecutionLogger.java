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
package org.databene.contiperf.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

import org.databene.contiperf.ExecutionLogger;
import org.databene.contiperf.report.CSVSummaryReportModule;
import org.databene.contiperf.util.ContiPerfUtil;

/**
 * {@link ExecutionLogger} implementation which writes the execution log to a file.<br/><br/>
 * Created: 12.10.09 10:12:39
 * @since 1.0
 * @author Volker Bergmann
 * @deprecated Replaced with {@link CSVSummaryReportModule}
 */
@Deprecated
public class FileExecutionLogger implements ExecutionLogger {
	
	private static final String DEFAULT_FILENAME = "target/contiperf/contiperf.log";
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private static boolean firstCall = true;
	static AtomicLong invocationCount = new AtomicLong();

	public FileExecutionLogger() {
		this(DEFAULT_FILENAME);
    }

	public FileExecutionLogger(String fileName) {
		if (firstCall) {
			createSummaryFile(fileName);
			firstCall = false;
		}
    }

	public void logInvocation(String id, int latency, long startTime) {
		invocationCount.incrementAndGet();
	    System.out.println(id + ',' + latency + ',' + startTime);
    }

	public void logSummary(String id, long elapsedTime, long invocationCount, long startTime) {
		OutputStream out = null;
        String message = id + "," + elapsedTime + ',' 
        	+ invocationCount + ',' + startTime + LINE_SEPARATOR;
		try {
	        out = new FileOutputStream(DEFAULT_FILENAME, true);
	        out.write(message.getBytes());
        } catch (IOException e) {
	        e.printStackTrace();
        } finally {
	        ContiPerfUtil.close(out);
        }
    }
	
	public long invocationCount() {
		return invocationCount.get();
	}
	
	// private helpers -------------------------------------------------------------------------------------------------

	private void createSummaryFile(String fileName) {
	    File file = new File(".", fileName);
	    try {
		    ensureDirectoryExists(file.getParentFile());	
		    if (file.exists())
		    	file.delete();
	    } catch (FileNotFoundException e) {
	    	System.out.println("Unable to create directory: " + file.getAbsolutePath());
	    }
    }

	private void ensureDirectoryExists(File dir) throws FileNotFoundException {
	    File parent = dir.getParentFile();
	    if (!dir.exists()) {
	    	if (parent == null)
	    		throw new FileNotFoundException();
	    	ensureDirectoryExists(parent);
	    	dir.mkdir();
	    }
    }

}
