package com.fy.exception.common.model.response;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 通用返回结果
 *
 * @param <T>
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class R<T> extends CommonResponse<T> {

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.message = msg;
    }

    public R(Throwable e) {
        super();
        this.message = e.getMessage();
        this.code = -1;
    }
}
