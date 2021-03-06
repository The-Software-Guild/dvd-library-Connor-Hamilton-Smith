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
        // Create a new view object for user interaction
        DvdLibView view = new DvdLibView(new ConsoleUserIO());

        // The path at which the data file will be stored
        String storageFilePath = "src/main/java/com/wiley/c242/connorhs/Model/DVDs.txt";
        DvdLibDAO dao = null;
        // Create a new data access object, catching and logging any potential file path errors
        try { dao = new DvdLibDAO(new FileStorageIO(storageFilePath)); }
        catch (Exception e) { view.Log(e.getMessage()); }

        // Create the controller object with dependency injection
        DvdLibController controller = new DvdLibController(dao, view);

        // Run the program
        controller.Run();
    }
}
