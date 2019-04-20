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
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.internal.function.Parameter;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;

public class PathTokenFactory {

    public static RootPathToken createRootPathToken(char token) {
        return new RootPathToken(token);
    }

    public static PathToken createSinglePropertyPathToken(String property, char stringDelimiter) {
        return new PropertyPathToken(singletonList(property), stringDelimiter);
    }

    public static PathToken createPropertyPathToken(List<String> properties, char stringDelimiter) {
        return new PropertyPathToken(properties, stringDelimiter);
    }

    public static PathToken createSliceArrayPathToken(final ArraySliceOperation arraySliceOperation) {
        return new ArraySliceToken(arraySliceOperation);
    }

    public static PathToken createIndexArrayPathToken(final ArrayIndexOperation arrayIndexOperation) {
        return new ArrayIndexToken(arrayIndexOperation);
    }

    public static PathToken createWildCardPathToken() {
        return new WildcardPathToken();
    }

    public static PathToken crateScanToken() {
        return new ScanPathToken();
    }

    public static PathToken createPredicatePathToken(Collection<Predicate> predicates) {
        return new PredicatePathToken(predicates);
    }

    public static PathToken createPredicatePathToken(Predicate predicate) {
        return new PredicatePathToken(predicate);
    }

    public static PathToken createFunctionPathToken(String function, List<Parameter> parameters) {
        return new FunctionPathToken(function, parameters);
    }

    public static PathToken createFilterPathToken(String function, Filter filter) {
        return new FilterPathToken(function, filter);
    }
}
