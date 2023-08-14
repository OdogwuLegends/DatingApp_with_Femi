package com.legends.promiscuous.exceptions;

public enum ExceptionMessage {

    USER_NOT_FOUND_EXCEPTION;
    USER_NOT_FOUND_EXCEPTION("")
    ExceptionMessage(){

    }

    ExceptionMessage(String message){
        this.message = message;
    }
    private String message;
}
