package com.github.asm0dey.shared.domain.Exception;

/**
 * User: finkel
 * <p/>
 * Date: 22.03.13
 * <p/>
 * Time: 15:18
 */
public class CreateUserException extends Exception{
    public CreateUserException(String s, Exception e) {
        super(s,e);

    }

    public CreateUserException() {
        super();
    }
}
