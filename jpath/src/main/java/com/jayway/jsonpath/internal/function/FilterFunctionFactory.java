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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.internal.function.filter.Child;
import com.jayway.jsonpath.internal.function.filter.First;
import com.jayway.jsonpath.internal.function.filter.Last;

/**
 * Implements a factory that given a name of the function will return the Function implementation, or null
 * if the value is not obtained.
 *
 * Leverages the function's name in order to determine which function to execute which is maintained internally
 * here via a static map
 *
 */
public class FilterFunctionFactory {

    public static final Map<String, Class<? extends FilterFunction>> FUNCTIONS;

    static {
        // New functions should be added here and ensure the name is not overridden
        Map<String, Class<? extends FilterFunction>> map = new HashMap<String, Class<? extends FilterFunction>>();

        // Math Functions
        map.put("child", Child.class);
        map.put("first", First.class);
        map.put("last", Last.class);
        
        FUNCTIONS = Collections.unmodifiableMap(map);
    }

    /**
     * Returns the function by name or throws InvalidPathException if function not found.
     *
     * @see #FUNCTIONS
     * @see PathFunction
     *
     * @param name
     *      The name of the function
     *
     * @return
     *      The implementation of a function
     *
     * @throws InvalidPathException
     */
    public static FilterFunction newFunction(String name) throws InvalidPathException {
        Class<? extends FilterFunction> functionClazz = FUNCTIONS.get(name);
        if(functionClazz == null){
            throw new InvalidPathException("Function with name: " + name + " does not exist.");
        } else {
            try {
                return functionClazz.newInstance();
            } catch (Exception e) {
                throw new InvalidPathException("Function of name: " + name + " cannot be created", e);
            }
        }
    }
}
