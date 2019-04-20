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

import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.internal.EvaluationContext;
import com.jayway.jsonpath.internal.PathRef;

/**
 * Applies on the children of an Object (JSON) or an Array
 */ 
public class Last extends Child {

    @Override
    public Object invoke(String currentPath, PathRef parent, Object model, EvaluationContext ctx, Filter filter) {   	
    	Object res = super.invoke(currentPath, parent, model, ctx, filter);
    	if(res!=null && ctx.configuration().jsonProvider().isArray(res)){	
    		int length = ctx.configuration().jsonProvider().length(res);
            if(length  > 0) {
            	return ctx.configuration().jsonProvider().getArrayIndex(res, length-1);
            }
    	}	
    	return res;		
    }
}
