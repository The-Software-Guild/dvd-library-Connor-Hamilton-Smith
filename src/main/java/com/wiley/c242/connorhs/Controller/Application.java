package com.wiley.c242.connorhs.Controller;
import com.wiley.c242.connorhs.Controller.DvdLibController;
import com.wiley.c242.connorhs.Model.DAO.DvdLibDAO;
import com.wiley.c242.connorhs.Model.DAO.FileStorageIO;
import com.wiley.c242.connorhs.View.DvdLibView;
import com.wiley.c242.connorhs.View.ConsoleUserIO;

public class Application
{
    public static void main (String[] args)
    {
        DvdLibDAO dao = new DvdLibDAO(new FileStorageIO());
        DvdLibView view = new DvdLibView(new ConsoleUserIO());

        DvdLibController controller = new DvdLibController(dao, view);

        controller.Run();
    }
}
