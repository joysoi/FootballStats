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
import com.example.footballstats.adapters.ScorersAdapter;
import com.example.footballstats.models.Competitions;
import com.example.footballstats.requests.responses.ScorersStandings;
import com.example.footballstats.util.Constants;
import com.example.footballstats.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ScorersFragment extends DaggerFragment {

    private static final String TAG = "ScorersFragment";
    private ScorersFragmentViewModel scorersFragmentViewModel;
    private RecyclerView recyclerView;
    private ScorersAdapter scorersAdapter;

    @Inject
    ViewModelProviderFactory providerFactory;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scorers, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewScorers);
        initRecyclerView();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        scorersFragmentViewModel = ViewModelProviders.of(this, providerFactory)
                .get(ScorersFragmentViewModel.class);
        Toast.makeText(getActivity(), "this is Scorers Fragment", Toast.LENGTH_SHORT).show();
        if (getArguments() != null) {
            Competitions competitions = getArguments().getParcelable(Constants.COMPETITIONS_INTENT);
            Log.d(TAG, "onViewCreated: comp: " + competitions);
            if (competitions != null) {
//                subscribeObservers(competitions.getCompetitionId());
            }
        }
    }

    private void initRecyclerView() {
        scorersAdapter = new ScorersAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(scorersAdapter);
    }

    private void subscribeObservers(int compId) {
        scorersFragmentViewModel.observeScorersFromViewModel(String.valueOf(compId)).removeObservers(getViewLifecycleOwner());
        scorersFragmentViewModel.observeScorersFromViewModel(String.valueOf(compId))
                .observe(getViewLifecycleOwner(), new Observer<ScorersStandings>() {
                    @Override
                    public void onChanged(ScorersStandings scorersStandings) {
                        Log.d(TAG, "onChanged: scorers info: " + scorersStandings);
                        Log.d(TAG, "onChanged: scorers info list: " + scorersStandings.getScorersList());
                        scorersAdapter.submitList(scorersStandings.getScorersList());
                    }
                });
    }
}
