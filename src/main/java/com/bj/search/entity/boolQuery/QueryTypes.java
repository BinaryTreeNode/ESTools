package com.bj.search.entity.boolQuery;

import lombok.Getter;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
@Getter
public enum QueryTypes {
    TERM("term"),
    MATCH("match");

    private final String name;
    QueryTypes(String name) {
        this.name = name;
    }
}
