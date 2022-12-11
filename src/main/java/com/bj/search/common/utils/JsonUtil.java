package com.bj.search.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Yang Li
 * @date 2022/12/8 10:02 上午
 */
@NoArgsConstructor
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, true);
    }

    public static String toJsonString(Object object) throws Exception {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new Exception(e);
        }
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static <T> T toObject(String str, Class<T> clazz) throws Exception {
        try {
            return OBJECT_MAPPER.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            throw new Exception(e);
        }
    }

    public static JsonNode toObject(String str) {
        try {
            return OBJECT_MAPPER.readTree(str);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(String str, TypeReference<T> t) throws Exception {
        try {
            return OBJECT_MAPPER.readValue(str, t);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static <T> T toObject(JsonParser jsonParser, TypeReference<T> t) throws Exception {
        try {
            return OBJECT_MAPPER.readValue(jsonParser, t);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static Map<String, Object> convertJsonNode2Map(JsonNode jsonNode) {
        try {
            return OBJECT_MAPPER.convertValue(jsonNode, Map.class);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Object> convertJsonNode2List(JsonNode jsonNode) throws Throwable{
        try {
            return OBJECT_MAPPER.convertValue(jsonNode, List.class);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T convertObject(Object object, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(object, clazz);
    }

    public static ObjectNode createObjectNode() {
        return OBJECT_MAPPER.createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return OBJECT_MAPPER.createArrayNode();
    }

}
