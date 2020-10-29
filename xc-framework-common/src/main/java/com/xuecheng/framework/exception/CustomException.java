package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 自定义异常类型
 * @author 小强子大大
 * @date 2020年10月4日19:20:15
 */
public class CustomException extends RuntimeException {
    ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
