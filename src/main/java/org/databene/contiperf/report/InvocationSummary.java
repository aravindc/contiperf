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

/**
 * Holds the data of an invocation summary.<br><br>
 * Created: 29.03.2010 12:38:57
 * @since 1.0
 * @author Volker Bergmann
 */
public class InvocationSummary {
	
	public final String id;
	public final long elapsedTime;
	public final long invocationCount;
	public final long startTime;
	
	public InvocationSummary(String id, long elapsedTime, long invocationCount, long startTime) {
	    this.id = id;
	    this.elapsedTime = elapsedTime;
	    this.invocationCount = invocationCount;
	    this.startTime = startTime;
    }
	
}
