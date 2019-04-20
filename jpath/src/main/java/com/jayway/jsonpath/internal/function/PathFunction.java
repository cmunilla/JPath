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
package com.jayway.jsonpath.internal.function;

import com.jayway.jsonpath.internal.EvaluationContext;
import com.jayway.jsonpath.internal.PathRef;

import java.util.List;

/**
 * Defines the pattern by which a function can be executed over the result set in the particular path
 * being grabbed.  The Function's input is the content of the data from the json path selector and its output
 * is defined via the functions behavior.  Thus transformations in types can take place.  Additionally, functions
 * can accept multiple selectors in order to produce their output.
 *
 * Created by matt@mjgreenwood.net on 6/26/15.
 */
public interface PathFunction {

    /**
     * Invoke the function and output a JSON object (or scalar) value which will be the result of executing the path
     *
     * @param currentPath
     *      The current path location inclusive of the function name
     * @param parent
     *      The path location above the current function
     *
     * @param model
     *      The JSON model as input to this particular function
     *
     * @param ctx
     *      Eval context, state bag used as the path is traversed, maintains the result of executing
     *
     * @param parameters
     * @return result
     */
    Object invoke(String currentPath, PathRef parent, Object model, EvaluationContext ctx, List<Parameter> parameters);
}
