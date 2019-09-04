package com.example.footballstats.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.footballstats.models.Competitions;
import com.example.footballstats.models.Player;
import com.example.footballstats.models.Scorers;
import com.example.footballstats.models.Standing;
import com.example.footballstats.models.Table;
import com.example.footballstats.models.Team;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

@Dao
public interface FootballDao {

    // Competition standings
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertCompetitions(Competitions... competitions);

    @Query("UPDATE competitions_table SET competitionName = :competitionName " +
            "WHERE competitionId = :competitionId")
    int updateCompetitions(int competitionId, String competitionName);

    @Query("SELECT * FROM competitions_table")
    LiveData<List<Competitions>> getAllCompetitions();


    // Table Standings
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertTableData(Table... tableData);

    @Query("UPDATE standings_table SET position = :position, team = :teamName, points = :points " +
            "WHERE team = :teamName")
    int updateTableData(int position, String teamName, int points);

    @Query("SELECT * FROM standings_table")
    LiveData<List<Table>> getAllTableData();


    // Scorers standings
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertScorers(Scorers... scorers);

    @Query("UPDATE scorers_table SET numberOfGoals = :numberOfGoals, team = :teamName, player = :playerName " +
            "WHERE team = :teamName")
    int updateScorers(int numberOfGoals, String teamName, String playerName);

    @Query("SELECT * FROM scorers_table ORDER BY numberOfGoals DESC")
    LiveData<List<Scorers>> getAllScorers();

}
