package com.wiley.c242.connorhs.Model.DAO;

import com.wiley.c242.connorhs.Model.DTO.DVD;
import com.wiley.c242.connorhs.Model.DTO.DateFormatException;
import com.wiley.c242.connorhs.Model.DTO.FileIOException;
import java.io.*;
import java.util.*;
import static java.lang.System.out;

public class FileStorageIO implements StorageIO
{
    private String path;
    public FileStorageIO(String filePath)
    {
        path = filePath;
    }

    // An override of the interface method for loading data from storage. Implements storage retrieval from file
    @Override
    public Map LoadData() throws FileIOException
    {
        // Create a scanner object to read the file
        Scanner input;
        try { input = new Scanner( new BufferedReader( new FileReader(path))); }
        // Throw an exception if the file path is not recognised
        catch (FileNotFoundException e) { throw new FileIOException(true); }

        String dvdString;
        String[] dvdDetails = { "", "", "", "", "", "" };
        DVD dvd = null;
        Map<String, DVD> dvdMap = new HashMap<>();
        // Loop through each line in the file
        while (input.hasNextLine())
        {
            // If the line exists, convert the string data into an object and add it to the data map
            dvdString = input.nextLine();
            if (dvdString != null)
            {
                dvdDetails = dvdString.split("::");
                // Some data entries may have one fewer detail (no user note) and must be handled separately
                // In either case, the DVD constructor is called to create an object with the new data
                if (dvdDetails.length == 6)
                {
                    try { dvd = new DVD(dvdDetails[0], dvdDetails[1], dvdDetails[2], dvdDetails[3], dvdDetails[4], dvdDetails[5]); }
                    catch (Exception e) { dvd = null; System.out.println(dvdString); }
                }
                else
                {
                    try { dvd = new DVD(dvdDetails[0], dvdDetails[1], dvdDetails[2], dvdDetails[3], dvdDetails[4], ""); }
                    catch (Exception e) { dvd = null; System.out.println(dvdString); }
                }
            }
            // If something went wrong and the DVD was not loaded successfully, do not try to add it to the map
            if (dvd != null)
                dvdMap.put(dvdDetails[0] + " " + dvdDetails[3], dvd);
        }
        // Close the input stream
        input.close();
        return dvdMap;
    }

    // An override of the interface method for saving data to storage. Implements data storage to file
    @Override
    public void SaveData(Map data) throws FileIOException
    {
        // Create a print writer for writing to file
        PrintWriter output;
        try { output = new PrintWriter(new FileWriter(path)); }
        catch (IOException e) { throw new FileIOException(false); }

        // Convert DVD objects to strings and write them to file
        List<DVD> dvdList = new ArrayList<DVD>(data.values());
        for (DVD dvd : dvdList)
        {
            // Use accessors to concatenate a string with all relevant data
            String dvdString = dvd.getTitle() + "::" + dvd.getStringReleaseDate() + "::" + dvd.getMpaaRating() + "::" +
                    dvd.getDirector() + "::" + dvd.getStudio() + "::" + dvd.getUserNote();
            // Write to file
            output.println(dvdString);
            output.flush();
        }
        // Close output stream
        output.close();
    }
}
