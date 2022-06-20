package com.wiley.c242.connorhs.Model.DTO;

public class DateFormatException extends Exception
{
    @Override
    public String getMessage() {
        return "Date format incorrect. Please use the form YYYY-MM-DD";
    }
}
