package com.tiptoptips.xl.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.tiptoptips.xl.utility.Constants;

@Entity(tableName = Constants.FILE_INFO)
public class SingleFile {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = Constants.FIELD_FILE_NAME)
    private String fileName = "";
    @ColumnInfo(name = Constants.FIELD_FILE_URL)
    private String fileUrl;
    @ColumnInfo(name = Constants.FIELD_FILE_BODY)
    private String fileBody;

    @NonNull
    public String getFileName() {
        return fileName;
    }

    public void setFileName(@NonNull String fileName) {
        this.fileName = fileName;
    }

    public String getFileBody() {
        return fileBody;
    }

    public void setFileBody(String fileBody) {
        this.fileBody = fileBody;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
