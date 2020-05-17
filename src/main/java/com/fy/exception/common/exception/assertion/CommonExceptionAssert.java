package com.fy.exception.common.exception.assertion;


import com.fy.exception.common.constant.IResponseEnum;
import com.fy.exception.common.exception.ArgumentException;
import com.fy.exception.common.exception.BaseException;

import java.text.MessageFormat;

/**
 * CommonExceptionAssert
 */
public interface CommonExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new ArgumentException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new ArgumentException(this, args, msg, t);
    }

}
