package com.tiptoptips.xl.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.tiptoptips.xl.utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = Constants.FILES_TABLE)
public class UserFile {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = Constants.FIELD_FILE_ID)
    private String id = "";
    @ColumnInfo(name = Constants.FIELD_FILE_NAME)
    private String fileName;
    @ColumnInfo(name = Constants.FIELD_CREATION_DATE)
    private String creationDate;
    @ColumnInfo(name = Constants.FIELD_FILE_URL)
    private String fileUrl;
    @ColumnInfo(name = Constants.FIELD_SHARED_WITH)
    private HashMap<String, String> sharedWith;
    @ColumnInfo(name = Constants.FIELD_FILE_BODY)
    private List<HashMap<String, String>> convertedFileBody;
    @Ignore
    private HashMap<String, HashMap<Long, HashMap<String, String>>> fileBody;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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


    public HashMap<String, HashMap<Long, HashMap<String, String>>> getFileBody() {
        return fileBody;
    }

    public void setFileBody(HashMap<String, HashMap<Long, HashMap<String, String>>> fileBody) {

        this.fileBody = fileBody;

        List<HashMap<String, String>> data = new ArrayList<>();

        for (Map.Entry<String, HashMap<Long, HashMap<String, String>>> entry : fileBody.entrySet()) {

            for (Map.Entry<Long, HashMap<String, String>> node : entry.getValue().entrySet()) {

                data.add(node.getValue());
            }
        }

        setConvertedFileBody(data);
    }

    public List<HashMap<String, String>> getConvertedFileBody() {
        return convertedFileBody;
    }

    public void setConvertedFileBody(List<HashMap<String, String>> convertedFileBody) {
        this.convertedFileBody = convertedFileBody;
    }
}
