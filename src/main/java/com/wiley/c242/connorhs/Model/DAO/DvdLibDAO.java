package com.wiley.c242.connorhs.Model.DAO;
import com.wiley.c242.connorhs.Model.DAO.StorageIO;
import com.wiley.c242.connorhs.Model.DTO.*;

import java.util.*;

public class DvdLibDAO
{
    private StorageIO io;
    private Map<String, DVD> dvds = new HashMap<>();

    public DvdLibDAO(StorageIO io)
    {
        this.io = io;
    }

    // args: title, releaseDate, mpaaRating, director, studio, userNote
    public void Add(String[] args) throws DateFormatException, DuplicateEntryException
    {
        // Generating a key from the title and director fields removes the need for a meaningless ID field
        String key = args[0] + " " + args[3];
        DVD dvd = new DVD(args[0], args[1], args[2], args[3], args[4], args[5]);

        // Throw an error if the key already exists
        if (dvds.containsKey(key))
            throw new DuplicateEntryException();
        else
            dvds.put(key, dvd);
    }

    public void Remove(String key) throws MissingEntryException
    {
        if (dvds.containsKey(key))
            dvds.remove(key);
        else
            throw new MissingEntryException();
    }

    public void Edit(String key) throws MissingEntryException
    {
//        if (dvds.containsKey(key))
//            dvds.put(key);
//        else
//            throw new MissingEntryException();
    }

    public List<DVD> List()
    {
        List<DVD> dvdList = new ArrayList<>(dvds.values());

        return dvdList;
    }

    public void Display()
    {
        System.out.println("Listing");
    }

    public void Search()
    {
        System.out.println("Searching");
    }
}
