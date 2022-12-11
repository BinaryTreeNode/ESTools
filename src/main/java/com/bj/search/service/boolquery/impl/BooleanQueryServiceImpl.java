package com.bj.search.service.boolquery.impl;

import com.bj.search.common.utils.GetFieldAndValue;
import com.bj.search.common.utils.JsonUtil;
import com.bj.search.service.boolquery.BooleanQueryService;
import com.bj.search.entity.boolQuery.CompoundQueryTypes;
import com.bj.search.entity.boolQuery.QueryMapper;
import com.bj.search.entity.boolQuery.QueryTypes;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/***
 * {
 *   "_source": false,
 *   "profile": true,
 *   "query": {
 *     "bool": {
 *       "must_not": [
 *         {
 *           "match": {
 *             "text": {
 *                 "query": "",
 *                 "zero_terms_query": "none"
 *               }
 *             }
 *         }
 *       ]
 *     }
 *   },
 *   "sort": [
 *     {
 *       "infoId": {
 *         "order": "desc"
 *       }
 *     }
 *   ],
 *   "size": 20
 * }
 */
@Slf4j
@Service
public class BooleanQueryServiceImpl implements BooleanQueryService {

    @Autowired
    QueryMapper queryMapper;

    @Autowired
    GetFieldAndValue testData;

    Random r = new Random();

    @Override
    public ObjectNode booleanQueryMaker(int width, int depth, CompoundQueryTypes[] boolens, QueryTypes[] queries, String[] field, Object[] value) {
        if (width == 0 || depth == 0)
            return JsonUtil.createObjectNode();
        ObjectNode outer = JsonUtil.createObjectNode();
        ObjectNode inner = JsonUtil.createObjectNode();
        int pos;
        if (depth == 1) {//无论width设置为多少，都只会有一个简单查询，不和bool结合
            for (int i = 0; i < width; ++i) {
                pos = queries.length > 0 ? r.nextInt(queries.length) : 0;
                ObjectNode node = queryMapper.get(queries[pos]).termLevelQueryMaker(field, value);
                outer.put(queries[pos].getName(), node.get(queries[pos].getName()));
            }
        } else {
            for (int i = 0; i < width; ++i) {
                pos = boolens.length > 0 ? r.nextInt(boolens.length) : 0;
                ObjectNode node = queryMapper.get(boolens[pos]).queryMaker(width, depth, boolens, queries, field, value);
                inner.putAll(node);
            }
            outer.put("bool", inner);
        }

        log.info("out======" + outer.toString());
        return outer;
    }

}
