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
package com.jayway.jsonpath.internal;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.ParseContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.jayway.jsonpath.internal.Utils.notEmpty;
import static com.jayway.jsonpath.internal.Utils.notNull;

public class ParseContextImpl implements ParseContext {

    private final Configuration configuration;

    public ParseContextImpl() {
        this(Configuration.defaultConfiguration());
    }

    public ParseContextImpl(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public DocumentContext parse(Object json) {
        notNull(json, "json object can not be null");
        return new JsonContext(json, configuration);
    }

    @Override
    public DocumentContext parse(String json) {
        notEmpty(json, "json string can not be null or empty");
        Object obj = configuration.jsonProvider().parse(json);
        return new JsonContext(obj, configuration);
    }

    @Override
    public DocumentContext parse(InputStream json) {
        return parse(json, "UTF-8");
    }

    @Override
    public DocumentContext parse(InputStream json, String charset) {
        notNull(json, "json input stream can not be null");
        notNull(charset, "charset can not be null");
        try {
            Object obj = configuration.jsonProvider().parse(json, charset);
            return new JsonContext(obj, configuration);
        } finally {
            Utils.closeQuietly(json);
        }
    }

    @Override
    public DocumentContext parse(File json) throws IOException {
        notNull(json, "json file can not be null");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(json);
            return parse(fis);
        } finally {
            Utils.closeQuietly(fis);
        }
    }

    @Override
    @Deprecated
    public DocumentContext parse(URL url) throws IOException {
        notNull(url, "url can not be null");
        InputStream fis = null;
        try {
            fis = url.openStream();
            return parse(fis);
        } finally {
            Utils.closeQuietly(fis);
        }
    }

}
