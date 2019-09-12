package com.example.footballstats.ui.standings;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.footballstats.models.Competitions;
import com.example.footballstats.repository.FootballRepo;
import com.example.footballstats.util.Resource;

import java.util.List;

import javax.inject.Inject;

public class StandingsListViewModel extends ViewModel {

    private static final String TAG = "StandingsListViewModel";
    private FootballRepo footballRepo;

    @Inject
    StandingsListViewModel(FootballRepo footballRepo) {
        Log.d(TAG, "StandingsListViewModel: is connected");
        this.footballRepo = footballRepo;
    }

    LiveData<Resource<List<Competitions>>> getFeed() {
        return footballRepo.observeFeed();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        footballRepo.unsubscribeObservables();
    }
}
