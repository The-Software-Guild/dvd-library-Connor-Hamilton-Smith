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

    @Override
    public Map LoadData() throws FileIOException
    {
        Scanner input;
        try { input = new Scanner( new BufferedReader( new FileReader(path))); }
        catch (FileNotFoundException e) { throw new FileIOException(true); }

        String dvdString;
        String[] dvdDetails = { "", "", "", "", "", "" };
        DVD dvd = null;

        Map<String, DVD> dvdMap = new HashMap<>();
        while (input.hasNextLine())
        {
            dvdString = input.nextLine();
            if (dvdString != null)
            {
                dvdDetails = dvdString.split("::");
                if (dvdDetails.length == 6)
                {
                    try { dvd = new DVD(dvdDetails[0], dvdDetails[1], dvdDetails[2], dvdDetails[3], dvdDetails[4], dvdDetails[5]); }
                    catch (Exception e) { dvd = null; }
                }
                else
                {
                    try { dvd = new DVD(dvdDetails[0], dvdDetails[1], dvdDetails[2], dvdDetails[3], dvdDetails[4], ""); }
                    catch (Exception e) { dvd = null; }
                }
            }
            if (dvd != null)
                dvdMap.put(dvdDetails[0] + " " + dvdDetails[3], dvd);
        }

        input.close();
        return dvdMap;
    }

    @Override
    public void SaveData(Map data) throws FileIOException
    {
        PrintWriter output;
        try { output = new PrintWriter(new FileWriter(path)); }
        catch (IOException e) { throw new FileIOException(false); }

        List<DVD> dvdList = new ArrayList<DVD>(data.values());
        for (DVD dvd : dvdList)
        {
            String dvdString = dvd.getTitle() + "::" + dvd.getStringReleaseDate() + "::" + dvd.getMpaaRating() + "::" +
                    dvd.getDirector() + "::" + dvd.getStudio() + "::" + dvd.getUserNote();
            output.println(dvdString);
            output.flush();
        }

        output.close();
    }
}
