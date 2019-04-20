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
import com.jayway.jsonpath.internal.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;

public class ArrayIndexOperation {

    private final static Pattern COMMA = Pattern.compile("\\s*,\\s*");

    private final List<Integer> indexes;

    private ArrayIndexOperation(List<Integer> indexes) {
        this.indexes = Collections.unmodifiableList(indexes);
    }

    public List<Integer> indexes() {
        return indexes;
    }

    public boolean isSingleIndexOperation(){
        return indexes.size() == 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(Utils.join(",", indexes));
        sb.append("]");

        return sb.toString();
    }

    public static ArrayIndexOperation parse(String operation) {
        //check valid chars
        for (int i = 0; i < operation.length(); i++) {
            char c = operation.charAt(i);
            if (!isDigit(c) && c != ',' && c != ' ' && c != '-') {
                throw new InvalidPathException("Failed to parse ArrayIndexOperation: " + operation);
            }
        }
        String[] tokens = COMMA.split(operation, -1);

        List<Integer> tempIndexes = new ArrayList<Integer>(tokens.length);
        for (String token : tokens) {
            tempIndexes.add(parseInteger(token));
        }

        return new ArrayIndexOperation(tempIndexes);
    }

    private static Integer parseInteger(String token) {
        try {
            return Integer.parseInt(token);
        } catch (Exception e){
            throw new InvalidPathException("Failed to parse token in ArrayIndexOperation: " + token, e);
        }
    }
}
