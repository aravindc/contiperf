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

import java.io.Closeable;

/**
 * Parent interface for ContiPerf adapters that perform, if necessary, parameter generation 
 * and the actual invocation of a target service.<br><br>
 * Created: 03.06.2010 19:18:21
 * @since 1.07
 * @author Volker Bergmann
 */
public interface InvocationRunner extends Runnable, Closeable {
	public void close();
}
