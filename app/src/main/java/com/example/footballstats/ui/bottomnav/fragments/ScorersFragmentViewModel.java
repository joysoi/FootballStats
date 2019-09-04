package com.example.footballstats.ui.bottomnav.fragments;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.footballstats.models.Scorers;
import com.example.footballstats.repository.FootballRepo;
import com.example.footballstats.util.Resource;

import java.util.List;

import javax.inject.Inject;

public class ScorersFragmentViewModel extends ViewModel {

    private static final String TAG = "ScorersFragViewModel";
    private FootballRepo footballRepo;

    @Inject
    ScorersFragmentViewModel(FootballRepo footballRepo) {
        Log.d(TAG, "ScorersFragmentViewModel: is ready");
        this.footballRepo = footballRepo;
    }

    LiveData<Resource<List<Scorers>>> observeScorersFromViewModel(int id) {
        return footballRepo.observeScorers(id);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        footballRepo.unsubscribeObservables();
    }
}
