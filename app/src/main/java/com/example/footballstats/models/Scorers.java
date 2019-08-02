package com.example.footballstats.models;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "scorers_table")
public class Scorers {
    @PrimaryKey()
    private int id_Scorers;

    @SerializedName("numberOfGoals")
    @Expose
    private int numberOfGoals;

    @SerializedName("player")
    @Expose
    private Player player;

    @SerializedName("team")
    @Expose
    private Team team;

    public Scorers() {
    }

    @Ignore
    public Scorers(Player player, Team team) {
        this.player = player;
        this.team = team;
    }

    @Ignore
    public Scorers(int numberOfGoals, Player player, Team team) {
        this.numberOfGoals = numberOfGoals;
        this.player = player;
        this.team = team;
    }

    public int getId_Scorers() {
        return id_Scorers;
    }

    public void setId_Scorers(int id_Scorers) {
        this.id_Scorers = id_Scorers;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getNumberOfGoals() {
        return numberOfGoals;
    }

    public void setNumberOfGoals(int numberOfGoals) {
        this.numberOfGoals = numberOfGoals;
    }

    @Override
    public String toString() {
        return "Scorers{" +
                "id_Scorers=" + id_Scorers +
                ", numberOfGoals=" + numberOfGoals +
                ", player=" + player +
                ", team=" + team +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        Scorers scorers = (Scorers) obj;
        return scorers.getId_Scorers() == getId_Scorers() &&
                scorers.getPlayer().equals(getPlayer()) &&
                scorers.getTeam().equals(getTeam())&&
                scorers.getNumberOfGoals() == getNumberOfGoals();
    }
}

