package com.wiley.c242.connorhs.Controller;
import com.wiley.c242.connorhs.View.*;
import com.wiley.c242.connorhs.Model.DAO.*;
import com.wiley.c242.connorhs.Model.DTO.*;

import java.text.DateFormat;
import java.util.List;

public class DvdLibController
{
    DvdLibDAO dao;
    DvdLibView view;

    public DvdLibController(DvdLibDAO dao, DvdLibView view)
    {
        this.dao = dao;
        this.view = view;
    }

    public void Run()
    {
        for (;;)
        {
            // Get the user command from the view
            String cmd = view.GetCommand().toUpperCase();

            // Check command validity
            switch(cmd)
            {
                case "HELP":
                    view.PrintHelp();
                    break;
                case "EXIT":
                    view.Log("Exiting program.");
                    return;
                case "ADD":
                    String[] addArgs = view.GetDvdArguments();
                    try {dao.Add(addArgs); }
                    catch (DateFormatException e) { view.Log(e.getMessage()); }
                    catch (DuplicateEntryException e) { view.Log(e.getMessage()); }
                    break;
                case "REMOVE":
                    String removeKey = view.GetKeyArguments();
                    try { dao.Remove(removeKey); }
                    catch (MissingEntryException e) { view.Log(e.getMessage()); }
                    break;
                case "EDIT":
//                    String editKey = view.GetKeyArguments();
//                    try { dao.Edit(editKey); }
//                    catch (MissingEntryException e) { view.Log(e.getMessage()); }
                    break;
                case "LIST":
                    List<DVD> dvdList = dao.List();
                    view.ListDvds(dvdList);
                    break;
                case "DISPLAY":
                    dao.Display();
                    break;
                case "SEARCH":
                    dao.Search();
                    break;
                default:
                    view.Log("Command not recognised.");
                    break;
            }
        }
    }
}
