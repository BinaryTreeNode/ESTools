package com.bj.search.service.boolenquery;

import com.bj.search.entity.boolQuery.CompoundQueryTypes;
import com.bj.search.entity.boolQuery.QueryTypes;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.sf.json.JSONObject;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
public interface BoolenQueryService {

    /***
     * create boolean query cases
     * @param width
     * @param depth
     * @param boolens
     * @param queries
     * @return
     */
    ObjectNode boolenQueryMaker(int width, int depth, CompoundQueryTypes[] boolens, QueryTypes[] queries, String[] field, Object[] value);
    ObjectNode boolenQueryMakerMulti(int width, int depth, CompoundQueryTypes[] boolens, QueryTypes[] queries, JSONObject js, JSONObject primaryJson, ArrayNode storeFields, CompoundQueryTypes type);

}
