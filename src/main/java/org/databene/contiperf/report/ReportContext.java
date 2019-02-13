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
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Central class for managing report modules as well as aspects that are specific for 
 * a 3rd party testing framework (e.g. JUnit) in which ContiPerf is to be integrated.<br><br>
 * Created: 16.01.2011 07:53:38
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class ReportContext {

	private File reportFolder;
	private Constructor<? extends Error> failureCtor;
	private List<ReportModule> modules;
	
	public ReportContext(File reportFolder, Class<? extends Error> failureClass) {
		this.reportFolder = reportFolder;
		ensureDirectoryExists(reportFolder);
		try {
			this.failureCtor = failureClass.getConstructor(String.class);
		} catch (SecurityException e) {
			throw new RuntimeException("Security exception in String constructor call of " + failureClass, e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Failure classes need a constructor with one String argument, " 
					+ failureClass + " does not have one", e);
		}
		this.modules = new ArrayList<ReportModule>();
	}
	
	public File getReportFolder() {
		return reportFolder;
	}
	
	public void addReportModule(ReportModule module) {
		module.setContext(this);
		modules.add(module);
	}

	public void fail(String message) {
		try {
			throw failureCtor.newInstance(message);
		} catch (Exception e) {
			throw new RuntimeException("Error creating failure. ", e);
		}
	}
	
	public List<ReportModule> getReportModules() {
		return modules;
	}

	@SuppressWarnings("unchecked")
	public <T extends ReportModule> T getReportModule(Class<T> moduleClass) {
		for (ReportModule module : modules)
			if (moduleClass.isAssignableFrom(module.getClass()))
				return (T) module;
		throw new RuntimeException("No module of type '" + moduleClass.getName() + " found. Available: " + modules);
	}
	
	protected void ensureDirectoryExists(File dir) {
	    if (!dir.exists()) {
		    File parent = dir.getParentFile();
	    	if (parent != null)
	    		ensureDirectoryExists(parent);
	    	dir.mkdir();
	    }
    }

}
