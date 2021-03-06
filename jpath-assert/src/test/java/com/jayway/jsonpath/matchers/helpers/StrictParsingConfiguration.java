package com.jayway.jsonpath.matchers.helpers;

import com.jayway.jsonpath.Configuration.Defaults;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.impl.json.JsonSmartJsonProvider;
import com.jayway.jsonpath.impl.mapper.JsonSmartMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import net.minidev.json.parser.JSONParser;

import java.util.EnumSet;
import java.util.Set;

public class StrictParsingConfiguration implements Defaults {

    private final JsonProvider jsonProvider = new JsonSmartJsonProvider(JSONParser.MODE_STRICTEST);
    private final MappingProvider mappingProvider = new JsonSmartMappingProvider();

    public JsonProvider jsonProvider() {
        return jsonProvider;
    }

    public MappingProvider mappingProvider() {
        return mappingProvider;
    }

    public Set<Option> options() {
        return EnumSet.noneOf(Option.class);
    }
}
