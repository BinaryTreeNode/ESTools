package com.bj.search.entity.searchResult;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
@Getter
@NoArgsConstructor
public class SearchResultStatus {
    private StatusEnum statusEnum;

    private JsonNode msg;

    public SearchResultStatus(StatusEnum s, JsonNode j) {
        this.statusEnum = s;
        this.msg = j;
    }

}
