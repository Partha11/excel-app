package com.tiptoptips.xl.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tiptoptips.xl.model.UserFile;
import com.tiptoptips.xl.utility.Constants;

import java.util.List;

@Dao
public interface FileDao {

    @Insert
    void insertFiles(UserFile... files);

    @Insert
    void insertFile(UserFile file);

    @Query("SELECT * FROM " + Constants.FILES_TABLE)
    List<UserFile> getUserFiles();
}
