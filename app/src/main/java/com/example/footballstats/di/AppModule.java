package com.example.footballstats.di;

import android.app.Application;

import androidx.room.Room;

import com.example.footballstats.persistance.FootballDao;
import com.example.footballstats.persistance.FootballDataBase;
import com.example.footballstats.repository.FootballRepo;
import com.example.footballstats.requests.FeedApiClient;
import com.example.footballstats.requests.FootballDataApi;
import com.example.footballstats.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static FootballDataBase provideDatabase(Application application) {
        return Room.databaseBuilder(
                application,
                FootballDataBase.class,
                Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    static FootballDao provideFootballDao(FootballDataBase footballDataBase) {
        return footballDataBase.getFootballDao();
    }

    @Singleton
    @Provides
    static FootballDataApi providesFootballDataApi(Retrofit retrofit) {
        return retrofit.create(FootballDataApi.class);
    }

    @Singleton
    @Provides
    static FeedApiClient provideFeedApiClient(FootballDataApi footballDataApi) {
        return new FeedApiClient(footballDataApi);
    }

    @Singleton
    @Provides
    static FootballRepo provideFootballRepo(FootballDao footballDao, FootballDataApi footballDataApi) {
        return new FootballRepo(footballDao, footballDataApi);
    }


//    @Singleton
//    @Provides
//    static FootballRepo provideFootballRepo(FeedApiClient feedApiClient){
//        return new FootballRepo(feedApiClient);
//    }

}
