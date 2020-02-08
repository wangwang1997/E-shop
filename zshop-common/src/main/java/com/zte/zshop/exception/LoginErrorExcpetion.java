package com.zte.zshop.exception;

/**
 * Author:helloboy
 * Date:2019-06-12 8:50
 * Description:<描述>
 */
public class LoginErrorExcpetion extends Exception{

    public LoginErrorExcpetion() {
        super();
    }

    public LoginErrorExcpetion(String message) {
        super(message);
    }

    public LoginErrorExcpetion(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginErrorExcpetion(Throwable cause) {
        super(cause);
    }

    protected LoginErrorExcpetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
