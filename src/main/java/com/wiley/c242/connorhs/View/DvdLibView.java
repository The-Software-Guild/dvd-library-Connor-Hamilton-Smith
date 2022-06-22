package com.wiley.c242.connorhs.View;
import com.wiley.c242.connorhs.Model.DTO.DVD;
import com.wiley.c242.connorhs.View.UserIO;

import java.util.ArrayList;
import java.util.List;

public class DvdLibView
{
    private UserIO io;

    public DvdLibView(UserIO io)
    {
        this.io = io;
    }


    /*
        INPUT
     */
    public String GetCommand()
    {
        io.PrintMessage("\nPlease enter a command or type HELP for a list of commands : ");
        return io.GetMessage();
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

    public String GetKeyArguments()
    {
        io.PrintMessage("Please enter the following details about this movie...");
        io.PrintMessage("Title : ");
        String key = io.GetMessage() + " ";
        io.PrintMessage("Director : ");
        key += io.GetMessage();

        return key;
    }

    public String[] EditDvdArguments()
    {
        String[] args = new String[7];

        // The key of the entry to be edited
        args[6] = GetKeyArguments();

        // The values to be changed
        io.PrintMessage("Editing. Please enter a new value or enter 'SKIP' to use the existing value...");
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

    public String[] GetSearchPattern()
    {
        io.PrintMessage("Please enter the desired search pattern or type HELP for a list of search patterns...");
        String pattern = "";
        String parameter = "";
        for (;;)
        {
            boolean success = true;

            pattern = io.GetMessage().toUpperCase();

            switch (pattern)
            {
                // Help writes a help message to the output, then sets success as false to loop around for another input
                case "HELP":
                    PrintSearchHelp();
                    success = false;
                    break;
                // Cancel and Note need no extra parameters, break out early
                case "CANCEL":
                case "NOTE":
                    break;
                // The remaining patterns need an additional search parameter
                case "TITLE":
                case "RATING":
                case "DIRECTOR":
                case "STUDIO":
                case "DATE":
                case "DATE-GT":
                case "DATE-LT":
                    io.PrintMessage("Please enter the search parameter..");
                    parameter = io.GetMessage();
                    break;
                default:
                    io.PrintMessage("Search pattern not recognised.");
                    success = false;
                    break;

            }
            // If the search pattern was received successfully, break out of the loop
            if (success)
                break;
        }
        return new String[] { pattern, parameter };
    }

    /*
        OUTPUT
     */
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
        io.PrintMessage("LIST : lists all DVDs in the library");
        io.PrintMessage("DISPLAY : displays a single DVD for a given title and director");
        io.PrintMessage("SEARCH : searches for a DVD by name\n");
    }

    public void PrintSearchHelp()
    {
        io.PrintMessage("Available search patterns include:");
        io.PrintMessage("CANCEL : cancels the search");
        io.PrintMessage("TITLE : searches for a specific title");
        io.PrintMessage("DATE-GT : searches for DVDs with a release date greater than some threshold");
        io.PrintMessage("DATE-LT : searches for DVDs with a release date less than some threshold");
        io.PrintMessage("DATE : searches for DVDs with a specific release date");
        io.PrintMessage("RATING : searches for DVDs with a specific MPAA rating");
        io.PrintMessage("DIRECTOR : searches for DVDs by a specific director");
        io.PrintMessage("STUDIO : searches for DVDs by a specific studio");
        io.PrintMessage("NOTE : returns all DVDs that have a user note\n");
    }

    public void ListDvds(List<DVD> dvds)
    {
        for (DVD dvd : dvds)
        {
            io.PrintMessage(dvd.ToString());
        }
    }
}
