package com.example.footballstats.ui.standings;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.footballstats.models.Competitions;
import com.example.footballstats.repository.FootballRepo;

import java.util.List;

import javax.inject.Inject;

public class StandingsListViewModel extends ViewModel {

    private static final String TAG = "StandingsListViewModel";
    private FootballRepo footballRepo;
    private MediatorLiveData<List<Competitions>> mediatorLiveData = new MediatorLiveData<>();

    @Inject
    StandingsListViewModel(FootballRepo footballRepo) {
        Log.d(TAG, "StandingsListViewModel: is connected");
        this.footballRepo = footballRepo;
    }

    public LiveData<List<Competitions>> getFeed() {
        observeFeed();
        return mediatorLiveData;
    }

    private void observeFeed() {
        LiveData<List<Competitions>> resourceLiveData = footballRepo.observeFeed();
        mediatorLiveData.addSource(resourceLiveData, new Observer<List<Competitions>>() {
            @Override
            public void onChanged(List<Competitions> listResource) {
                mediatorLiveData.setValue(listResource);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        footballRepo.unsubscribeObservables();
    }
}


