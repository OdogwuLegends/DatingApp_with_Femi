package com.legends.promiscuous.exceptions;

public enum ExceptionMessage {

    USER_NOT_FOUND_EXCEPTION,
    USER_WITH_EMAIL_NOT_FOUND_EXCEPTION("User with email %s not found");
    ExceptionMessage(){

    }

    ExceptionMessage(String message){
        this.message = message;
    }
    private String message;
}
