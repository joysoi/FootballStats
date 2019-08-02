package com.example.footballstats.models;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "standings_table")
public class Table {

    @PrimaryKey
    private int teamId;

    @SerializedName("position")
    @Expose
    private Integer position;

    @SerializedName("team")
    @Expose
    private Team team;

    @SerializedName("points")
    @Expose
    private Integer points;


    public Table() {
    }

    @Ignore
    public Table(Integer position, Team team, Integer points) {
        this.position = position;
        this.team = team;
        this.points = points;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Table{" +
                "teamId=" + teamId +
                ", position=" + position +
                ", team=" + team +
                ", points=" + points +
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
        Table table = (Table) obj;
        return table.getTeamId() == getTeamId() &&
                table.getPosition().equals(getPosition())
                && table.getPoints().equals(getPoints());
    }
}
