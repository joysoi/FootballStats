package com.example.footballstats.ui.bottomnav.fragments;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.footballstats.repository.FootballRepo;
import com.example.footballstats.requests.responses.ScorersStandings;

import javax.inject.Inject;

public class ScorersFragmentViewModel extends ViewModel {

    private static final String TAG = "ScorersFragViewModel";
    private FootballRepo footballRepo;

    @Inject
    public ScorersFragmentViewModel(FootballRepo footballRepo) {
        Log.d(TAG, "ScorersFragmentViewModel: is ready");
        this.footballRepo = footballRepo;
    }

    LiveData<ScorersStandings> observeScorersFromViewModel(String id) {
//        return footballRepo.observeScorersFromRepo(id);
        return null;
    }
}
