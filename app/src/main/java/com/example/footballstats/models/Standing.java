package com.example.footballstats.models;

import androidx.annotation.Nullable;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Standing {

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

    public String getType() {
        return type;
    }

    public List<Table> getTable() {
        return table;
    }


    @Override
    public String toString() {
        return "Standing{" +
                "type='" + type + '\'' +
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
