package com.example.footballstats.requests.responses;

import com.example.footballstats.models.Scorers;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScorersStandings {
    @SerializedName("scorers")
    @Expose
    private List<Scorers> scorersList = null;

    public List<Scorers> getScorersList() {
        return scorersList;
    }

    @Override
    public String toString() {
        return "ScorersResponse{" +
                "scorersList=" + scorersList +
                '}';
    }
}
