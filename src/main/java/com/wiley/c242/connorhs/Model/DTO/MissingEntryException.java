package com.wiley.c242.connorhs.Model.DTO;

public class MissingEntryException extends Exception
{
    @Override
    public String getMessage() {
        return "DVD not found.";
    }
}
