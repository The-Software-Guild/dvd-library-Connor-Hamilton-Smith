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
    public void Add(String[] dvdDetails) throws DateFormatException, DuplicateEntryException
    {
        // Generating a key from the title and director fields removes the need for a meaningless ID field
        String key = dvdDetails[0] + " " + dvdDetails[3];
        DVD dvd = new DVD(dvdDetails[0], dvdDetails[1], dvdDetails[2], dvdDetails[3], dvdDetails[4], dvdDetails[5]);

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

    public void Edit(String[] dvdDetails) throws DuplicateEntryException, MissingEntryException, DateFormatException
    {
        // Replace the SKIP keyword with the existing entry
        String[] oldDetails = dvds.get(dvdDetails[6]).ToString().split(", ");
        for (int i = 0; i < oldDetails.length; i++)
        {
            if (dvdDetails[i].toUpperCase().equals("SKIP"))
                dvdDetails[i] = oldDetails[i];
            System.out.println(dvdDetails[i] + ", " + oldDetails[i]);
        }

        // Check that the key exists in the map
        if (dvds.containsKey(dvdDetails[6]))
        {
            String newKey = dvdDetails[0] + " " + dvdDetails[3];
            DVD dvd = new DVD(dvdDetails[0], dvdDetails[1], dvdDetails[2], dvdDetails[3], dvdDetails[4], dvdDetails[5]);

            // Check whether the edit has changed the key (by changing either the title or director)
            if (newKey.equals(dvdDetails[6]))
            {
                // If the key is unchanged, just replace the entry at said key with the new data
                dvds.put(dvdDetails[6], dvd);
            }
            // If the key has changed, remove the old entry, then add a new one
            else
            {
                System.out.println("Key changed from " + dvdDetails[6] + " to " + newKey);
                // Check that the new key is not already in use
                if (dvds.containsKey(newKey))
                    throw new DuplicateEntryException();
                else
                {
                    // Delete the old key
                    dvds.remove(dvdDetails[6]);
                    // Re-add the dvd under the new key
                    dvds.put(newKey, dvd);
                }
            }
        }
        else
            throw new MissingEntryException();
    }

    public List<DVD> List()
    {
        List<DVD> dvdList = new ArrayList<>(dvds.values());

        return dvdList;
    }

    public DVD Display(String key) throws MissingEntryException
    {
        //
        if (dvds.containsKey(key))
            return dvds.get(key);
        else
            throw new MissingEntryException();
    }

    public List<DVD> Search(String[] searchPattern) throws DateFormatException
    {
        List<DVD> dvdsFound = new ArrayList<>();
        switch (searchPattern[0])
        {
            case "TITLE":
                for (DVD dvd : dvds.values())
                {
                    if (dvd.getTitle().equals(searchPattern[1]))
                        dvdsFound.add(dvd);
                }
                break;
            case "RATING":
                for (DVD dvd : dvds.values())
                {
                    if (dvd.getMpaaRating().equals(searchPattern[1]))
                        dvdsFound.add(dvd);
                }
                break;
            case "DIRECTOR":
                for (DVD dvd : dvds.values())
                {
                    if (dvd.getDirector().equals(searchPattern[1]))
                        dvdsFound.add(dvd);
                }
                break;
            case "STUDIO":
                for (DVD dvd : dvds.values())
                {
                    if (dvd.getStudio().equals(searchPattern[1]))
                        dvdsFound.add(dvd);
                }
                break;
            case "DATE":
                for (DVD dvd : dvds.values())
                {
                    int releaseDate = dvd.getReleaseDate();
                    if (releaseDate == DVD.formatDateToInt(searchPattern[1]))
                        dvdsFound.add(dvd);
                }
                break;
            case "DATE-GT":
                for (DVD dvd : dvds.values())
                {
                    int releaseDate = dvd.getReleaseDate();
                    if (releaseDate > DVD.formatDateToInt(searchPattern[1]))
                        dvdsFound.add(dvd);
                }
                break;
            case "DATE-LT":
                for (DVD dvd : dvds.values())
                {
                    int releaseDate = dvd.getReleaseDate();
                    if (releaseDate < DVD.formatDateToInt(searchPattern[1]))
                        dvdsFound.add(dvd);
                }
                break;
            case "NOTE":
                for (DVD dvd : dvds.values())
                {
                    if (!dvd.getUserNote().equals(""))
                        dvdsFound.add(dvd);
                }
                break;
        }
         return dvdsFound;
    }
}
