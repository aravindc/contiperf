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

import java.io.File;

import org.databene.contiperf.report.HtmlReportModule;
import org.databene.contiperf.report.ReportContext;

/**
 * Parses and provides the ContiPerf configuration.<br><br>
 * Created: 18.10.2009 06:46:31
 * @since 1.0
 * @author Volker Bergmann
 */
public class Config {

	private static final String DEFAULT_REPORT_FOLDER_NAME = "contiperf-report";
	public static final String SYSPROP_ACTIVE = "contiperf.active";
	public static final String SYSPROP_CONFIG_FILENAME = "contiperf.config";
	public static final String DEFAULT_CONFIG_FILENAME = "contiperf.config.xml";
	
	public boolean active() {
		String sysprop = System.getProperty(SYSPROP_ACTIVE);
		return (sysprop == null || !"false".equalsIgnoreCase(sysprop.trim()));
    }

	// helpers ---------------------------------------------------------------------------------------------------------

	public static String getConfigFileName() {
		String filename = System.getProperty(SYSPROP_CONFIG_FILENAME);
		if (filename == null || filename.trim().length() == 0)
			filename = DEFAULT_CONFIG_FILENAME;
		return filename;
	}

	private static Config instance;
	
	public static Config instance() {
	    if (instance == null)
	    	instance = new Config();
	    return instance;
    }

	public int getInvocationCount(String testId) {
		// TODO v2.x read config file and support override of annotation settings
		return -1;
    }

	public ReportContext createDefaultReportContext(Class<? extends AssertionError> failureClass) {
		File reportFolder = getReportFolder();
		ReportContext context = new ReportContext(reportFolder, failureClass);
		context.addReportModule(new HtmlReportModule());
		return context;
    }

	public File getReportFolder() {
		File targetDir = new File("target");
		return (targetDir.exists() ? 
				new File(targetDir, DEFAULT_REPORT_FOLDER_NAME) : 
				new File(DEFAULT_REPORT_FOLDER_NAME)); 
		// TODO v2.x determine from config file
		//return reportFolder;
	}

}
