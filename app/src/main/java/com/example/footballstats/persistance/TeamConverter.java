package com.example.footballstats.persistance;

import androidx.room.TypeConverter;

import com.example.footballstats.models.Team;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class TeamConverter {

    @TypeConverter
    public static String fromTeamToString(Team team) {
        Gson gson = new Gson();
        return gson.toJson(team);
    }

    @TypeConverter
    public static Team fromStringToTeam(String value) {
        Type type = new TypeToken<Team>() {
        }.getType();
        return new Gson().fromJson(value, type);
    }
}
