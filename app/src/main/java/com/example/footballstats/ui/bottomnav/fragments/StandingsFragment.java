package com.example.footballstats.ui.bottomnav.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballstats.R;
import com.example.footballstats.adapters.StandingsAdapter;
import com.example.footballstats.models.Competitions;
import com.example.footballstats.models.Table;
import com.example.footballstats.util.Constants;
import com.example.footballstats.util.Resource;
import com.example.footballstats.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class StandingsFragment extends DaggerFragment {

    private static final String TAG = "StandingsFragment";
    private StandingsFragmentViewModel standingsFragmentViewModel;
    private StandingsAdapter standingsAdapter;
    private RecyclerView recyclerView;
    private Competitions competitions;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standings, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewStandings);
        initRecyclerView();
        if (getArguments() != null) {
            competitions = getArguments().getParcelable(Constants.COMPETITIONS_INTENT);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        standingsFragmentViewModel = ViewModelProviders.of(this, providerFactory)
                .get(StandingsFragmentViewModel.class);
        if (competitions != null) {
            subscribeObservers(competitions.getCompetitionId());
        }

    }

    private void initRecyclerView() {
        standingsAdapter = new StandingsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(standingsAdapter);
    }

    private void subscribeObservers(final int competitionId) {
        standingsFragmentViewModel.getLeagueStandings(competitionId)
                .removeObservers(getViewLifecycleOwner());

        standingsFragmentViewModel.getLeagueStandings(competitionId)
                .observe(getViewLifecycleOwner(), new Observer<Resource<List<Table>>>() {
                    @Override
                    public void onChanged(Resource<List<Table>> tableList) {
                        if (tableList != null) {
                            if(tableList.data != null){
                                switch (tableList.status){
                                    case LOADING:{
                                        Log.d(TAG, "onChanged: Status LOADING");
                                        standingsAdapter.displayLoading();
                                        break;
                                    }
                                    case ERROR:{
                                        Log.e(TAG, "onChanged: cannot refresh the cache." );
                                        Log.e(TAG, "onChanged: ERROR message: " + tableList.message );
                                        Log.e(TAG, "onChanged: status: ERROR, #Table List size: " + tableList.data.size());
                                        Toast.makeText(getActivity(), tableList.message, Toast.LENGTH_SHORT).show();
                                        standingsAdapter.setTableList(tableList.data);
                                        break;
                                    }
                                    case SUCCESS:{
                                        Log.d(TAG, "onChanged: cache has been refreshed.");
                                        Log.d(TAG, "onChanged: status: SUCCESS, #Table List size:: " + tableList.data.size());
                                        standingsAdapter.setTableList(tableList.data);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
    }
}
