package com.bj.search.entity.boolQuery;
import lombok.Getter;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
@Getter
public enum CompoundQueryTypes {
    MUST,
    SHOULD,
    FILTER,
    MUSTNOT;
}
