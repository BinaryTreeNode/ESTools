package com.bj.search.controller.boolQuery;

import com.bj.search.config.OutputProperties;
import com.bj.search.entity.boolQuery.CompoundQueryTypes;
import com.bj.search.entity.boolQuery.QueryTypes;
import com.bj.search.service.boolquery.BooleanQueryService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    BooleanQueryService booleanQueryService;

    @Autowired
    OutputProperties outputProperties;

    /**
     * @param width the number of sub queries of each layer
     * @param depth
     * @return
     */
    @Operation(summary = "create query without key and value")
    @PostMapping("/createQueryNoValue")
    public ObjectNode createBooleanSingle(@RequestParam(required = false, defaultValue = "4") int width,
                                          @RequestParam(required = false, defaultValue = "2") int depth) {

        ObjectNode objectNode = booleanQueryService.booleanQueryMaker(
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
}

