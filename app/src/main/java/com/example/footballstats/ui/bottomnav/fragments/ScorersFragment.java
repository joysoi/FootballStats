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
import com.example.footballstats.models.Scorers;
import com.example.footballstats.util.Constants;
import com.example.footballstats.util.Resource;
import com.example.footballstats.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ScorersFragment extends DaggerFragment {

    private static final String TAG = "ScorersFragment";
    private ScorersFragmentViewModel scorersFragmentViewModel;
    private RecyclerView recyclerView;
    private ScorersAdapter scorersAdapter;
    private Competitions competitions;

    @Inject
    ViewModelProviderFactory providerFactory;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scorers, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewScorers);
        initRecyclerView();
        if (getArguments() != null) {
            competitions = getArguments().getParcelable(Constants.COMPETITIONS_INTENT);
        }
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        scorersFragmentViewModel = ViewModelProviders.of(this, providerFactory)
                .get(ScorersFragmentViewModel.class);
        if (competitions != null) {
            subscribeObservers(competitions.getCompetitionId());
        }
    }

    private void initRecyclerView() {
        scorersAdapter = new ScorersAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(scorersAdapter);
    }

    private void subscribeObservers(int compId) {
        scorersFragmentViewModel.observeScorersFromViewModel(compId)
                .removeObservers(getViewLifecycleOwner());
        scorersFragmentViewModel.observeScorersFromViewModel(compId)
                .observe(getViewLifecycleOwner(), new Observer<Resource<List<Scorers>>>() {
                    @Override
                    public void onChanged(Resource<List<Scorers>> scorersList) {
                        if (scorersList != null) {

                            if (scorersList.data != null) {
                                switch (scorersList.status) {
                                    case LOADING: {
                                        Log.d(TAG, "onChanged: Status LOADING");
                                        scorersAdapter.displayLoading();
                                        break;
                                    }
                                    case ERROR: {
                                        Log.e(TAG, "onChanged: cannot refresh the cache.");
                                        Log.e(TAG, "onChanged: ERROR message: " + scorersList.message);
                                        Log.e(TAG, "onChanged: status: ERROR, #Scorers: " + scorersList.data.size());
                                        Toast.makeText(getActivity(), scorersList.message, Toast.LENGTH_SHORT).show();
                                        scorersAdapter.setScorersList(scorersList.data);
                                        break;
                                    }
                                    case SUCCESS: {
                                        Log.d(TAG, "onChanged: cache has been refreshed.");
                                        Log.d(TAG, "onChanged: status: SUCCESS, #Scorers: " + scorersList.data.size());
                                        scorersAdapter.setScorersList(scorersList.data);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
    }
}
