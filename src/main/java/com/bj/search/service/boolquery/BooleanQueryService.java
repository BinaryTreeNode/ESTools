package com.bj.search.service.boolquery;

import com.bj.search.entity.boolQuery.CompoundQueryTypes;
import com.bj.search.entity.boolQuery.QueryTypes;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
public interface BooleanQueryService {

    /***
     * create boolean query cases
     * @param width
     * @param depth
     * @param boolens
     * @param queries
     * @return
     */
    ObjectNode booleanQueryMaker(int width, int depth, CompoundQueryTypes[] boolens, QueryTypes[] queries, String[] field, Object[] value);
}
