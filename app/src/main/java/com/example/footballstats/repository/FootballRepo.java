package com.example.footballstats.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;


import com.example.footballstats.models.Competitions;
import com.example.footballstats.models.Standing;
import com.example.footballstats.models.Table;
import com.example.footballstats.persistance.FootballDao;
import com.example.footballstats.requests.FootballDataApi;
import com.example.footballstats.requests.responses.Feed;
import com.example.footballstats.requests.responses.LeagueStandings;
import com.example.footballstats.util.Constants;
import com.example.footballstats.util.NetworkBoundResource;

import java.lang.reflect.Array;
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

    //feed response
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
                        Log.d(TAG, "saveCallResult: ID: " + compId);
                        if (availableCompetitionsIds.contains(compId)) {
                            compItems.setTimestamp((int) System.currentTimeMillis() / 1000); // in seconds
                            newCompList.add(compItems);
                            insertCompetitions(newCompList);
                        }
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Competitions> data) {
                if (data != null) {
                    int currentTime = (int) System.currentTimeMillis() / 1000; // time in seconds
                    for (Competitions competitions : data) {
                        Log.d(TAG, "currentTime: " + currentTime);
                        int lastRefresh = competitions.getTimestamp() / 1000;
                        Log.d(TAG, "lastRefresh: " + lastRefresh);
                        Log.d(TAG, "shouldFetch: it's been " + ((currentTime - lastRefresh) / 60 / 60 / 24) +
                                " seconds since last update");
                        if ((currentTime - lastRefresh) >= Constants.REFRESH_TIME) {
                            return true;
                        }
                    }
                    Log.d(TAG, "shouldFetch: Should REFRESH List: " + true);
                    return true;
                }
                Log.d(TAG, "shouldFetch: Should NOT REFRESH List " + false);
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

    private Observable<Competitions[]> insertCompetitions(final List<Competitions> newCompList) {
        final Competitions[] competitions = new Competitions[newCompList.size()];
        Observable<Competitions[]> observableInsert = Observable
                .create(new ObservableOnSubscribe<Competitions[]>() {
                    @Override
                    public void subscribe(ObservableEmitter<Competitions[]> emitter) throws Exception {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(competitions);
                        }
                        if (!emitter.isDisposed()) {
                            footballDao.insertCompetitions((newCompList).toArray(competitions));
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

    public LiveData<List<Table>> observeLeagueStandings(final int id) {
        Log.d(TAG, "observeLeagueStandings: ID: " + id);
        return new NetworkBoundResource<List<Table>, LeagueStandings>() {
            @Override
            protected void saveCallResult(@NonNull LeagueStandings item) {
                List<Table> tableList = new ArrayList<>();
                int competitionId = item.getCompetition().getCompetitionId();
                Log.d(TAG, "saveCallResult: final int id: " + id);
                Log.d(TAG, "saveCallResult: League standings id: " + competitionId);
                if (item.getStandings() != null) {
                    for (Standing standing : item.getStandings()) {
                        if (standing.getType().equals("TOTAL")) {
                            standing.setTimestamp((int) System.currentTimeMillis() / 1000);
                            tableList.addAll(standing.getTableList());
                            insertTableStandings(tableList);
                            Log.d(TAG, "saveCallResult: TABLE LIST: " + tableList);
                        }
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Table> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Table>> loadFromDb() {
                return footballDao.getAllTableData();
            }

            @NonNull
            @Override
            protected LiveData<LeagueStandings> createCall() {
                Log.d(TAG, "observeLeagueStandings: ID: " + id);
                return LiveDataReactiveStreams
                        .fromPublisher(footballDataApi.getLeagueStandingsResponse(String.valueOf(id))
                                .subscribeOn(Schedulers.io()));
            }
        }.getAsLiveData();
    }


    private void insertTableStandings(final List<Table> tableList) {
        final Table[] tables = new Table[tableList.size()];
        Observable<Table[]> observableInsert = Observable
                .create(new ObservableOnSubscribe<Table[]>() {
                    @Override
                    public void subscribe(ObservableEmitter<Table[]> emitter) throws Exception {
                        if (!emitter.isDisposed()) {
                            int index = 0;
                            for (long rowId : footballDao.insertTableData((tableList.toArray(tables)))) {
                                if (rowId == -1) {
                                    updateTableStandings(
                                            tables[index].getPosition(),
                                            tables[index].getTeam().getName(),
                                            tables[index].getPoints()
                                    );
                                }
                                index++;
                            }
                            emitter.onNext(tables);
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observableInsert.subscribe(new io.reactivex.Observer<Table[]>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: insertTableStandings called...");
                disposable.add(d);
            }

            @Override
            public void onNext(Table[] table) {
                Log.d(TAG, "onNext: insertTableStandings: " +
                        Arrays.toString(table));
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: insertTableStandings", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: insertTableStandings called...");
            }
        });

    }


    private int updateTableStandings(final int position, final String teamName, final int points) {
        Observable<Table> observableUpdate = Observable
                .create(new ObservableOnSubscribe<Table>() {
                    @Override
                    public void subscribe(ObservableEmitter<Table> emitter) throws Exception {
                        if (!emitter.isDisposed()) {
                            footballDao.updateTableData(
                                    position,
                                    teamName,
                                    points
                            );
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observableUpdate.subscribe(new io.reactivex.Observer<Table>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: updateTableStandings called");
                disposable.add(d);
            }

            @Override
            public void onNext(Table table) {
                Log.d(TAG, "onNext: team UPDATED " + table.getTeam());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: updateTableStandings: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: updateTableStandings: called...");
            }
        });

        // int returned for testing purposes only
        return points;
    }

    public void unsubscribeObservables() {
        if (disposable != null) {
            disposable.clear();
        }
    }
}
