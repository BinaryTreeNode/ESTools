package com.bj.search.service.singlequeries;

import com.bj.search.entity.boolQuery.CompoundQueryTypes;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
public interface SingleQueriesService {
    /**
     * build term query
     * @param field
     * @param value
     * @return
     */
    ObjectNode termLevelQueryMaker(String[] field, Object[] value);
    ObjectNode termLevelQueryMakerMulti(String field, Object value, ArrayNode storeFields, CompoundQueryTypes type);
}
