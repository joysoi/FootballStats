package com.example.footballstats.requests.responses;

import com.example.footballstats.models.Competitions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Feed {

    @SerializedName("competitions")
    @Expose
    private List<Competitions> competitionsList;

    public List<Competitions> getCompetitionsList() {
        return competitionsList;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "competitionsList=" + competitionsList +
                '}';
    }
}