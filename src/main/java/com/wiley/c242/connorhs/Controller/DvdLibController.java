package com.wiley.c242.connorhs.Controller;
import com.wiley.c242.connorhs.View.DvdLibView;
import com.wiley.c242.connorhs.Model.DAO.DvdLibDAO;

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

    }
}
