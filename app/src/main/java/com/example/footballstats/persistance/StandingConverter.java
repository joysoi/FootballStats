package com.example.footballstats.persistance;

import androidx.room.TypeConverter;

import com.example.footballstats.models.Standing;
import com.example.footballstats.models.Team;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class StandingConverter {

    @TypeConverter
    public static String fromStandingToString(Standing standing) {
        Gson gson = new Gson();
        return gson.toJson(standing);
    }

    @TypeConverter
    public static Standing fromStringStanding(String value) {
        Type type = new TypeToken<Standing>() {
        }.getType();
        return new Gson().fromJson(value, type);
    }
}
