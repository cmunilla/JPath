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

import com.jayway.jsonpath.InvalidPathException;

import static java.lang.Character.isDigit;

public class ArraySliceOperation {

    public enum Operation {
        SLICE_FROM,
        SLICE_TO,
        SLICE_BETWEEN
    }

    private final Integer from;
    private final Integer to;
    private final Operation operation;

    private ArraySliceOperation(Integer from, Integer to, Operation operation) {
        this.from = from;
        this.to = to;
        this.operation = operation;
    }

    public Integer from() {
        return from;
    }

    public Integer to() {
        return to;
    }

    public Operation operation() {
        return operation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(from == null ? "" : from.toString());
        sb.append(":");
        sb.append(to == null ? "" : to.toString());
        sb.append("]");

        return sb.toString();
    }

    public static ArraySliceOperation parse(String operation){
        //check valid chars
        for (int i = 0; i < operation.length(); i++) {
            char c = operation.charAt(i);
            if( !isDigit(c)  && c != '-' && c != ':'){
                throw new InvalidPathException("Failed to parse SliceOperation: " + operation);
            }
        }
        String[] tokens = operation.split(":");

        Integer tempFrom = tryRead(tokens, 0);
        Integer tempTo = tryRead(tokens, 1);
        Operation tempOperation;

        if (tempFrom != null && tempTo == null) {
            tempOperation = Operation.SLICE_FROM;
        } else if (tempFrom != null) {
            tempOperation = Operation.SLICE_BETWEEN;
        } else if (tempTo != null) {
            tempOperation = Operation.SLICE_TO;
        } else {
            throw new InvalidPathException("Failed to parse SliceOperation: " + operation);
        }

        return new ArraySliceOperation(tempFrom, tempTo, tempOperation);
    }

    private static Integer tryRead(String[] tokens, int idx){
        if(tokens.length > idx){
            if(tokens[idx].equals("")){
                return null;
            }
            return Integer.parseInt(tokens[idx]);
        } else {
            return null;
        }
    }
}
