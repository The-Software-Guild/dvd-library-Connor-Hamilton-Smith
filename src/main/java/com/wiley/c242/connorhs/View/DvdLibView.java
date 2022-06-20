package com.wiley.c242.connorhs.View;
import com.wiley.c242.connorhs.Model.DTO.DVD;
import com.wiley.c242.connorhs.View.UserIO;

import java.util.List;

public class DvdLibView
{
    private UserIO io;

    public DvdLibView(UserIO io)
    {
        this.io = io;
    }

    public String GetCommand()
    {
        io.PrintMessage("Please enter a command or type HELP for a list of commands : ");
        return io.GetMessage();
    }

    public void Log(String message)
    {
        io.PrintMessage(message);
    }

    public void PrintHelp()
    {
        io.PrintMessage("Available commands include:");
        io.PrintMessage("EXIT : exits the program");
        io.PrintMessage("ADD : adds a DVD to the library");
        io.PrintMessage("REMOVE : removes a DVD from the library");
        io.PrintMessage("EDIT : edits a DVD entry in the library");
        io.PrintMessage("LIST : lists all DVDs in the library with IDs");
        io.PrintMessage("DISPLAY : displays a single DVD for a given ID");
        io.PrintMessage("SEARCH : searches for a DVD by name\n");
    }

    public String[] GetDvdArguments()
    {
        String[] args = new String[6];
        io.PrintMessage("Please enter the following details about this movie...");
        io.PrintMessage("Title : ");
        args[0] = io.GetMessage();
        io.PrintMessage("Release date (YYYY-MM-DD) : ");
        args[1] = io.GetMessage();
        io.PrintMessage("MPAA rating : ");
        args[2] = io.GetMessage();
        io.PrintMessage("Director : ");
        args[3] = io.GetMessage();
        io.PrintMessage("Studio : ");
        args[4] = io.GetMessage();
        io.PrintMessage("Any additional notes? : ");
        args[5] = io.GetMessage();
        return args;
    }

    public void ListDvds(List<DVD> dvds)
    {
        for (DVD dvd : dvds)
        {
            io.PrintMessage(dvd.ToString());
        }
    }
}
