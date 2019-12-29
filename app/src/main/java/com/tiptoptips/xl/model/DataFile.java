package com.tiptoptips.xl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class DataFile {

    @SerializedName("fileKey")
    @Expose
    private String fileKey;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("creationDate")
    @Expose
    private String creationDate;
    @SerializedName("fileData")
    @Expose
    private List<HashMap<String, String>> fileData;
    @SerializedName("columnTypes")
    @Expose
    private List<Integer> columnTypes;
    @SerializedName("sharedWith")
    @Expose
    private List<Share> sharedWith;

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
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

    public List<HashMap<String, String>> getFileData() {
        return fileData;
    }

    public void setFileData(List<HashMap<String, String>> fileData) {
        this.fileData = fileData;
    }

    public List<Share> getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(List<Share> sharedWith) {
        this.sharedWith = sharedWith;
    }

    public List<Integer> getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(List<Integer> columnTypes) {
        this.columnTypes = columnTypes;
    }
}
