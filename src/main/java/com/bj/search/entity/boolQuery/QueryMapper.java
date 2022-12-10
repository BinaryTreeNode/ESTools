package com.bj.search.entity.boolQuery;


import com.bj.search.service.boolenquery.CompoundQueryService;
import com.bj.search.service.boolenquery.impl.FilterQueryImpl;
import com.bj.search.service.boolenquery.impl.MustNotQueryImpl;
import com.bj.search.service.boolenquery.impl.MustQueryImpl;
import com.bj.search.service.boolenquery.impl.ShouldQueryImpl;
import com.bj.search.service.singlequeries.SingleQueriesService;
import com.bj.search.service.singlequeries.impl.TermQueryImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
@Service
@Getter
public class QueryMapper {

    @Autowired
    MustQueryImpl mustQueryImpl;
    @Autowired
    MustNotQueryImpl mustNotQueryImpl;
    @Autowired
    ShouldQueryImpl shouldQueryImpl;
    @Autowired
    FilterQueryImpl filterQueryImpl;
    @Autowired
    TermQueryImpl termQueryImpl;
//    @Autowired
//    MatchQueryImpl matchQueryImpl;

    public  Pattern SPACE_PATTERN = Pattern.compile("\\s+");
    public  Map<CompoundQueryTypes, CompoundQueryService> compoundQueryServiceMap = new HashMap<>();
    public  Map<QueryTypes, SingleQueriesService> singleQueriesServiceMap = new HashMap<>();


    @PostConstruct
    public void queryMapperInit(){
        compoundQueryServiceMap.put(CompoundQueryTypes.MUST, mustQueryImpl);
        compoundQueryServiceMap.put(CompoundQueryTypes.MUSTNOT, mustNotQueryImpl);
        compoundQueryServiceMap.put(CompoundQueryTypes.SHOULD, shouldQueryImpl);
        compoundQueryServiceMap.put(CompoundQueryTypes.FILTER, filterQueryImpl);

        singleQueriesServiceMap.put(QueryTypes.TERM, termQueryImpl);
//        singleQueriesServiceMap.put(QueryTypes.MATCH, matchQueryImpl);
    }

    public  CompoundQueryService get(CompoundQueryTypes types) {
        return compoundQueryServiceMap.get(types);
    }

    public  SingleQueriesService get(QueryTypes types) {
        return singleQueriesServiceMap.get(types);
    }

}
