package com.example.footballstats.ui.bottomnav.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballstats.R;
import com.example.footballstats.adapters.StandingsAdapter;
import com.example.footballstats.models.Competitions;
import com.example.footballstats.models.Standing;
import com.example.footballstats.models.Table;
import com.example.footballstats.util.Constants;
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
                .observe(getViewLifecycleOwner(), new Observer<List<Table>>() {
                    @Override
                    public void onChanged(List<Table> tableList) {
                        if (tableList != null) {
                            Log.d(TAG, "onChanged: TABLE LIST: " + tableList);
                            standingsAdapter.submitList(tableList);
                        }
                    }
                });
    }
}
