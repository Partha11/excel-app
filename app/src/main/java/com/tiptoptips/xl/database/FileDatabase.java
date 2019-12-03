package com.tiptoptips.xl.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tiptoptips.xl.model.UserFile;
import com.tiptoptips.xl.utility.Constants;
import com.tiptoptips.xl.utility.Converter;

@Database(entities = {UserFile.class}, version = Constants.DATABASE_VERSION, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class FileDatabase extends RoomDatabase {

    private static FileDatabase instance;
    public abstract FileDao getFileDao();

    public static synchronized FileDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context, FileDatabase.class, Constants.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
