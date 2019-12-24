package com.tiptoptips.xl.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tiptoptips.xl.model.SingleFile;
import com.tiptoptips.xl.model.UserFile;
import com.tiptoptips.xl.utility.Constants;

import java.util.List;

@Dao
public interface FileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFiles(UserFile... files);

    @Insert
    void insertFile(UserFile file);

    @Insert
    void insertFilesInfo(SingleFile... files);

    @Insert
    void insertFileInfo(SingleFile file);

    @Query("SELECT * FROM " + Constants.FILES_TABLE)
    List<UserFile> getUserFiles();

    @Query("SELECT " + Constants.FIELD_FILE_URL + " FROM " + Constants.FILES_TABLE)
    List<String> getAllFileUrl();

    @Query("SELECT " + Constants.FIELD_FILE_URL + " FROM " + Constants.FILE_INFO)
    List<String> getSavedFileUrl();

    @Query("SELECT * FROM " + Constants.FILES_TABLE + " WHERE " + Constants.FIELD_FILE_ID + " = :id")
    UserFile getFileFromId(String id);

    @Query("SELECT * FROM " + Constants.FILE_INFO + " WHERE " + Constants.FIELD_FILE_NAME + " = :id")
    SingleFile getFileBodyFromId(String id);
}
