package com.example.footballstats.ui.bottomnav.fragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.footballstats.repository.FootballRepo;
import com.example.footballstats.requests.FootballDataApi;
import com.example.footballstats.requests.responses.LeagueStandings;

import javax.inject.Inject;

public class StandingsFragmentViewModel extends ViewModel {

    private static final String TAG = "StandingsFragmentViewMo";
    private FootballRepo footballRepo;

    @Inject
    StandingsFragmentViewModel(FootballRepo footballRepo) {
        Log.d(TAG, "StandingsFragmentViewModel: Fragment view model is ready");
        this.footballRepo = footballRepo;
    }


    LiveData<LeagueStandings> observeStandingsFromViewModel(String id) {
//        return footballRepo.observeStandingsFromRepo(id);
        return null;
    }
}
