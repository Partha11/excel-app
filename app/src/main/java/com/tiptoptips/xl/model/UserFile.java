package com.tiptoptips.xl.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.tiptoptips.xl.utility.Constants;

import java.util.HashMap;

@Entity(tableName = Constants.FILES_TABLE)
public class UserFile {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.FIELD_FILE_ID)
    private int id;
    @ColumnInfo(name = Constants.FIELD_FILENAME)
    private String fileName;
    @ColumnInfo(name = Constants.FIELD_CREATION_DATE)
    private String creationDate;
    @ColumnInfo(name = Constants.FIELD_FILE_URL)
    private String fileUrl;
    @ColumnInfo(name = Constants.FIELD_SHARED_WITH)
    private HashMap<String, String> sharedWith;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public HashMap<String, String> getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(HashMap<String, String> sharedWith) {
        this.sharedWith = sharedWith;
    }
}
