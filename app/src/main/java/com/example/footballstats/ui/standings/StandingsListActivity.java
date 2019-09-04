package com.example.footballstats.ui.standings;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.footballstats.R;
import com.example.footballstats.adapters.CompetitionsAdapter;
import com.example.footballstats.adapters.OnCompetitionsListener;
import com.example.footballstats.models.Competitions;
import com.example.footballstats.ui.bottomnav.BottomNavActivity;
import com.example.footballstats.util.Constants;
import com.example.footballstats.util.Resource;
import com.example.footballstats.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class StandingsListActivity extends DaggerAppCompatActivity implements OnCompetitionsListener {

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
        subscribeObservers();
    }

    private void initRecyclerView() {
        competitionsAdapter = new CompetitionsAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(competitionsAdapter);
    }


    private void subscribeObservers() {
        standingsListViewModel.getFeed().observe(this, new Observer<Resource<List<Competitions>>>() {
            @Override
            public void onChanged(Resource<List<Competitions>> listResource) {
                if (listResource != null) {
                    Log.d(TAG, "onChanged: status: " + listResource.status);
                    if (listResource.data != null) {
                        switch (listResource.status) {
                            case LOADING: {
                                Log.d(TAG, "onChanged: Status LOADING");
                                competitionsAdapter.displayLoading();
                                break;
                            }
                            case ERROR: {
                                Log.e(TAG, "onChanged: cannot refresh the cache.");
                                Log.e(TAG, "onChanged: ERROR message: " + listResource.message);
                                Log.e(TAG, "onChanged: status: ERROR, #Competitions: " + listResource.data.size());
                                Log.e(TAG, "onChanged: status: ERROR, #Competitions: " + listResource.data);
                                Toast.makeText(StandingsListActivity.this, listResource.message, Toast.LENGTH_SHORT).show();
                                competitionsAdapter.setCompetitions(listResource.data);
                                break;
                            }
                            case SUCCESS: {
                                Log.d(TAG, "onChanged: cache has been refreshed.");
                                Log.d(TAG, "onChanged: status: SUCCESS, #Competitions: " + listResource.data.size());
                                Log.d(TAG, "onChanged: status: SUCCESS, #Competitions: " + listResource.data);
                                competitionsAdapter.setCompetitions(listResource.data);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onCompetitionsClick(int position) {
        Intent intent = new Intent(StandingsListActivity.this, BottomNavActivity.class);
        intent.putExtra(Constants.COMPETITIONS_INTENT, competitionsAdapter.getSelectedCompetition(position));
        startActivity(intent);
    }

}
