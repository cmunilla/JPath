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
package com.jayway.jsonpath.internal.function.numeric;

/**
 * Provides the average of a series of numbers in a JSONArray
 *
 * Created by mattg on 6/26/15.
 */
public class Average extends AbstractAggregation {

    private Double summation = 0d;
    private Double count = 0d;

    @Override
    protected void next(Number value) {
        count++;
        summation += value.doubleValue();
    }

    @Override
    protected Number getValue() {
        if (count != 0d) {
            return summation / count;
        }
        return 0d;
    }
}
