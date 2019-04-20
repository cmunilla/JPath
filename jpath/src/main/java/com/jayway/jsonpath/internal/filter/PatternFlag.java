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
package com.jayway.jsonpath.internal.filter;

import java.util.regex.Pattern;

public enum PatternFlag {
    UNIX_LINES(Pattern.UNIX_LINES, 'd'),
    CASE_INSENSITIVE(Pattern.CASE_INSENSITIVE, 'i'),
    COMMENTS(Pattern.COMMENTS, 'x'),
    MULTILINE(Pattern.MULTILINE, 'm'),
    DOTALL(Pattern.DOTALL, 's'),
    UNICODE_CASE(Pattern.UNICODE_CASE, 'u'),
    UNICODE_CHARACTER_CLASS(Pattern.UNICODE_CHARACTER_CLASS, 'U');

    private final int code;
    private final char flag;

    private PatternFlag(int code, char flag) {
        this.code = code;
        this.flag = flag;
    }

    public static int parseFlags(char[] flags) {
        int flagsValue = 0;
        for (char flag : flags) {
            flagsValue |= getCodeByFlag(flag);
        }
        return flagsValue;
    }

    public static String parseFlags(int flags) {
        StringBuilder builder = new StringBuilder();
        for (PatternFlag patternFlag : PatternFlag.values()) {
            if ((patternFlag.code & flags) == patternFlag.code) {
                builder.append(patternFlag.flag);
            }
        }
        return builder.toString();
    }

    private static int getCodeByFlag(char flag) {
        for (PatternFlag patternFlag : PatternFlag.values()) {
            if (patternFlag.flag == flag) {
                return patternFlag.code;
            }
        }
        return 0;
    }
}
