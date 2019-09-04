package com.example.footballstats.ui.bottomnav.fragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.footballstats.models.Table;
import com.example.footballstats.repository.FootballRepo;
import com.example.footballstats.util.Resource;

import java.util.List;

import javax.inject.Inject;

public class StandingsFragmentViewModel extends ViewModel {

    private static final String TAG = "StandingsFragmentViewMo";
    private FootballRepo footballRepo;

    @Inject
    StandingsFragmentViewModel(FootballRepo footballRepo) {
        Log.d(TAG, "StandingsFragmentViewModel: is ready");
        this.footballRepo = footballRepo;
    }

    LiveData<Resource<List<Table>>> getLeagueStandings(int id){
        return footballRepo.observeLeagueStandings(id);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        footballRepo.unsubscribeObservables();
    }

}
