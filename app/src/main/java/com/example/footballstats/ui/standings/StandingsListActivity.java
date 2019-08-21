package com.example.footballstats.ui.standings;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.footballstats.R;
import com.example.footballstats.adapters.CompetitionsAdapter;
import com.example.footballstats.models.Competitions;
import com.example.footballstats.ui.bottomnav.BottomNavActivity;
import com.example.footballstats.util.Constants;
import com.example.footballstats.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class StandingsListActivity extends DaggerAppCompatActivity {

    private static final String TAG = "StandingsListActivity";

    private StandingsListViewModel standingsListViewModel;
    private RecyclerView recyclerView;
    private CompetitionsAdapter competitionsAdapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standing_list);
        recyclerView = findViewById(R.id.competitionsList);
        standingsListViewModel = ViewModelProviders.of(this, providerFactory).get(StandingsListViewModel.class);
        initRecyclerView();
        navigateToCompetition();
        subscribeObservers();
    }

    private void initRecyclerView() {
        competitionsAdapter = new CompetitionsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(competitionsAdapter);
    }


    private void subscribeObservers() {
        standingsListViewModel.getFeed().observe(this, new Observer<List<Competitions>>() {
            @Override
            public void onChanged(List<Competitions> listResource) {
                if (listResource != null) {
                    competitionsAdapter.submitList(listResource);
                }
            }
        });
    }

    private void navigateToCompetition() {
        competitionsAdapter.setOnCompetitionsItemClickListener(new CompetitionsAdapter.OnClickCompetitionListener() {
            @Override
            public void competitionListClick(Competitions competitions) {
                Intent intent = new Intent(StandingsListActivity.this, BottomNavActivity.class);
                intent.putExtra(Constants.COMPETITIONS_INTENT, competitions);
                Log.d(TAG, "competitionListClick: ID:" + competitions.getCompetitionId());
                startActivity(intent);
            }
        });
    }
}
