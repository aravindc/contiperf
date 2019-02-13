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

/**
 * Indicates that an error occurred in performance test execution.<br><br>
 * Created: 16.04.2010 11:50:32
 * @since 1.03
 * @author Volker Bergmann
 */
public class PerfTestExecutionError extends PerfTestException {

	private static final long serialVersionUID = -8918904442269990674L;

	public PerfTestExecutionError() {
	    super();
    }

	public PerfTestExecutionError(String message, Throwable cause) {
	    super(message, cause);
    }

	public PerfTestExecutionError(String message) {
	    super(message);
    }

	public PerfTestExecutionError(Throwable cause) {
	    super(cause);
    }

}
