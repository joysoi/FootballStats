package com.example.footballstats.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.footballstats.ui.bottomnav.fragments.ScorersFragmentViewModel;
import com.example.footballstats.ui.bottomnav.fragments.StandingsFragmentViewModel;
import com.example.footballstats.ui.standings.StandingsListViewModel;
import com.example.footballstats.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

    @Binds
    @IntoMap
    @ViewModelKey(StandingsListViewModel.class)
    public abstract ViewModel bindStandingsListViewModel (StandingsListViewModel standingsListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(StandingsFragmentViewModel.class)
    public abstract ViewModel bindStandingsFragmentViewModel (StandingsFragmentViewModel standingsFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ScorersFragmentViewModel.class)
    public abstract ViewModel bindScorersFragmentViewModel (ScorersFragmentViewModel scorersFragmentViewModel);
}
