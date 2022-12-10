package com.bj.search.service.singlequeries.impl;

import com.bj.search.common.utils.JsonUtil;
import com.bj.search.config.FieldConfig;
import com.bj.search.entity.boolQuery.CompoundQueryTypes;
import com.bj.search.service.singlequeries.SingleQueriesService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
@Service
public class TermQueryImpl implements SingleQueriesService {

    @Autowired
    FieldConfig fieldConfig;

    public ObjectNode termLevelQueryMaker(String[] field, Object[] value) {
        ObjectNode outer = JsonUtil.createObjectNode();
        ObjectNode inner = JsonUtil.createObjectNode();
        //随机
        Random r = new Random();
        int rf = field.length > 0 ? r.nextInt(field.length) : 0;
        int rv = value.length > 0 ? r.nextInt(value.length) : 0;

        if (value[rv] instanceof String) {
            inner.put(field[rf], value[rv].toString());
        } else {
            inner.put(field[rf], Long.valueOf(value[rv].toString()));
        }

        outer.put("term", inner);

        return outer;
    }

    public ObjectNode termLevelQueryMakerMulti(String field, Object value, ArrayNode storeFields, CompoundQueryTypes type) {
        ObjectNode outer = JsonUtil.createObjectNode();
        ObjectNode inner = JsonUtil.createObjectNode();
        storeFields.add(field);
        if (type == CompoundQueryTypes.MUSTNOT) {
            String fieldType = fieldConfig.getProperty(field).split(",")[0];
            switch (fieldType) {
                case "Integer":
                    value = Long.valueOf(value.toString()) + 123;
                    break;
                case "Float":
                    value = Float.valueOf(value.toString()) + 888.8;
                    break;
                case "Double":
                    value = Double.valueOf(value.toString()) + 666.66;
                    break;
                case "text":
                case "completion":
                case "keyword":
                    value = value.toString() + "test";
                    break;
                default:
                    value = value.toString() + "testdefault";
            }
        }

        if (Math.random() < 0.5) {
            inner = putValue(field, value);
        } else {
            String str = String.format("%.2f", Math.random() * 10);
            double boost = Double.parseDouble(str);
            ObjectNode inner_in = putValue("value", value);
            inner_in.put("boost", boost);
            inner.put(field, inner_in);
        }
        outer.put("term", inner);
        return outer;
    }

    public ObjectNode putValue(String field, Object value) {
        ObjectNode inner = JsonUtil.createObjectNode();
        if (value instanceof String) {
            inner.put(field, value.toString());
        } else if (value instanceof Integer) {
            inner.put(field, Integer.valueOf(value.toString()));
        } else if (value instanceof Float) {
            inner.put(field, Float.valueOf(value.toString()));
        } else if (value instanceof Double) {
            inner.put(field, Double.valueOf(value.toString()));
        } else if (value instanceof Long) {
            inner.put(field, Long.valueOf(value.toString()));
        }
        return inner;
    }

    public static void main(String[] args) {
        TermQueryImpl termQuery = new TermQueryImpl();
        for (int i = 0; i < 10; i++) {
            System.out.println(termQuery.termLevelQueryMakerMulti("id", 1.3, JsonUtil.createArrayNode(), CompoundQueryTypes.MUST));
        }
    }
}
