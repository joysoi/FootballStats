package com.example.footballstats.di;

import android.app.Application;

import com.example.footballstats.BaseApplication;
import com.example.footballstats.persistance.FootballDao;
import com.example.footballstats.persistance.FootballDataBase;
import com.example.footballstats.repository.FootballRepo;
import com.example.footballstats.ui.standings.StandingsListActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuildersModule.class, ViewModelFactoryModule.class})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
