package com.example.footballstats.models;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "league_standings_table")
public class Standing {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private int standingsId;

    @SerializedName("type")
    @Expose
    private String type = null;

    @SerializedName("table")
    @Expose
    private List<Table> table = null;

    public Standing() {
    }

    @Ignore
    public Standing(String type) {
        this.type = type;
    }


    public int getStandingsId() {
        return standingsId;
    }

    public void setStandingsId(int standingsId) {
        this.standingsId = standingsId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Table> getTable() {
        return table;
    }

    public void setTable(List<Table> table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "Standing{" +
                "standingsId=" + standingsId +
                ", type='" + type + '\'' +
                ", table=" + table +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }

        if(getClass() != obj.getClass()){
            return false;
        }
        Standing standing = (Standing) obj;
        return standing.getType().equals(getType());
    }
}
