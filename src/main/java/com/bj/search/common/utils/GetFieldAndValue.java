package com.bj.search.common.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GetFieldAndValue {

    static String [] textFields = {"textNotNullWithKey","param111825","textNotImpCanNull","param111661","textDicCanNull","textCutNorImpNullN",
            "textKeyNotAnalyzer","textNormalImpNotNull","param112567","textCutSImpNullN","textImpCanNull","textNotImpNotNull","param112880",
            "trans_c_new","textCanNullWithKey","title","content","param112536","textImpNotNull","textCutNImpNullN","textNormalImpCanNull",
            "textCutYImpNullY","textCutNImpNullY","textDicNotNull","infoTitle"};

    static String [] textWithKeyWord = {"textNotNullWithKey","textKeyNotAnalyzer","textCanNullWithKey"};

    static String [] multiValFields = {"unintValMutiCanNull","intValMutiCanNull","intValMutiNotNull","unintValMutiNotNull",
            "floatMutiCanNull","doubleMutiCanNull","floatMutiNotNull","doubleMutiNotNull"};

    static String [] keywordFields = {"keywordCanNull","keywordNotNull"};

    public ObjectNode getFieldValue(ObjectNode js, ObjectNode primaryJson){
        ObjectNode objectNode = JsonUtil.createObjectNode();
        List<String> keys = new ArrayList<>();
        while (js.fieldNames().hasNext()) {
            keys.add(js.fieldNames().next());
        }
        int random = (int)(Math.random()*keys.size());
        String field = keys.get(random);
        String orgField = field;

        Object value = js.get(field);
        if(value == null && field.equals(primaryJson.get("primaryKey").textValue())){
            value = primaryJson.get("value");
        }

        return  objectNode;
    }
}
