package com.example.footballstats.persistance;

import androidx.room.TypeConverter;

import com.example.footballstats.models.Standing;
import com.example.footballstats.models.Table;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class StandingsListConverter implements Serializable {
    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<Standing> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Standing>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Standing> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Standing>>() {
        }.getType();
        List<Standing> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }


}
