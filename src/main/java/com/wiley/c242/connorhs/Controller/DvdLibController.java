package com.wiley.c242.connorhs.Controller;
import com.wiley.c242.connorhs.Model.DTO.DVD;
import com.wiley.c242.connorhs.View.DvdLibView;
import com.wiley.c242.connorhs.Model.DAO.DvdLibDAO;
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
                    String[] args = view.GetDvdArguments();
                    dao.Add(args);
                    break;
                case "REMOVE":
                    dao.Remove();
                    break;
                case "EDIT":
                    dao.Edit();
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
