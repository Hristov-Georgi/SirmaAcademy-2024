package com.sirmaacademy.professionalprogram.solidprinciplesexercises.openclosedprinciple.loggerclass;

public class ConsoleLogger implements Logger{

    private String message;

    public ConsoleLogger(String message) {
        this.message = message;
    }

    @Override
    public void log() {
        System.out.println(this.message);
    }

}
