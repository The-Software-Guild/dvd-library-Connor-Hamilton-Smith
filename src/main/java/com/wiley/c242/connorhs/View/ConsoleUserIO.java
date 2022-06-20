package com.wiley.c242.connorhs.View;

import java.util.Scanner;

public class ConsoleUserIO implements UserIO
{
    Scanner input = new Scanner(System.in);

    @Override
    public void PrintMessage(String message)
    {
        System.out.println(message);
    }

    @Override
    public String GetMessage()
    {
        return input.nextLine();
    }
}
