package com.example.footballstats.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "test_table")
public class TestModel {
    @PrimaryKey
    private int id;
    private String name;

    public TestModel() {
    }

    @Ignore
    public TestModel(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
