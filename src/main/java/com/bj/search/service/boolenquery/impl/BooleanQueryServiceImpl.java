package com.bj.search.service.boolenquery.impl;

import com.bj.search.common.utils.GetFieldAndValue;
import com.bj.search.common.utils.JsonUtil;
import com.bj.search.service.boolenquery.BoolenQueryService;
import com.bj.search.entity.boolQuery.CompoundQueryTypes;
import com.bj.search.entity.boolQuery.QueryMapper;
import com.bj.search.entity.boolQuery.QueryTypes;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
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
public class BooleanQueryServiceImpl implements BoolenQueryService {

    @Autowired
    QueryMapper queryMapper;

    @Autowired
    GetFieldAndValue testData;

    @Override
    public ObjectNode boolenQueryMaker(int width, int depth, CompoundQueryTypes[] boolens, QueryTypes[] queries, String[] field, Object[] value){
        if(width == 0 || depth == 0)
            return JsonUtil.createObjectNode();
        //外层{}
        ObjectNode outer = JsonUtil.createObjectNode();
        ObjectNode inner = JsonUtil.createObjectNode();
        Random r = new Random();
        int pos;
        if(depth == 1){//无论width设置为多少，都只会有一个简单查询，不和bool结合
            for(int i = 0; i < width; ++i){
                pos =  queries.length > 0 ? r.nextInt(queries.length) : 0;
                ObjectNode node = queryMapper.get(queries[pos]).termLevelQueryMaker(field, value);
                outer.put(queries[pos].getName(),node.get(queries[pos].getName()));
            }
        } else {
            for(int i = 0; i < width; ++i){
                pos =  boolens.length > 0 ? r.nextInt(boolens.length) : 0;
                ObjectNode node = queryMapper.get(boolens[pos]).queryMaker(width, depth, boolens, queries, field, value);
                inner.putAll(node);
            }
            outer.put("bool",inner);
        }

        log.info("out======"+outer.toString());

        //写入数据库
//        BoolQuery boolQuery = new BoolQuery();
//        boolQuery.setQuery(outer.toString());
//        boolQueryMapper.insert(boolQuery);


        return outer;
    }


    @Override
    public ObjectNode boolenQueryMakerMulti(int width, int depth, CompoundQueryTypes[] boolens, QueryTypes[] queries, JSONObject js,JSONObject primaryJson, ArrayNode storeFields,CompoundQueryTypes type){
        if(width == 0 || depth == 0)
            return JsonUtil.createObjectNode();

        ObjectNode outer = JsonUtil.createObjectNode();
        ObjectNode inner = JsonUtil.createObjectNode();
        Random r = new Random();
        int pos;
        if(depth == 1){//无论width设置为多少，都只会有一个简单查询，不和bool结合
            if(js.size() > 0) {
                pos = queries.length > 0 ? r.nextInt(queries.length) : 0;
                JSONObject keyValue = testData.getFieldValue(js,primaryJson);
                ObjectNode node = queryMapper.get(queries[pos]).termLevelQueryMakerMulti(keyValue.getString("field"), keyValue.get("value"),storeFields,type);
                outer.put(queries[pos].getName(), node.get(queries[pos].getName()));
            }
        } else {
            boolean shouldFlag = false;
            for(int i = 0; i < width; ++i){
                if(js.size() == 0) break;
                pos =  boolens.length > 0 ? r.nextInt(boolens.length) : 0;
                if(!shouldFlag){
                    shouldFlag = (boolens[pos] == CompoundQueryTypes.SHOULD) ? true : false;
                }
                ObjectNode node = queryMapper.get(boolens[pos]).queryMakerMulti(width, depth, boolens, queries,js,primaryJson,storeFields);
                inner.putAll(node);
            }
            if(shouldFlag){
                String [] shouldMatchArr = {"404","1","-75%","2 < 25%"};

                int matchRandom = (int) (Math.random()*shouldMatchArr.length);
                String match = shouldMatchArr[matchRandom];
                if(!match.equals("404")){
                    inner.put("minimum_should_match",match);
                }
            }

            if(Math.random() > 0.5){
                String str = String.format("%.2f",Math.random()*10);
                double boost = Double.parseDouble(str);
                inner.put("boost",boost);
            }
            outer.put("bool",inner);
        }

        log.info("out======"+outer.toString());

//        //写入数据库
//        BoolQuery boolQuery = new BoolQuery();
//        boolQuery.setQuery(outer.toString());
//        boolQueryMapper.insert(boolQuery);


        return outer;
    }

}
