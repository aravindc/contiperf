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

import java.util.Arrays;
import java.util.List;

import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * JUnit {@link Runner} class for wrapping test classes that are unaware of ContiPerf with 
 * a suite class that adds performance test and requirements configuration.<br/><br/>
 * Created: 02.05.2010 07:32:02
 * @since 1.05
 * @author Volker Bergmann
 */
public class ContiPerfSuiteRunner extends Suite {

	public ContiPerfSuiteRunner(Class<?> suiteClass) throws InitializationError {
	    super(suiteClass, new ContiPerfRunnerBuilder(instantiate(suiteClass)));
    }

	private static Object instantiate(Class<?> suiteClass) {
	    try {
	        return suiteClass.newInstance();
        } catch (Exception e) {
	        throw new RuntimeException(e);
        }
    }

	@Override
    public void run(RunNotifier runnotifier) {
	    super.run(runnotifier);
    }
	
	static class ContiPerfRunnerBuilder extends AllDefaultPossibilitiesBuilder {
		
		protected Object suite;
		
		public ContiPerfRunnerBuilder(Object suite) {
			super(true);
			this.suite = suite;
        }

		@Override
        public Runner runnerForClass(Class<?> testClass) throws Throwable {
			List<RunnerBuilder> builders = Arrays.asList(
					ignoredBuilder(),
					annotatedBuilder(),
					suiteMethodBuilder(),
					junit3Builder(),
					contiPerfSuiteBuilder() // extends and replaces the JUnit4 builder
				);

			for (RunnerBuilder each : builders) {
				Runner runner = each.safeRunnerForClass(testClass);
				if (runner != null)
					return runner;
			}
			return null;
        }

		private RunnerBuilder contiPerfSuiteBuilder() {
	        return new RunnerBuilder() {

				@Override
                public Runner runnerForClass(Class<?> testClass) throws Throwable {
	                return new BlockContiPerfClassRunner(testClass, suite);
                }
	        	
	        };
        }
		
	}
	
}
