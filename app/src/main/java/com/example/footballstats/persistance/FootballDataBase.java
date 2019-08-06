package com.example.footballstats.persistance;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.footballstats.models.Competitions;
import com.example.footballstats.models.Player;
import com.example.footballstats.models.Scorers;
import com.example.footballstats.models.Standing;
import com.example.footballstats.models.Table;
import com.example.footballstats.models.Team;
import com.example.footballstats.models.TestModel;

@Database(entities = {Competitions.class, Team.class, Table.class, Scorers.class, Player.class, Standing.class, TestModel.class},
        version = 127, exportSchema = false)
@TypeConverters({PlayerConverter.class, TeamConverter.class, TableListConverter.class})
public abstract class FootballDataBase extends RoomDatabase {

    public abstract FootballDao getFootballDao();
}