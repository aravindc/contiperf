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

import java.lang.reflect.Method;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerScheduler;
import org.junit.runners.model.Statement;

/**
 * Executes all tests of one test class concurrently. 
 * Warning: This is an experimental implementation, so its behaviour may change in future versions.<br><br>
 * Created: 07.04.2012 17:18:54
 * @since 2.1.0
 * @author Volker Bergmann
 */
public class ParallelRunner extends BlockJUnit4ClassRunner {

	public ParallelRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	protected Statement childrenInvoker(final RunNotifier notifier) {
		return new Statement() {
			@Override
			public void evaluate() {
				runChildren(notifier);
			}
		};
	}

	private void runChildren(final RunNotifier notifier) {
		RunnerScheduler scheduler = new ParallelScheduler();
		for (FrameworkMethod method : getChildren())
 			scheduler.schedule(new ChildRunnable(method, notifier));
		scheduler.finished();
	}

	public class ChildRunnable implements Runnable {

		FrameworkMethod method;
		RunNotifier notifier;
		
		public ChildRunnable(FrameworkMethod method, RunNotifier notifier) {
			this.method = method;
			this.notifier = notifier;
		}

		public void run() {
			ParallelRunner.this.runChild(method, notifier);
		}
		
		@Override
		public String toString() {
			Method realMethod = method.getMethod();
			return realMethod.getDeclaringClass().getSimpleName() + '.' + realMethod.getName() + "()";
		}
	}

}
