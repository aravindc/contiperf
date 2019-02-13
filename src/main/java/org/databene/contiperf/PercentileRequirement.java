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
 * Defines percentile performance requests on a test.<br><br>
 * Created: 18.10.2009 06:25:42
 * @since 1.0
 * @author Volker Bergmann
 */
public class PercentileRequirement {
	
	private int percentage;
	private int millis;
	
	public PercentileRequirement() {
	    this(-1, -1);
    }

	public PercentileRequirement(int percentage, int millis) {
	    this.percentage = percentage;
	    this.millis = millis;
    }

	public int getPercentage() {
    	return percentage;
    }

	public void setPercentage(int percentage) {
    	this.percentage = percentage;
    }

	public int getMillis() {
    	return millis;
    }
	
	public void setMillis(int millis) {
    	this.millis = millis;
    }

}
