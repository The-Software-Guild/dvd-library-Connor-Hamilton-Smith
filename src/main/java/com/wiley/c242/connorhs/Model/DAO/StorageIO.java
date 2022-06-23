package com.wiley.c242.connorhs.Model.DAO;
import com.wiley.c242.connorhs.Model.DTO.FileIOException;
import java.util.Map;

public interface StorageIO
{
    public Map LoadData() throws FileIOException;
    public void SaveData(Map data) throws FileIOException;
}
