package com.example.E_commerce_Backend.exception;

public class InvalidPaymentException extends RuntimeException{

    public InvalidPaymentException(String message){
        super(message);
    }
}
