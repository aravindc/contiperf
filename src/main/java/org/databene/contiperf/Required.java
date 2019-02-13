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

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies performance requirements for a test.<br><br>
 * Created: 15.10.2009 14:42:57
 * @since 1.0
 * @author Volker Bergmann
 */
@Documented
@Target({ METHOD, TYPE })
@Retention(RUNTIME)
public @interface Required {
	
	/** Requires the average number of test executions per second to be the specified value or higher. */
	int throughput()   default -1;

	/** Requires the average test execution time to be of the specified value or less. */
	int average()      default -1;
	
	/** Requires the execution time of 50% of the test executions of the specified value or less. */
	int median()       default -1;
	
	/** Requires each test execution time of the specified value or less. */
	int max()          default -1;
	
	/** Requires the total elapsed time from the beginning of the first test execution to the end of the last one.
	    This does not mean the accumulated response time, but the duration of the test run. */
	int totalTime()    default -1;
	
	/** Requires the execution time of 90% of the test executions of the specified value or less. */
	int percentile90() default -1;

	/** Requires the execution time of 95% of the test executions of the specified value or less. */
	int percentile95() default -1;
	
	/** Requires the execution time of 99% of the test executions of the specified value or less. */
	int percentile99() default -1;
	
	/** Defines a custom set of percentile requirements as a comma-separated list of percentile:millisecond pairs,
	 *  for example 80:300,96:2000 to require 80% of the invocations to take 300 ms or less and 96% to take 2000 ms 
	 *  or less. */
	String percentiles() default "";
	
}
