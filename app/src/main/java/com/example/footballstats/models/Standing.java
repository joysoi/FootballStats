package com.example.footballstats.models;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Standing {

    @SerializedName("type")
    @Expose
    private String type = null;

    @SerializedName("table")
    @Expose
    private List<Table> tableList = null;

    private int timestamp;

    public Standing() {
    }

    @Ignore
    public Standing(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public void setTableList(List<Table> tableList) {
        this.tableList = tableList;
    }


    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Standing{" +
                ", type='" + type + '\'' +
                ", tableList=" + tableList +
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
        Standing standing = (Standing) obj;
        return standing.getType().equals(getType())
                && standing.getTableList().equals(getTableList());
    }
}
