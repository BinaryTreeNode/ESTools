package com.bj.search.service.Levenshtein.impl;
import com.bj.search.common.Levenshtein;
import com.bj.search.common.utils.JsonUtil;
import com.bj.search.entity.searchResult.StatusEnum;
import com.bj.search.service.Levenshtein.LevenshteinService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
@Slf4j
@Service
public class LevenshteinServiceImpl implements LevenshteinService {
    public JsonNode search(Object inputA, Object inputB) throws IOException {
        ObjectNode objectNode = JsonUtil.createObjectNode();
        float fout = -1;
        String msg = "";
        if(inputA instanceof String && inputB instanceof String){
            fout = Levenshtein.getSimilarityRatio((String)inputA, (String)inputB);
            msg = "inputA is [" + inputA + "] " + "inputB is [" + inputB + "] " + "Similarity is :" + fout;
            objectNode.put("status",StatusEnum.SEARCH_SUCCESS.getStrategy());
            objectNode.put("msg",msg);
        } else if (inputA instanceof Long[] && inputB instanceof Long[]) {
            fout = Levenshtein.getSimilarityRatio((Long[]) inputA, (Long[]) inputB);
            msg = "inputA is [" + inputA + "] " + "inputB is [" + inputB + "] " + "Similarity is :" + fout;            objectNode.put("status",StatusEnum.SEARCH_SUCCESS.getStrategy());
            objectNode.put("msg",msg);
        } else {
            msg = "Input is invalid, wordsA class is :" + inputA.getClass() + "wordsB class is :" + inputB.getClass();
            log.warn(msg);
            objectNode.put("status",StatusEnum.BAD_REQUEST.getStrategy());
            objectNode.put("msg",msg);
        }
        return objectNode;
    }

}
