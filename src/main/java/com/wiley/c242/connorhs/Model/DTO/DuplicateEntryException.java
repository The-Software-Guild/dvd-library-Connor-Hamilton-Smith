package com.wiley.c242.connorhs.Model.DTO;

public class DuplicateEntryException extends Exception
{
    @Override
    public String getMessage() {
        return "This DVD already exists in the library.";
    }
}
