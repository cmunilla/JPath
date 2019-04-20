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
 */
package com.jayway.jsonpath.internal.function.filter;

import java.util.Collection;

import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPathException;
import com.jayway.jsonpath.Predicate.PredicateContext;
import com.jayway.jsonpath.internal.EvaluationContext;
import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.function.FilterFunction;
import com.jayway.jsonpath.internal.path.PredicateContextImpl;

/**
 * Applies on the children of an Object (JSON) or an Array
 */ 
public class Child  implements FilterFunction {

    @Override
    public Object invoke(String currentPath, PathRef parent, Object model, EvaluationContext ctx, Filter filter) {    	
    	Object resultArray = ctx.configuration().jsonProvider().createArray();
        int idx = 0; 
        if(ctx.configuration().jsonProvider().isArray(model)){
            Iterable<?> objects = ctx.configuration().jsonProvider().toIterable(model);
            for (Object obj : objects) {
	           	 boolean res = true;
	           	 if(filter != null) {		
	                 PredicateContext pc = new PredicateContextImpl(obj, ctx.rootDocument(), ctx.configuration(), null);
	                 res = filter.apply(pc);
	           	 }
	           	 if(res) {
	           		 ctx.configuration().jsonProvider().setArrayIndex(resultArray, idx, obj);
	           		 idx+=1;
	           	 }
            }
            switch(ctx.configuration().jsonProvider().length(resultArray)) {
            case 0: 
            	return null;
            case 1:
            	//return ctx.configuration().jsonProvider().getArrayIndex(resultArray, 0);
            default: 
            	return resultArray;
            }
        } else if(ctx.configuration().jsonProvider().isMap(model)){
            Collection<String> keys = ctx.configuration().jsonProvider().getPropertyKeys(model);
            for (String key : keys) {
            	 Object obj =  ctx.configuration().jsonProvider().getMapValue(model, key);
            	 boolean res = true;
            	 if(filter != null) {		
                     PredicateContext pc = new PredicateContextImpl(obj, ctx.rootDocument(), ctx.configuration(), null);
             	     res = filter.apply(pc);
            	 }
            	 if(res) {
            		 ctx.configuration().jsonProvider().setArrayIndex(resultArray, idx, obj);
            		 idx+=1;
            	 }
            }
            switch(ctx.configuration().jsonProvider().length(resultArray)) {
            case 0: 
            	return null;
            case 1:
            	//return ctx.configuration().jsonProvider().getArrayIndex(resultArray, 0);
            default: 
            	return resultArray;
            }
        }
        throw new JsonPathException("Child function attempted to be applied  value using empty array");
    }
}
