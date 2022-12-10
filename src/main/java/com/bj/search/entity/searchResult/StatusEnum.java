package com.bj.search.entity.searchResult;

import lombok.Getter;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
@Getter
public enum StatusEnum {

    SEARCH_SUCCESS(200),
    INDEX_CREATED(201),
    BAD_REQUEST(400),
    INDEX_FAILED(500);

    private int strategy;

    StatusEnum(int strategy) {
        this.strategy = strategy;
    }
}
