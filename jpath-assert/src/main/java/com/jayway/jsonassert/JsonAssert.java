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
package com.jayway.jsonassert;


import com.jayway.jsonassert.impl.JsonAsserterImpl;
import com.jayway.jsonassert.impl.matcher.CollectionMatcher;
import com.jayway.jsonassert.impl.matcher.IsCollectionWithSize;
import com.jayway.jsonassert.impl.matcher.IsEmptyCollection;
import com.jayway.jsonassert.impl.matcher.IsMapContainingKey;
import com.jayway.jsonassert.impl.matcher.IsMapContainingValue;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;

public class JsonAssert {

    /**
     * Creates a JSONAsserter
     *
     * @param json the JSON document to create a JSONAsserter for
     * @return a JSON asserter initialized with the provided document
     * @throws ParseException when the given JSON could not be parsed
     */
    public static JsonAsserter with(String json) {
        return new JsonAsserterImpl(JsonPath.parse(json).json());
    }

    /**
     * Creates a JSONAsserter
     *
     * @param reader the reader of the json document
     * @return a JSON asserter initialized with the provided document
     * @throws ParseException when the given JSON could not be parsed
     */
    public static JsonAsserter with(Reader reader) throws IOException {
        return new JsonAsserterImpl(JsonPath.parse(convertReaderToString(reader)).json());

    }

    /**
     * Creates a JSONAsserter
     *
     * @param is the input stream
     * @return a JSON asserter initialized with the provided document
     * @throws ParseException when the given JSON could not be parsed
     */
    public static JsonAsserter with(InputStream is) throws IOException {
        Reader reader = new InputStreamReader(is);
        return with(reader);
    }

    //Matchers

    public static CollectionMatcher collectionWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsCollectionWithSize(sizeMatcher);
    }

    public static Matcher<Map<String, ?>> mapContainingKey(Matcher<String> keyMatcher) {
        return new IsMapContainingKey(keyMatcher);
    }

    public static <V> Matcher<? super Map<?, V>> mapContainingValue(Matcher<? super V> valueMatcher) {
        return new IsMapContainingValue<V>(valueMatcher);
    }

    public static Matcher<Collection<Object>> emptyCollection() {
        return new IsEmptyCollection<Object>();
    }

    private static String convertReaderToString(Reader reader)
            throws IOException {

        if (reader != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                reader.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }


}
