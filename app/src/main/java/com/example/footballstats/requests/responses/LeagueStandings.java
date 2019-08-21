package com.example.footballstats.requests.responses;

import com.example.footballstats.models.Competitions;
import com.example.footballstats.models.Standing;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeagueStandings {

    @SerializedName("competition")
    @Expose
    private Competitions competition;

    @SerializedName("standings")
    @Expose
    private List<Standing> standings = null;

    public Competitions getCompetition() {
        return competition;
    }

    public void setCompetition(Competitions competition) {
        this.competition = competition;
    }

    public List<Standing> getStandings() {
        return standings;
    }

    public void setStandings(List<Standing> standings) {
        this.standings = standings;
    }

    @Override
    public String toString() {
        return "LeagueStandings{" +
                "competition=" + competition +
                ", standings=" + standings +
                '}';
    }
}
