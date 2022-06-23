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

    /*
        The controller constructor. Sets the DAO and view with dependency injection
        Instructs the DAO to load DVD data from file
     */
    public DvdLibController(DvdLibDAO dao, DvdLibView view)
    {
        this.dao = dao;
        this.view = view;
        try { dao.LoadDVDs(); }
        catch (FileIOException e) { view.Log(e.getMessage()); }
    }

    /*
        Run the main body of the program
        Retrieve, parse, then execute commands in an endless loop until the EXIT command is called
     */
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
                    // On exit, save the updated data map to file, then return from Run()
                    view.Log("Saving data...");
                    try { dao.SaveDVDs(); }
                    catch (FileIOException e) { view.Log(e.getMessage()); }
                    view.Log("Exiting program.");
                    return;
                case "ADD":
                    // On add, get the appropriate arguments from the user (title, release date, etc...) then add the data to the map
                    String[] dvdDetails = view.GetDvdArguments();
                    try {dao.Add(dvdDetails); }
                    // dao.Add() throws errors for basic input validation
                    catch (DateFormatException e) { view.Log(e.getMessage()); }
                    catch (DuplicateEntryException e) { view.Log(e.getMessage()); }
                    break;
                case "REMOVE":
                    // On remove, delete the map entry at a specific key (Title + Director)
                    String removeKey = view.GetKeyArguments();
                    try { dao.Remove(removeKey); }
                    // Input validation
                    catch (MissingEntryException e) { view.Log(e.getMessage()); }
                    break;
                case "EDIT":
                    // On edit, get the key of the entry to be edited, get updated parameters, then use the map.put() method to replace the entry
                    // If the key was edited (either title or director) delete the old entry and create a new one with the new data
                    String[] newDvdDetails = view.EditDvdArguments();
                    try { dao.Edit(newDvdDetails); }
                    // Input validation
                    catch (DateFormatException e) { view.Log(e.getMessage()); }
                    catch (DuplicateEntryException e) { view.Log(e.getMessage()); }
                    catch (MissingEntryException e) { view.Log(e.getMessage()); }
                    break;
                case "LIST":
                    // On list, print out the ToString() method of all DVDs
                    List<DVD> dvdList = dao.List();
                    view.ListDvds(dvdList);
                    break;
                case "DISPLAY":
                    // On display, print the ToString() of a DVD with a specific key
                    String displayKey = view.GetKeyArguments();
                    DVD dvd = null;
                    // Get the value
                    try { dvd = dao.Display(displayKey); }
                    // Input validation
                    catch (MissingEntryException e) { view.Log(e.getMessage()); }
                    finally { view.Log(dvd.ToString()); }
                    break;
                case "SEARCH":
                    // The search command brings up a second menu asking the user for a search pattern (e.g. search by title, release date, etc...)
                    String[] searchPattern = view.GetSearchPattern();
                    if (searchPattern.equals("CANCEL"))
                        break;
                    // Search with the input pattern and display all DVDs that fit
                    List<DVD> dvdsFound = null;
                    try { dvdsFound = dao.Search(searchPattern); }
                    // Input validation
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
