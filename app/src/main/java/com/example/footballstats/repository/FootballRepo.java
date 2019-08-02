package com.example.footballstats.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;


import com.example.footballstats.models.Competitions;
import com.example.footballstats.persistance.FootballDao;
import com.example.footballstats.requests.FootballDataApi;
import com.example.footballstats.requests.responses.Feed;
import com.example.footballstats.util.Constants;
import com.example.footballstats.util.NetworkBoundResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class FootballRepo {

    private static final String TAG = "FootballRepo";

    private FootballDao footballDao;
    private FootballDataApi footballDataApi;
    private CompositeDisposable disposable = new CompositeDisposable();

    //feed
    private MediatorLiveData<Feed> feedMediatorLiveData = new MediatorLiveData<>();

    @Inject
    public FootballRepo(FootballDao footballDao, FootballDataApi footballDataApi) {
        this.footballDao = footballDao;
        this.footballDataApi = footballDataApi;
    }

    public LiveData<List<Competitions>> observeFeed() {
        return new NetworkBoundResource<List<Competitions>, Feed>() {
            @Override
            protected void saveCallResult(@NonNull Feed item) {
                if (item.getCompetitionsList() != null) {
                    final List<Competitions> newCompList = new ArrayList<>();
                    List<String> availableCompetitionsIds = Arrays.asList("2021", "2019", "2002", "2003", "2017", "2015");
                    for (Competitions compItems : item.getCompetitionsList()) {
                        String compId = String.valueOf(compItems.getCompetitionId());
                        if (availableCompetitionsIds.contains(compId)) {
                            compItems.setTimestamp((int) System.currentTimeMillis() / 1000); // in seconds
                            newCompList.add(compItems);
                        }
                    }
                    Log.d(TAG, "saveCallResult: newCompList: " + newCompList.size());
                    insertCompetitions(newCompList);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Competitions> data) {
                if (data != null) {
                    int currentTime = (int) System.currentTimeMillis() / 1000; // time in seconds
                    for (Competitions competitions : data){
                        Log.d(TAG, "currentTime: " + currentTime);
                        int lastRefresh = competitions.getTimestamp();
                        Log.d(TAG, "lastRefresh: " + lastRefresh);
                        Log.d(TAG, "shouldFetch: it's been " + ((currentTime - lastRefresh) / 60 / 60 / 24));
                        if((currentTime - lastRefresh) >= Constants.REFRESH_TIME){
                            return true;
                        }
                    }
                    Log.d(TAG, "shouldFetch: Should REFRESH List: " + true);
                    return true;
                }
                Log.d(TAG, "shouldFetch: Should REFRESH List " + false);
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<Competitions>> loadFromDb() {
                return footballDao.getAllCompetitions();
            }

            @NonNull
            @Override
            protected LiveData<Feed> createCall() {
                final LiveData<Feed> source = LiveDataReactiveStreams
                        .fromPublisher(footballDataApi.getMainResponse()
                                .subscribeOn(Schedulers.io()));

                feedMediatorLiveData.addSource(source, new Observer<Feed>() {
                    @Override
                    public void onChanged(Feed feed) {
                        feedMediatorLiveData.setValue(feed);
                        feedMediatorLiveData.removeSource(source);
                    }
                });
                return feedMediatorLiveData;
            }
        }.getAsLiveData();
    }

    public Observable<Competitions[]> insertCompetitions(final List<Competitions> newCompList) {
        final Competitions[] competitions = new Competitions[newCompList.size()];

        Observable<Competitions[]> observableInsert = Observable
                .create(new ObservableOnSubscribe<Competitions[]>() {
                    @Override
                    public void subscribe(ObservableEmitter<Competitions[]> emitter) throws Exception {
                        Log.d(TAG, "subscribe insertOrUpdateCompetitions : called...");
                        if (!emitter.isDisposed()) {
                            emitter.onNext(competitions);

                        }
                        if (!emitter.isDisposed()) {
                            footballDao.insertCompetitions((newCompList.toArray(competitions)));
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observableInsert.subscribe(new io.reactivex.Observer<Competitions[]>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe observableInsert: called...");
                disposable.add(d);
            }

            @Override
            public void onNext(Competitions[] competitions) {
                Log.d(TAG, "onNext: competitions: " + Arrays.toString(competitions));
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete observableInsert: called...");
            }
        });

        return observableInsert;
    }


//    public Observable<Competitions[]> insertOrUpdateCompetitions(final List<Competitions> newCompList) {
//        final Competitions[] competitions = new Competitions[newCompList.size()];
//
//        Observable<Competitions[]> observableInsert = Observable
//                .create(new ObservableOnSubscribe<Competitions[]>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Competitions[]> emitter) throws Exception {
//                        Log.d(TAG, "subscribe insertOrUpdateUser : called...");
//                        if (!emitter.isDisposed()) {
//                            int index = 0;
//                            for (long rowId : footballDao.insertCompetitions((newCompList.toArray(competitions)))) {
//                                if (rowId == -1) {
//                                    updateCompetitions(
//                                            competitions[index].getCompetitionId(),
//                                            competitions[index].getCompetitionName()
//                                    );
//                                }
//                                index++;
//                            }
//                            emitter.onNext(competitions);
//                            emitter.onComplete();
//                        }
//
//                    }
//                }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//        observableInsert.subscribe(new io.reactivex.Observer<Competitions[]>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "onSubscribe observableInsert: called...");
//                disposable.add(d);
//            }
//
//            @Override
//            public void onNext(Competitions[] competitions) {
//                Log.d(TAG, "onNext: competitions: " + Arrays.toString(competitions));
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "onError: ", e);
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete observableInsert: called...");
//            }
//        });
//
//        return observableInsert;
//    }

//    public int updateCompetitions(final int competitionId, final String competitionName) {
//        Observable<Competitions> observableUpdate = Observable
//                .create(new ObservableOnSubscribe<Competitions>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Competitions> emitter) throws Exception {
//                        if (!emitter.isDisposed()) {
//                            footballDao.updateCompetitions(competitionId,
//                                    competitionName);
//                            emitter.onComplete();
//                        }
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//
//        observableUpdate.subscribe(new io.reactivex.Observer<Competitions>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "onSubscribe observableUpdate: called...");
//                disposable.add(d);
//            }
//
//            @Override
//            public void onNext(Competitions competitions) {
//                Log.d(TAG, "onNext: competitions updated: " + competitions.getCompetitionName());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "onError: ", e);
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete observableUpdate: called...");
//            }
//        });
//
//        // int returned for testing purposes only
//        return competitionId;
//    }

    public void unsubscribeObservables() {
        if (disposable != null) {
            disposable.clear();
        }
    }
}
