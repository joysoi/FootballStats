package com.example.footballstats.models;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int playerId;

    @SerializedName("name")
    @Expose
    private String playerName;

    public Player() {
    }

    @Ignore
    public Player(String playerName) {
        this.playerName = playerName;
    }


    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return "Player{" +
                ", playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
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
        Player player = (Player) obj;
        return player.getPlayerName().equals(getPlayerName());
    }

}