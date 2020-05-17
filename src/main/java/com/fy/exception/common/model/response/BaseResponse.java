package com.fy.exception.common.model.response;


import com.fy.exception.common.constant.IResponseEnum;
import com.fy.exception.common.constant.enums.CommonResponseEnum;
import lombok.Data;

/**
 * <p>基础返回结果</p>
 */
@Data
public class BaseResponse {
    /**
     * 返回码
     */
    protected int code;
    /**
     * 返回消息
     */
    protected String message;

    public BaseResponse() {
        // 默认创建成功的回应
        this(CommonResponseEnum.SUCCESS);
    }

    public BaseResponse(IResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMessage());
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
