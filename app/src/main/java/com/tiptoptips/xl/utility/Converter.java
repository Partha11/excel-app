package com.tiptoptips.xl.utility;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;

public class Converter implements Serializable {

    @TypeConverter
    public static HashMap<String, String> fromString(String value) {

        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromHashMap(HashMap<String, String> map) {

        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
        return new Gson().toJson(map, type);
    }
}
