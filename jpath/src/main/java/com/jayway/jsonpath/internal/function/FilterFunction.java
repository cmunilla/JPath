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
package com.jayway.jsonpath.internal.function;

import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.internal.EvaluationContext;
import com.jayway.jsonpath.internal.PathRef;

/**
 */
public interface FilterFunction {

    /**
     * Applies the Filter passed as parameter on the specified Object model and returns 
     * the set of compliant object(s)
     *
     * @param currentPath The current path location inclusive of the function name
     * @param parent The path location above the current function     
     * @param model The JSON model as input to this particular function     
     * @param ctx Evaluation context, state bag used as the path is traversed, 
     * maintains the result of executing 
     * @param filter the Filter to be applied
     * 
     * @return the set of objects that complies the specified filter
     */
    Object invoke(String currentPath, PathRef parent, Object model, EvaluationContext ctx, Filter filter);
}
