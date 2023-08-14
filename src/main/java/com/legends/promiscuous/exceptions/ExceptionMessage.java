package com.legends.promiscuous.exceptions;

public enum ExceptionMessage {

    USER_NOT_FOUND_EXCEPTION,
    USER_WITH_EMAIL_NOT_FOUND_EXCEPTION("User with email %s not found"),
    USER_REGISTRATION_FAILED_EXCEPTION(" ");
    ExceptionMessage(){

    }

    ExceptionMessage(String message){
        this.message = message;
    }
    private String message;

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
