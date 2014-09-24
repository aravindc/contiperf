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
package org.databene.contiperf.junit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.databene.contiperf.Config;
import org.databene.contiperf.report.LoggerModuleAdapter;
import org.databene.contiperf.report.ReportContext;
import org.databene.contiperf.report.ReportModule;

/**
 * JUnit-specific implementation of the ReportContext interface.<br/><br/>
 * Created: 16.01.2011 15:12:24
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class JUnitReportContext extends ReportContext {
	
	public static final Class<PerformanceRequirementFailedError> FAILURE_CLASS = PerformanceRequirementFailedError.class;

	public JUnitReportContext() {
		super(Config.instance().getReportFolder(), FAILURE_CLASS);
	}
	
	public static JUnitReportContext createInstance(Object suite) {
	    List<ReportModule> modules = parseReportModules(suite);
	    JUnitReportContext context = new JUnitReportContext();
		for (ReportModule module : modules)
	    	context.addReportModule(module);
		return context;
    }

	@SuppressWarnings("deprecation")
	private static List<ReportModule> parseReportModules(Object suite) {
		List<ReportModule> modules = new ArrayList<ReportModule>();
		if (suite != null) {
		    for (Field field : suite.getClass().getFields()) {
	    		try {
	    			if (ReportModule.class.isAssignableFrom(field.getType()))
	    				modules.add((ReportModule) field.get(suite));
	    			else if (org.databene.contiperf.ExecutionLogger.class.isAssignableFrom(field.getType()))
	    				modules.add(new LoggerModuleAdapter((org.databene.contiperf.ExecutionLogger) field.get(suite)));
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            }
		    }	
		}
		// TODO v2.x support annotation based ReportModule configuration
	    return modules;
	}
	
}
