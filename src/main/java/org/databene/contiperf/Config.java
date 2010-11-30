/*
 * (c) Copyright 2009-2010 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU Lesser General Public License (LGPL), Eclipse Public License (EPL) 
 * and the BSD License.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.contiperf;

import org.databene.contiperf.log.FileExecutionLogger;

/**
 * Parses and provides the ContiPerf configuration.<br/><br/>
 * Created: 18.10.2009 06:46:31
 * @since 1.0
 * @author Volker Bergmann
 */
public class Config {

	public static final String SYSPROP_ACTIVE = "contiperf.active";
	public static final String SYSPROP_CONFIG_FILENAME = "contiperf.config";
	public static final String DEFAULT_CONFIG_FILENAME = "contiperf.config.xml";
	
	public boolean active() {
		String sysprop = System.getProperty(SYSPROP_ACTIVE);
		return (sysprop == null || !"false".equals(sysprop.trim().toLowerCase()));
    }

	// helpers ---------------------------------------------------------------------------------------------------------

	public static String getConfigFileName() {
		String filename = System.getProperty(SYSPROP_CONFIG_FILENAME);
		if (filename == null || filename.trim().length() == 0)
			filename = DEFAULT_CONFIG_FILENAME;
		return filename;
	}

	private static Config instance;
	
	public static Config instance() {
	    if (instance == null)
	    	instance = new Config();
	    return instance;
    }

	public int getInvocationCount(String testId) {
		// TODO v1.x read config file and support override of annotation settings
		return -1;
    }

	public ExecutionLogger createDefaultExecutionLogger() {
		return new FileExecutionLogger();
    }

}
