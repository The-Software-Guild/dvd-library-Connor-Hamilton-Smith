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

    /*
        Data Commands
     */
    // Add: args(dvdDetails = { title, releaseDate, mpaaRating, director, studio, userNote })
    public void Add(String[] dvdDetails) throws DateFormatException, DuplicateEntryException
    {
        // Generating a key from the title and director fields removes the need for a meaningless ID field
        String key = dvdDetails[0] + " " + dvdDetails[3];
        DVD dvd = new DVD(dvdDetails[0], dvdDetails[1], dvdDetails[2], dvdDetails[3], dvdDetails[4], dvdDetails[5]);

        // Throw an error if the key already exists
        if (dvds.containsKey(key))
            throw new DuplicateEntryException();
        // If the key does not exist, add it to the map
        else
            dvds.put(key, dvd);
    }

    // Remove: args(key = "Title Director")
    public void Remove(String key) throws MissingEntryException
    {
        // If the key exists, remove it from the map. Otherwise, throw an error
        if (dvds.containsKey(key))
            dvds.remove(key);
        else
            throw new MissingEntryException();
    }

    // Edit: args(dvdDetails = { title, releaseDate, mpaaRating, director, studio, userNote })
    public void Edit(String[] dvdDetails) throws DuplicateEntryException, MissingEntryException, DateFormatException
    {
        // If the SKIP keyword was used, replace it with the existing entry
        String[] oldDetails = new String[6];
        try { oldDetails = dvds.get(dvdDetails[6]).ToString().split(", "); }
        catch (Exception e) { throw new MissingEntryException(); }

        for (int i = 0; i < oldDetails.length; i++)
        {
            if (dvdDetails[i].toUpperCase().equals("SKIP"))
                dvdDetails[i] = oldDetails[i];
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

    // List: return(list of DVDs)
    public List<DVD> List()
    {
        // Get all DVDs from the map
        List<DVD> dvdList = new ArrayList<>(dvds.values());

        return dvdList;
    }

    // Display: args(key = "Title Director"), return(matching DVD)
    public DVD Display(String key) throws MissingEntryException
    {
        // If the key exists in the map, return the corresponding DVD. Otherwise, throw an error
        if (dvds.containsKey(key))
            return dvds.get(key);
        else
            throw new MissingEntryException();
    }

    // Search: args(searchPattern = {"search field", "search pattern"} e.g. {"TITLE", "Avatar"}), return(a list of all DVDs that match the pattern)
    public List<DVD> Search(String[] searchPattern) throws DateFormatException
    {
        List<DVD> dvdsFound = new ArrayList<>();
        // Search depending on pattern
        switch (searchPattern[0])
        {
            // For title, rating, director studio: add the DVD to the list if it 's corresponding property matches the search parameter
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
            // For the date patterns, the search parameter must be converted to an int using the static function in DVD before comparison
            case "DATE":
                for (DVD dvd : dvds.values())
                {
                    int releaseDate = dvd.getReleaseDate();
                    if (releaseDate == DVD.formatDateToInt(searchPattern[1]))
                        dvdsFound.add(dvd);
                }
                break;
            // Check for dates greater than the search parameter
            case "DATE-GT":
                for (DVD dvd : dvds.values())
                {
                    int releaseDate = dvd.getReleaseDate();
                    if (releaseDate > DVD.formatDateToInt(searchPattern[1]))
                        dvdsFound.add(dvd);
                }
                break;
            // Check for dates less than the search parameter
            case "DATE-LT":
                for (DVD dvd : dvds.values())
                {
                    int releaseDate = dvd.getReleaseDate();
                    if (releaseDate < DVD.formatDateToInt(searchPattern[1]))
                        dvdsFound.add(dvd);
                }
                break;
            // List all DVDs which have a non-empty user note
            case "NOTE":
                for (DVD dvd : dvds.values())
                {
                    if (!(dvd.getUserNote().equals(" ") || dvd.getUserNote().equals("")))
                        dvdsFound.add(dvd);
                }
                break;
        }
         return dvdsFound;
    }


    /*
        Data I/O
     */
    // Use the io interface to load data from storage
    public void LoadDVDs() throws FileIOException
    {
        this.dvds = io.LoadData();
    }

    // Use the io interface to save data to storage
    public void SaveDVDs() throws FileIOException
    {
        io.SaveData(this.dvds);
    }
}
