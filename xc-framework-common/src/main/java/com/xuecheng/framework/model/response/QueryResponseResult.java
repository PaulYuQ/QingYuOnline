package com.xuecheng.framework.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 小强子大大
 */
@Data
@ToString
@NoArgsConstructor
public class QueryResponseResult<T> extends ResponseResult {

    QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode,QueryResult queryResult){
        super(resultCode);
        this.queryResult = queryResult;
    }

    public void setQueryResult(QueryResult<T> queryResult) {
        this.queryResult = queryResult;
    }

    public QueryResult<T> getQueryResult() {
        return queryResult;
    }

}
