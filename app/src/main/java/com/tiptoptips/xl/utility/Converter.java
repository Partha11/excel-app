package com.tiptoptips.xl.utility;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @TypeConverter
    public static List<HashMap<String, String>> fromStringToList(String value) {

        Type type = new TypeToken<List<HashMap<String, String>>>() {}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromListToString(List<HashMap<String, String>> list) {

        Type type = new TypeToken<List<HashMap<String, String>>>() {}.getType();
        return new Gson().toJson(list, type);
    }

    @TypeConverter
    public static String fromMapToString(HashMap<String, HashMap<Long, HashMap<String, String>>> list) {

        Type type = new TypeToken<HashMap<String, HashMap<Long, HashMap<String, String>>>>() {}.getType();
        return new Gson().toJson(list, type);
    }

    @TypeConverter
    public static HashMap<String, HashMap<Long, HashMap<String, String>>> fromStringToMap(String data) {

        Type type = new TypeToken<HashMap<String, HashMap<Long, HashMap<String, String>>>>() {}.getType();
        return new Gson().fromJson(data, type);
    }
}
