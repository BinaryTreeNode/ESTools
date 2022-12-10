package com.bj.search.controller.similarity;

import com.bj.search.common.utils.JsonUtil;
import com.bj.search.entity.searchResult.SearchResultStatus;
import com.bj.search.entity.searchResult.StatusEnum;
import com.bj.search.service.Levenshtein.LevenshteinService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
@RestController
@RequestMapping("/Levenshtein")
public class Levenshtein {
    @Autowired
    LevenshteinService levenshteinService;

    @Operation(summary = "similarity api")
    @PostMapping("/search")
    public SearchResultStatus search(@RequestParam(required = true, defaultValue = "sheep") Object inputA, @RequestParam(required = true, defaultValue = "sheet") Object inputB) throws IOException {

        JsonNode fout = levenshteinService.search(inputA, inputB);
        ObjectNode objectNode = JsonUtil.createObjectNode();
        objectNode.put("similarityRatio is ", "[ " + fout + " ]");
        return new SearchResultStatus(StatusEnum.SEARCH_SUCCESS, fout);

    }
}
