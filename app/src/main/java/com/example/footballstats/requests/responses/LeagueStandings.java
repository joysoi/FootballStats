package com.example.footballstats.requests.responses;

import com.example.footballstats.models.Standing;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeagueStandings {
    @SerializedName("standings")
    @Expose
    private List<Standing> standings = null;

    public List<Standing> getStandings() {
        return standings;
    }

    @Override
    public String toString() {
        return "LeagueStandings{" +
                "standings=" + standings +
                '}';
    }
}
