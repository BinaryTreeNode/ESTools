package com.bj.search.service.boolquery.impl;

import com.bj.search.common.utils.GetFieldAndValue;
import com.bj.search.common.utils.JsonUtil;
import com.bj.search.service.boolquery.CompoundQueryService;
import com.bj.search.entity.boolQuery.CompoundQueryTypes;
import com.bj.search.entity.boolQuery.QueryMapper;
import com.bj.search.entity.boolQuery.QueryTypes;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
@Service
public class ShouldQueryImpl implements CompoundQueryService {

    @Autowired
    BooleanQueryServiceImpl booleanQueryServiceImpl;
    @Autowired
    QueryMapper queryMapper;

    @Autowired
    GetFieldAndValue testData;

    @Override
    public ObjectNode queryMaker(int width, int depth, CompoundQueryTypes[] boolens, QueryTypes[] queries, String[] field, Object[] value) {
        if ((depth > 0 && (boolens == null || boolens.length == 0))
                || (depth == 0 && (queries == null || queries.length == 0)))
            return null;

        ObjectNode outer = JsonUtil.createObjectNode();
        if (width > 0) {
            ArrayNode inner = JsonUtil.createArrayNode();
            if (depth == 0) {
                //leaf node
                for (int i = 0; i < width; ++i) {
                    inner.add(queryMapper.get(QueryTypes.TERM).termLevelQueryMaker(field, value));
                }
            } else {
                depth--;
                for (int i = 0; i < width; ++i) {
                    inner.add(booleanQueryServiceImpl.booleanQueryMaker(width, depth, boolens, queries, field, value));
                }
            }
            outer.put("should", inner);
        } else {
            outer.putAll(queryMapper.get(QueryTypes.TERM).termLevelQueryMaker(field, value));
        }
        return outer;
    }
}
