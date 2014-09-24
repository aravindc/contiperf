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
 * Indicates that a performance requirement has been missed in a performance test.<br/><br/>
 * Created: 16.04.2010 11:50:09
 * @since 1.03
 * @author Volker Bergmann
 */
public class PerfTestFailure extends AssertionError {

	private static final long serialVersionUID = 8265877929635405862L;

	public PerfTestFailure() {
	    super();
    }

	public PerfTestFailure(String message) {
	    super(message);
    }

	public PerfTestFailure(Throwable cause) {
	    super(cause);
    }

}
