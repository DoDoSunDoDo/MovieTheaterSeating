package com.walmart.seating.exception;

public class InvalidFilePathException extends Exception{
    public InvalidFilePathException() {
        super("Please use pattern $valid/input/address $valid/output/address");
    }
}
