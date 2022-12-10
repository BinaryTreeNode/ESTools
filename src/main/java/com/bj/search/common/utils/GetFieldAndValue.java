package com.bj.search.common.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    public JSONObject getFieldValue(JSONObject js,JSONObject primaryJson){
        JSONObject result = new JSONObject();
        List<String> keys = new ArrayList<>();
        keys.addAll(js.keySet());
        int random = (int)(Math.random()*keys.size());
        String field = keys.get(random);
        String orgField = field;

        Object value = js.get(field);
        if(value == null && field.equals(primaryJson.getString("primaryKey"))){
            value = primaryJson.get("value");
        }

        Object realValue = value;
        String realField = field;
        if(Arrays.asList(textFields).contains(field)){
            String [] values = value.toString().split("[ \r\n\t]");
            int randomInner = (int)(Math.random()*values.length);
            realValue = values[randomInner];
            if(realValue.toString().length() > 255){
                if(Math.random() > 0.4){
                    realValue = realValue.toString().substring(0,255);
                }
            }
            if(Arrays.asList(textWithKeyWord).contains(field)){
                if(Math.random() >0.2){
                    realField = field + ".keyword";
                    realValue = value.toString();
                }
            }
        }

        if(Arrays.asList(multiValFields).contains(field)){
            String [] values = value.toString().split(" ");
            int randomInner = (int)(Math.random()*values.length);
            realValue = values[randomInner];
        }

        if(field.equals("completion")){
            JSONArray jsonArray = JSONArray.fromObject(value.toString());
            int comRandom = (int)(Math.random()*jsonArray.size());
            realValue = jsonArray.getJSONObject(comRandom).get("input");
        }

        if(Arrays.asList(keywordFields).contains(field)){
            if(value instanceof JSONArray) {
                JSONArray jsonArray = JSONArray.fromObject(value.toString());
                if(jsonArray.size() == 0){
                    realValue = "WCSNULL";
                }else{
                    int keyRandom = (int) (Math.random() * jsonArray.size());
                    realValue = jsonArray.getString(keyRandom);
                }

            }else {
                realValue = value.toString();
            }
        }

        result.put("field",realField);
        log.info("realField:{}",realField);
        result.put("value",realValue.toString());

        js.remove(orgField);
        return  result;
    }
}
