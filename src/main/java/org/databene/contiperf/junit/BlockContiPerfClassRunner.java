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

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.rules.MethodRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * Replaces the standard {@link BlockJUnit4ClassRunner} for supporting ContiPerf features 
 * in performance test suites.<br><br>
 * Created: 02.05.2010 07:54:08
 * @since 1.05
 * @author Volker Bergmann
 */
@SuppressWarnings("deprecation")
public class BlockContiPerfClassRunner extends BlockJUnit4ClassRunner {
	
	protected ContiPerfRule rule;

	public BlockContiPerfClassRunner(Class<?> testClass, Object suite) throws InitializationError {
	    super(testClass);
	    rule = new ContiPerfRule(JUnitReportContext.createInstance(suite), suite);
    }
	
	/** method taken as is from BlockJUnit4ClassRunner 4.7 
	 * in order to preserve its functionality over following versions */
	@Override
	protected Statement methodBlock(FrameworkMethod method) {
		Object test;
		try {
			test= new ReflectiveCallable() {
				@Override
				protected Object runReflectiveCall() throws Throwable {
					return createTest();
				}
			}.run();
		} catch (Throwable e) {
			return new Fail(e);
		}

		Statement statement= methodInvoker(method, test);
		statement= possiblyExpectingExceptions(method, test, statement);
		statement= withPotentialTimeout(method, test, statement);
		statement= withRules(method, test, statement);
		statement= withBefores(method, test, statement);
		statement= withAfters(method, test, statement);
		return statement;
	}
	
	/** method taken as is from BlockJUnit4ClassRunner 4.7 
	 * in order to preserve its functionality over following versions */
	private Statement withRules(FrameworkMethod method, Object target,
			Statement statement) {
		Statement result= statement;
		for (MethodRule each : rules(target))
			result= each.apply(result, method, target);
		return result;
	}
	
	/** actual override feature of this class */
	@Override
	protected List<MethodRule> rules(Object test) {
	    boolean configured = false;
		List<MethodRule> rules = new ArrayList<MethodRule>();
		for (FrameworkField each : ruleFields()) {
			MethodRule targetRule = createRule(test, each);
	    	if (targetRule instanceof ContiPerfRule) {
	    		ContiPerfRule cpRule = (ContiPerfRule) targetRule;
				if (cpRule.getContext().getReportModules().size() == 0)
	    			cpRule.setContext(rule.getContext());
	    		configured = true;
	    	}
			rules.add(targetRule);
		}
	    if (!configured)
	    	rules.add(rule);
		return rules;
	}

	/** method taken as is from BlockJUnit4ClassRunner 4.7 
	 * in order to preserve its functionality over following versions */
	private List<FrameworkField> ruleFields() {
		return getTestClass().getAnnotatedFields(Rule.class);
	}

	/** method taken as is from BlockJUnit4ClassRunner 4.7 
	 * in order to preserve its functionality over following versions */
	private static MethodRule createRule(Object test,
			FrameworkField each) {
		try {
			return (MethodRule) each.get(test);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(
					"How did getFields return a field we couldn't access?");
		}
	}

}
