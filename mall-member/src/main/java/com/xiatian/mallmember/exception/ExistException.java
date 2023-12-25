package com.xiatian.mallmember.exception;

public class ExistException extends RuntimeException{
    public ExistException(){
        super("严禁多次注册");
    }

}
