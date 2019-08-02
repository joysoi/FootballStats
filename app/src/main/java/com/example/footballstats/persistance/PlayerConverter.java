package com.example.footballstats.persistance;

import androidx.room.TypeConverter;

import com.example.footballstats.models.Player;
import com.example.footballstats.models.Team;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PlayerConverter {

    @TypeConverter
    public static String fromPlayerToString(Player player) {
        Gson gson = new Gson();
        return gson.toJson(player);
    }

    @TypeConverter
    public static Player fromStringToPlayer(String value) {
        Type type = new TypeToken<Player>() {
        }.getType();
        return new Gson().fromJson(value, type);
    }
}
