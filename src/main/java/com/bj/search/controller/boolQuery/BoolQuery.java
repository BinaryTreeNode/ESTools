package com.bj.search.controller.boolQuery;

import com.bj.search.common.utils.JsonUtil;
import com.bj.search.config.OutputProperties;
import com.bj.search.entity.boolQuery.CompoundQueryTypes;
import com.bj.search.entity.boolQuery.QueryTypes;
import com.bj.search.service.boolenquery.BoolenQueryService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
@Slf4j
@RestController
@RequestMapping("/boolQuery")
public class BoolQuery {

    @Autowired
    BoolenQueryService boolenQueryService;

    @Autowired
    OutputProperties outputProperties;

    /**
     * @param width the number of sub queries of each layer
     * @param depth
     * @return
     */
    @Operation(summary = "create query without key and value")
    @GetMapping("/createQueryNoValue")
    public ObjectNode createBooleanSingle(@RequestParam(required = false, defaultValue = "4") int width,
                                          @RequestParam(required = false, defaultValue = "2") int depth) {

        ObjectNode objectNode = boolenQueryService.boolenQueryMaker(
                width,
                depth,
                new CompoundQueryTypes[]{CompoundQueryTypes.FILTER, CompoundQueryTypes.MUST, CompoundQueryTypes.MUSTNOT, CompoundQueryTypes.SHOULD},
                new QueryTypes[]{QueryTypes.TERM},
                new String[]{"textNotNullWithKey", "textCanNullWithKey", "textKeyNotAnalyzer"},
                new Object[]{"", "111", "444"});
        objectNode.put("stored_fields", "id");
        objectNode.put("_source", "id");
        log.info(objectNode.toString());
        return objectNode;

    }

    @Operation(summary = "create query with key and value")
    @PostMapping("/creatQueryWithValue")
    public ObjectNode createBoolean(@RequestParam(required = false, defaultValue = "4") int width,
                                    @RequestParam(required = false, defaultValue = "2") int depth,
                                    @RequestParam String jsonStr) {

        JSONObject data = JSONObject.fromObject(jsonStr);
        ArrayNode storeFields = JsonUtil.createArrayNode();

        JSONObject primaryJson = new JSONObject();
        if (data.has("id")) {
            primaryJson.put("primaryKey", "id");
            primaryJson.put("value", data.getLong("id"));
        } else if (data.has("infoId")) {
            primaryJson.put("primaryKey", "infoId");
            primaryJson.put("value", data.getLong("infoId"));
        }

        ObjectNode objectNode = boolenQueryService.boolenQueryMakerMulti(
                width,
                depth,
                new CompoundQueryTypes[]{CompoundQueryTypes.FILTER, CompoundQueryTypes.MUST, CompoundQueryTypes.MUSTNOT, CompoundQueryTypes.SHOULD},
                new QueryTypes[]{QueryTypes.TERM},
                data,
                primaryJson,
                storeFields,
                CompoundQueryTypes.MUST);

        ObjectNode finalNode = JsonUtil.createObjectNode();
        finalNode.put("query", objectNode);

        int[] sizeArr = {404, 10, 100, 1000, 3500};
        int[] fromArr = {404, 0, 1, 3, 10};

        int sizeRandom = (int) (Math.random() * sizeArr.length);
        int size = sizeArr[sizeRandom];
        if (size != 404) {
            finalNode.put("size", size);
        }

        int fromRandom = (int) (Math.random() * fromArr.length);
        int from = fromArr[fromRandom];
        if (from != 404) {
            finalNode.put("from", from);
        }

        List<String> tmpList = new ArrayList<>();
        for (int i = 0; i < storeFields.size(); i++) {
            String tmp = storeFields.get(i).toString().replace("\"", "");
            if (!tmpList.contains(tmp)) {
                tmpList.add(tmp);
            }
        }
        ArrayNode dedupeField = JsonUtil.createArrayNode();
        for (String tmpField : tmpList) {
            dedupeField.add(tmpField);
        }

        finalNode.put("stored_fields", dedupeField);
        finalNode.put("_source", dedupeField);

        log.info("finalQuery:{}", finalNode.toString());

        return finalNode;
    }
}

