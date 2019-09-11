package com.example.footballstats.ui.bottomnav;

import android.os.Bundle;

import com.example.footballstats.R;
import com.example.footballstats.models.Competitions;

import com.example.footballstats.util.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import dagger.android.support.DaggerAppCompatActivity;

public class BottomNavActivity extends DaggerAppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "BottomNavActivity";
    private BottomNavigationView bottomNavigationView;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        if (getIntent().hasExtra(Constants.COMPETITIONS_INTENT)) {
            Competitions competitions = getIntent().getParcelableExtra(Constants.COMPETITIONS_INTENT);
            bundle = new Bundle();
            bundle.putParcelable(Constants.COMPETITIONS_INTENT, competitions);
            Log.d(TAG, "getIncomingIntent: ID:" + competitions.getCompetitionId());
        }
        bottomNavigationView = findViewById(R.id.nav_view);
        initNavigationController();
    }

    private NavOptions navOptions() {
        return new NavOptions.Builder()
                .setPopUpTo(R.id.main, true)
                .build();
    }

    private boolean isValidDestination() {
        return R.id.navigation_scorers != Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId();
    }

    private void initNavigationController() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.navigation_standings, bundle, navOptions());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_standings:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.navigation_standings, bundle, navOptions());
                break;
            case R.id.navigation_scorers:
                if (isValidDestination())
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.navigation_scorers, bundle);
                break;
        }
        return true;
    }
}
