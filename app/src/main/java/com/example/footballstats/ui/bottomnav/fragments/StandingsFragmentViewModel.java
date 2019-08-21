package com.example.footballstats.ui.bottomnav.fragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.footballstats.models.Standing;
import com.example.footballstats.models.Table;
import com.example.footballstats.repository.FootballRepo;

import java.util.List;

import javax.inject.Inject;

public class StandingsFragmentViewModel extends ViewModel {

    private static final String TAG = "StandingsFragmentViewMo";
    private FootballRepo footballRepo;
//    private MediatorLiveData<List<Table>> mediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<List<Table>> mediatorLiveData = new MediatorLiveData<>();

    @Inject
    StandingsFragmentViewModel(FootballRepo footballRepo) {
        Log.d(TAG, "StandingsFragmentViewModel: fragment view model is ready");
        this.footballRepo = footballRepo;
    }

    public LiveData<List<Table>> getLeagueStandings(int id){
        Log.d(TAG, "getLeagueStandings: ID: " + id);
        observeLeagueStandings(id);
        return mediatorLiveData;
    }

    private void observeLeagueStandings(int id){
        LiveData<List<Table>> standingLiveData = footballRepo.observeLeagueStandings(id);
        Log.d(TAG, "getLeagueStandings: ID: " + id);
        mediatorLiveData.addSource(standingLiveData, new Observer<List<Table>>() {
            @Override
            public void onChanged(List<Table> standing) {
                mediatorLiveData.setValue(standing);
            }
        });
    }


//    public LiveData<List<Standing>> getLeagueStandings(String id){
//        Log.d(TAG, "observeLeagueStandings: COMP ID IN VM: " + id);
//        observeLeagueStandings(id);
//        return mediatorLiveData;
//    }
//
//    private void observeLeagueStandings(String id){
//        Log.d(TAG, "observeLeagueStandings: COMP ID IN VM: " + id);
//        LiveData<List<Standing>> standingLiveData = footballRepo.observeLeagueStandings(id);
//        mediatorLiveData.addSource(standingLiveData, new Observer<List<Standing>>() {
//            @Override
//            public void onChanged(List<Standing> standing) {
//                mediatorLiveData.setValue(standing);
//            }
//        });
//    }


    @Override
    protected void onCleared() {
        super.onCleared();
        footballRepo.unsubscribeObservables();
    }

}
