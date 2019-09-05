package com.example.footballstats.di;

//import com.example.footballstats.di.di_standings.StandingsModule;

import com.example.footballstats.ui.bottomnav.fragments.ScorersFragment;
import com.example.footballstats.ui.bottomnav.fragments.StandingsFragment;
import com.example.footballstats.ui.bottomnav.fragments.StandingsFragmentViewModel;
import com.example.footballstats.ui.standings.StandingsListActivity;
import com.example.footballstats.ui.bottomnav.BottomNavActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract StandingsListActivity contributeStandingListActivity();

    @ContributesAndroidInjector
    abstract BottomNavActivity contributeBottomNavActivity();

    @ContributesAndroidInjector
    abstract StandingsFragment contributeStandingsFragment();

    @ContributesAndroidInjector
    abstract ScorersFragment contributeScorersFragment();

}
