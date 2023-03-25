package com.estudo.mensageria.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProcessingException extends RuntimeException{

    public ProcessingException(String message){
        super(message);
    }
}
