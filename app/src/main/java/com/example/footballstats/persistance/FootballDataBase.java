package com.example.footballstats.persistance;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.footballstats.models.Competitions;
import com.example.footballstats.models.Player;
import com.example.footballstats.models.Scorers;
import com.example.footballstats.models.Standing;
import com.example.footballstats.models.Table;

@Database(entities = {Competitions.class, Table.class, Scorers.class},
        version = 13)
@TypeConverters({PlayerConverter.class, TeamConverter.class})
public abstract class FootballDataBase extends RoomDatabase {
    public abstract FootballDao getFootballDao();
}