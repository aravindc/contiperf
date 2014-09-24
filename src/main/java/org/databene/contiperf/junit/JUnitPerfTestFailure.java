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

import junit.framework.AssertionFailedError;

/**
 * Reports a performance test failure to the JUnit test framework.<br/><br/>
 * Created: 16.01.2011 15:01:16
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class JUnitPerfTestFailure extends AssertionFailedError {

	private static final long serialVersionUID = -6164364500484072786L;

	public JUnitPerfTestFailure() {
		super();
	}

	public JUnitPerfTestFailure(String message) {
		super(message);
	}

}
