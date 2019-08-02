package com.example.footballstats.di.di_standings.di_fragment;

import com.example.footballstats.ui.bottomnav.fragments.ScorersFragment;
import com.example.footballstats.ui.bottomnav.fragments.StandingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BottomNavFragmentModule {

    @ContributesAndroidInjector
    abstract StandingsFragment contributeStandingsFragment();

    @ContributesAndroidInjector
    abstract ScorersFragment contributeScorersFragment();
}
