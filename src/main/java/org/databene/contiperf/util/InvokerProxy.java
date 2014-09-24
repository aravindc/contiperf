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
package org.databene.contiperf.util;

import org.databene.contiperf.Invoker;

/**
 * {@link Invoker} implementation which acts as a proxy to another Invoker.<br/><br/>
 * Created: 29.03.2010 11:41:54
 * @since 1.0
 * @author Volker Bergmann
 */
public class InvokerProxy implements Invoker {

	protected final Invoker target;

	public InvokerProxy(Invoker target) {
	    this.target = target;
    }

	public String getId() {
	    return target.getId();
    }

	public Object invoke(Object[] args) throws Exception {
	    return target.invoke(args);
    }
	
}
