package com.fy.exception.common.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用返回结果
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonResponse<T> extends BaseResponse {
    /**
     * 数据列表
     */
    protected T data;

    public CommonResponse() {
        super();
    }

    public CommonResponse(T data) {
        super();
        this.data = data;
    }
}
