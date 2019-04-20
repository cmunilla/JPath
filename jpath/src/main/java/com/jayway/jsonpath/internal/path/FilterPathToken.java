/*
 * Copyright 2011 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This source code was originally provided by the Jayway JsonPath project
 * and might have been modified in the JPath project
 */
package com.jayway.jsonpath.internal.path;

import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.function.FilterFunction;
import com.jayway.jsonpath.internal.function.FilterFunctionFactory;
import com.jayway.jsonpath.internal.function.PathFunctionFactory;
import com.jayway.jsonpath.spi.json.JsonProvider;

/**
 * Token representing a Function call to one of the functions produced via the FunctionFactory
 *
 * @see PathFunctionFactory
 */
public class FilterPathToken extends PathToken {

    private final String functionName;
    private final String pathFragment;
    private Filter functionFilter;

    public FilterPathToken(String pathFragment, Filter filter) {
        this.pathFragment = pathFragment + ((filter != null) ? "(...)" : "()");
        if(null != pathFragment){
            functionName = pathFragment;
            functionFilter = filter;
        } else {
            functionName = null;
            functionFilter = null;
        }
    }

    @Override
    public void evaluate(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {
        FilterFunction filterFunction = FilterFunctionFactory.newFunction(functionName);
        Object result = filterFunction.invoke(currentPath, parent, model, ctx, functionFilter);
        if(result != null) {
        	ctx.addResult(currentPath + "." + functionName, parent, result);
	        if (!isLeaf()) {
	            next().evaluate(currentPath, parent, result, ctx);
	        }
        } else {
        	ctx.addResult(currentPath + "." + functionName, parent, JsonProvider.UNDEFINED);
        }
    }

    @Override
    public boolean isTokenDefinite() {
        return true;
    }

    @Override
    public String getPathFragment() {
        return "." + pathFragment;
    }
}
