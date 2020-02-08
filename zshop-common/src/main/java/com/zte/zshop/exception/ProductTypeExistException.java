package com.zte.zshop.exception;

/**
 * Author:helloboy
 * Date:2019-05-16 17:29
 * Description:<描述>
 */
public class ProductTypeExistException extends Exception{

    public ProductTypeExistException() {
    }

    public ProductTypeExistException(String message) {
        super(message);
    }

    public ProductTypeExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductTypeExistException(Throwable cause) {
        super(cause);
    }

    public ProductTypeExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
