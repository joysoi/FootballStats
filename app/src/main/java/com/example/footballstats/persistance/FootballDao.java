package com.example.footballstats.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.footballstats.models.Competitions;
import com.example.footballstats.models.Player;
import com.example.footballstats.models.Scorers;
import com.example.footballstats.models.Standing;
import com.example.footballstats.models.Table;
import com.example.footballstats.models.Team;

import java.util.List;

@Dao
public interface FootballDao {

    //  Competitions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertCompetitions(Competitions[] competitions);

    @Query("UPDATE competitions_table SET competitionName = :competitionName " +
            "WHERE competitionId = :competitionId")
    int updateCompetitions(int competitionId, String competitionName);

    @Query("SELECT * FROM competitions_table")
    LiveData<List<Competitions>> getAllCompetitions();


    // Table Standings
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTableData(Table tableData);

    @Query("UPDATE standings_table SET position = :position, team = :team, points = :points " +
            "WHERE teamId = :teamId")
    int updateTableData(int teamId, int position, Team team, int points);

    @Query("SELECT * FROM standings_table ORDER BY points DESC")
    LiveData<List<Table>> getAllTableData();


    // Team names
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTeamNames(Team teamName);

    @Query("UPDATE team_table SET teamId = :teamId, name= :name " +
            "WHERE idTeam = :idTeam")
    void updateTeamNames(int idTeam, int teamId, String name);

    @Query("SELECT * FROM team_table WHERE idTeam = :position")
    LiveData<Team> getAllTeamNames(int position);


    // Scorers
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScorers(Scorers scorers);

    @Query("UPDATE scorers_table SET numberOfGoals = :numberOfGoals, team = :team, player = :player " +
            "WHERE id_Scorers = :id_Scorers")
    void updateScorers(int id_Scorers, int numberOfGoals, Team team, Player player);

    @Query("SELECT * FROM scorers_table ORDER BY numberOfGoals DESC")
    LiveData<List<Scorers>> getAllScorers();


    //Players
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlayers(Player player);

    @Query("UPDATE players_table SET playerId = :playerId, playerName= :playerName " +
            "WHERE idPlayer = :idPlayer")
    void updatePlayers(int idPlayer, int playerId, String playerName);

    @Query("SELECT * FROM players_table WHERE idPlayer = :getId_Scorers")
    LiveData<Player> getAllPlayers(int getId_Scorers);


    // Team names of scorers
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScorersTeamNames(Team teamScorersName);

    @Query("SELECT * FROM team_table WHERE idTeam = :numOfGoals")
    LiveData<Team> getAllScorersTeamNames(int numOfGoals);

}
