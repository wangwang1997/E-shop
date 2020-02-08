package com.zte.zshop.exception;

/**
 * Author:helloboy
 * Date:2019-05-30 15:37
 * Description:<描述>
 */
public class SysuserNotExistException extends Exception {
    public SysuserNotExistException() {
    }

    public SysuserNotExistException(String message) {
        super(message);
    }

    public SysuserNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysuserNotExistException(Throwable cause) {
        super(cause);
    }

    public SysuserNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
