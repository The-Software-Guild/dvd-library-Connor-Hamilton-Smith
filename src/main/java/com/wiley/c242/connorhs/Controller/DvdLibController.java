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
        try { dao.LoadDVDs(); }
        catch (FileIOException e) { view.Log(e.getMessage()); }
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
                    view.Log("Saving data...");
                    try { dao.SaveDVDs(); }
                    catch (FileIOException e) { view.Log(e.getMessage()); }
                    view.Log("Exiting program.");
                    return;
                case "ADD":
                    String[] dvdDetails = view.GetDvdArguments();
                    try {dao.Add(dvdDetails); }
                    catch (DateFormatException e) { view.Log(e.getMessage()); }
                    catch (DuplicateEntryException e) { view.Log(e.getMessage()); }
                    break;
                case "REMOVE":
                    String removeKey = view.GetKeyArguments();
                    try { dao.Remove(removeKey); }
                    catch (MissingEntryException e) { view.Log(e.getMessage()); }
                    break;
                case "EDIT":
                    String[] newDvdDetails = view.EditDvdArguments();
                    try { dao.Edit(newDvdDetails); }
                    catch (DateFormatException e) { view.Log(e.getMessage()); }
                    catch (DuplicateEntryException e) { view.Log(e.getMessage()); }
                    catch (MissingEntryException e) { view.Log(e.getMessage()); }
                    break;
                case "LIST":
                    List<DVD> dvdList = dao.List();
                    view.ListDvds(dvdList);
                    break;
                case "DISPLAY":
                    String displayKey = view.GetKeyArguments();
                    DVD dvd = null;
                    try { dvd = dao.Display(displayKey); }
                    catch (MissingEntryException e) { view.Log(e.getMessage()); }
                    finally { view.Log(dvd.ToString()); }
                    break;
                case "SEARCH":
                    String[] searchPattern = view.GetSearchPattern();
                    if (searchPattern.equals("CANCEL"))
                        break;
                    List<DVD> dvdsFound = null;
                    try { dvdsFound = dao.Search(searchPattern); }
                    catch (DateFormatException e) { view.Log(e.getMessage()); }
                    finally { view.ListDvds(dvdsFound); }
                    break;
                default:
                    view.Log("Command not recognised.");
                    break;
            }
        }
    }
}
