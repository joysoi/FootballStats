package com.example.footballstats.di;

import com.example.footballstats.di.di_standings.StandingsModule;
import com.example.footballstats.di.di_standings.di_fragment.BottomNavFragmentModule;
import com.example.footballstats.ui.bottomnav.fragments.StandingsFragmentViewModel;
import com.example.footballstats.ui.standings.StandingsListActivity;
import com.example.footballstats.ui.bottomnav.BottomNavActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {ViewModelFactoryModule.class, StandingsModule.class})
    abstract StandingsListActivity contributeStandingListActivity();

    @ContributesAndroidInjector(modules = {BottomNavFragmentModule.class, ViewModelFactoryModule.class})
    abstract BottomNavActivity contributeBottomNavActivity();


}
