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

import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPathException;
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.internal.Path;
import com.jayway.jsonpath.internal.path.PathCompiler;
import static com.jayway.jsonpath.internal.filter.ValueNodes.*;

public abstract class ValueNode {

    public abstract Class<?> type(Predicate.PredicateContext ctx);

    public boolean isPatternNode() {
        return false;
    }

    public PatternNode asPatternNode() {
        throw new InvalidPathException("Expected regexp node");
    }

    public boolean isPathNode() {
        return false;
    }

    public PathNode asPathNode() {
        throw new InvalidPathException("Expected path node");
    }

    public boolean isNumberNode() {
        return false;
    }

    public NumberNode asNumberNode() {
        throw new InvalidPathException("Expected number node");
    }

    public boolean isStringNode() {
        return false;
    }

    public StringNode asStringNode() {
        throw new InvalidPathException("Expected string node");
    }

    public boolean isBooleanNode() {
        return false;
    }

    public BooleanNode asBooleanNode() {
        throw new InvalidPathException("Expected boolean node");
    }

    public boolean isJsonNode() {
        return false;
    }

    public JsonNode asJsonNode() {
        throw new InvalidPathException("Expected json node");
    }

    public boolean isPredicateNode() {
        return false;
    }

    public PredicateNode asPredicateNode() {
        throw new InvalidPathException("Expected predicate node");
    }

    public boolean isValueListNode() {
        return false;
    }

    public ValueListNode asValueListNode() {
        throw new InvalidPathException("Expected value list node");
    }

    public boolean isNullNode() {
        return false;
    }

    public NullNode asNullNode() {
        throw new InvalidPathException("Expected null node");
    }

    public UndefinedNode asUndefinedNode() {
        throw new InvalidPathException("Expected undefined node");
    }

    public boolean isUndefinedNode() {
        return false;
    }

    public boolean isClassNode() {
        return false;
    }

    public ClassNode asClassNode() {
        throw new InvalidPathException("Expected class node");
    }

    private static boolean isPath(Object o) {
        if(o == null || !(o instanceof String)){
            return false;
        }
        String str = o.toString().trim();
        if (str.length() <= 0) {
            return false;
        }
        char c0 = str.charAt(0);
        if(c0 == '@' || c0 == '$'){
            try {
                PathCompiler.compile(str);
                return true;
            } catch(Exception e){
                return false;
            }
        }
        return false;
    }

    //----------------------------------------------------
    //
    // Factory methods
    //
    //----------------------------------------------------
    public static ValueNode toValueNode(Object o){
        if(o == null) return NULL_NODE;
        if(o instanceof ValueNode) return (ValueNode)o;
        if(o instanceof Class) return createClassNode((Class)o);
        else if(isPath(o)) return new PathNode(o.toString(), false, false);
        else if(JsonNode.isJson(o)) return createJsonNode(o.toString());
        else if(o instanceof String) return createStringNode(o.toString(), true);
        else if(o instanceof Character) return createStringNode(o.toString(), false);
        else if(o instanceof Number) return createNumberNode(o.toString());
        else if(o instanceof Boolean) return createBooleanNode(o.toString());
        else if(o instanceof Pattern) return createPatternNode((Pattern)o);
        else throw new JsonPathException("Could not determine value type");
    }

    public static StringNode createStringNode(CharSequence charSequence, boolean escape){
        return new StringNode(charSequence, escape);
    }

    public static ClassNode createClassNode(Class<?> clazz){
        return new ClassNode(clazz);
    }

    public static NumberNode createNumberNode(CharSequence charSequence){
        return new NumberNode(charSequence);
    }

    public static BooleanNode createBooleanNode(CharSequence charSequence){
        return Boolean.parseBoolean(charSequence.toString()) ? TRUE : FALSE;
    }

    public static NullNode createNullNode(){
        return NULL_NODE;
    }


    public static JsonNode createJsonNode(CharSequence json) {
        return JsonNode.newInstance(json);
    }

    public static JsonNode createJsonNode(Object parsedJson) {
        return JsonNode.newInstance(parsedJson);
    }

    public static PatternNode createPatternNode(CharSequence pattern) {
        return new PatternNode(pattern);
    }

    public static PatternNode createPatternNode(Pattern pattern) {
        return new PatternNode(pattern);
    }

    public static UndefinedNode createUndefinedNode() {
        return UNDEFINED;
    }

    public static PathNode createPathNode(CharSequence path, boolean existsCheck, boolean shouldExists) {
        return new PathNode(path, existsCheck, shouldExists);
    }

    public static ValueNode createPathNode(Path path) {
        return new PathNode(path);
    }

}

