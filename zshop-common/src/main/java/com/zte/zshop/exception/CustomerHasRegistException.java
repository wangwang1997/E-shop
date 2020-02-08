package com.zte.zshop.exception;

/**
 * Author:helloboy
 * Date:2019-06-20 13:49
 * Description:<描述>
 */
public class CustomerHasRegistException extends Exception {
    public CustomerHasRegistException(String message) {
        super(message);
    }

    public CustomerHasRegistException() {
    }

    public CustomerHasRegistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerHasRegistException(Throwable cause) {
        super(cause);
    }

    public CustomerHasRegistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
