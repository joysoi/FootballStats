package com.example.footballstats.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;


import com.example.footballstats.models.Competitions;
import com.example.footballstats.models.Scorers;
import com.example.footballstats.models.Standing;
import com.example.footballstats.models.Table;
import com.example.footballstats.persistance.FootballDao;
import com.example.footballstats.requests.FootballDataApi;
import com.example.footballstats.requests.responses.ApiResponse;
import com.example.footballstats.requests.responses.Feed;
import com.example.footballstats.requests.responses.LeagueStandings;
import com.example.footballstats.requests.responses.ScorersStandings;
import com.example.footballstats.util.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class FootballRepo {

    private static final String TAG = "FootballRepo";

    private FootballDao footballDao;
    private FootballDataApi footballDataApi;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public FootballRepo(FootballDao footballDao, FootballDataApi footballDataApi) {
        this.footballDao = footballDao;
        this.footballDataApi = footballDataApi;
    }

    /*
        Competitions
     */

    public LiveData<Resource<List<Competitions>>> observeFeed() {
        return new NetworkBoundResource<List<Competitions>, Feed>() {
            @Override
            protected void saveCallResult(@NonNull Feed item) {
                if (item.getCompetitionsList() != null) {
                    final List<Competitions> newCompList = new ArrayList<>();
                    List<String> availableCompetitionsIds = Arrays.asList("2021", "2019", "2002", "2003", "2017", "2015");
                    for (Competitions compItems : item.getCompetitionsList()) {
                        String compId = String.valueOf(compItems.getCompetitionId());
                        if (availableCompetitionsIds.contains(compId)) {
                            newCompList.add(compItems);
                            insertCompetitions(newCompList);
                        }
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Competitions> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Competitions>> loadFromDb() {
                return footballDao.getAllCompetitions();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Feed>> createCall() {
                return footballDataApi.getMainResponse();
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
                            int index = 0;
                            for (long rowId : footballDao.insertCompetitions((newCompList.toArray(competitions)))) {
                                if (rowId == -1) {
                                    Log.d(TAG, "saveCallResult: CONFLICT... This recipe is already in the cache");
                                    updateCompetitions(
                                            competitions[index].getCompetitionId(),
                                            competitions[index].getCompetitionName()
                                    );
                                }
                                index++;
                            }
                            emitter.onNext(competitions);
                            emitter.onComplete();
                        }

                    }
                }).subscribeOn(Schedulers.io());

        observableInsert.subscribe(new Observer<Competitions[]>() {
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

        //return value for testing purposes
        return observableInsert;
    }

    private int updateCompetitions(final int competitionId, final String competitionName) {

        Observable<Competitions> updateCompetitions = Observable
                .create(new ObservableOnSubscribe<Competitions>() {
                    @Override
                    public void subscribe(ObservableEmitter<Competitions> emitter) throws Exception {
                        if (!emitter.isDisposed()) {
                            footballDao.updateCompetitions(
                                    competitionId,
                                    competitionName
                            );
                        }
                    }
                }).subscribeOn(Schedulers.io());

        updateCompetitions.subscribe(new Observer<Competitions>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
                Log.d(TAG, "onSubscribe: updateCompetitions: called");
            }

            @Override
            public void onNext(Competitions competitions) {
                Log.d(TAG, "onNext: updateCompetitions: " + competitions);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: updateCompetitions", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: updateCompetitions called");
            }
        });

        //return value for testing purposes
        return competitionId;
    }


    /*
        League Standings
     */

    public LiveData<Resource<List<Table>>> observeLeagueStandings(final int id) {
        return new NetworkBoundResource<List<Table>, LeagueStandings>() {
            @Override
            protected void saveCallResult(@NonNull LeagueStandings item) {
                if (item.getStandings() != null) {
                    for (Standing standing : item.getStandings()) {
                        if (standing.getType().equals("TOTAL")) {
                            Log.d(TAG, "saveCallResult: TABLE LIST: " + standing.getTableList());
                            insertTableStandings(standing.getTableList());
                        }
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Table> data) {
                Log.d(TAG, "shouldFetch: FETCH TABLE LIST: " + true);
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Table>> loadFromDb() {
                return footballDao.getAllTableData();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<LeagueStandings>> createCall() {
                return footballDataApi.getLeagueStandingsResponse(String.valueOf(id));
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
                }).subscribeOn(Schedulers.io());

        observableInsert.subscribe(new Observer<Table[]>() {
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
                .subscribeOn(Schedulers.io());

        observableUpdate.subscribe(new Observer<Table>() {
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


    /*
        Scorers
     */

    public LiveData<Resource<List<Scorers>>> observeScorers(final int id) {
        return new NetworkBoundResource<List<Scorers>, ScorersStandings>() {
            @Override
            protected void saveCallResult(@NonNull ScorersStandings item) {
                if (item.getScorersList() != null) {
                    Log.d(TAG, "saveCallResult: Scorers list: " + item.getScorersList());
                    insertScorersList(item.getScorersList());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Scorers> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Scorers>> loadFromDb() {
                return footballDao.getAllScorers();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ScorersStandings>> createCall() {
                return footballDataApi.getScorersResponse(String.valueOf(id));
            }
        }.getAsLiveData();
    }

    private void insertScorersList(final List<Scorers> scorersList) {
        final Scorers[] scorers = new Scorers[scorersList.size()];
        Observable<Scorers[]> observableInsert = Observable
                .create(new ObservableOnSubscribe<Scorers[]>() {
                    @Override
                    public void subscribe(ObservableEmitter<Scorers[]> emitter) throws Exception {
                        if (!emitter.isDisposed()) {
                            int index = 0;
                            for (long rowId : footballDao.insertScorers((scorersList.toArray(scorers)))) {
                                if (rowId == -1) {
                                    updateScorersTable(
                                            scorers[index].getNumberOfGoals(),
                                            scorers[index].getTeam().getName(),
                                            scorers[index].getPlayer().getPlayerName()
                                    );
                                }
                                index++;
                            }
                            emitter.onNext(scorers);
                            emitter.onComplete();
                        }
                    }
                })
                .subscribeOn(Schedulers.io());

        observableInsert.subscribe(new Observer<Scorers[]>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
                Log.d(TAG, "onSubscribe: insertScorersList called");
            }

            @Override
            public void onNext(Scorers[] scorers) {
                Log.d(TAG, "onNext: insertScorersList" + Arrays.toString(scorers));
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: insertScorersList", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: insertScorersList");
            }
        });
    }

    private int updateScorersTable(final int numberOfGoals, final String teamName, final String playerName) {
        Observable<Scorers> observableUpdate = Observable
                .create(new ObservableOnSubscribe<Scorers>() {
                    @Override
                    public void subscribe(ObservableEmitter<Scorers> emitter) throws Exception {
                        if (!emitter.isDisposed()) {
                            footballDao.updateScorers(
                                    numberOfGoals,
                                    teamName,
                                    playerName
                            );
                        }
                    }
                })
                .subscribeOn(Schedulers.io());

        observableUpdate.subscribe(new Observer<Scorers>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
                Log.d(TAG, "onSubscribe: updateScorersTable called...");
            }

            @Override
            public void onNext(Scorers scorers) {
                Log.d(TAG, "onNext: updateScorersTable: " + scorers);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: updateScorersTable", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: updateScorersTable");
            }
        });

        //returned value for testing purposes only
        return numberOfGoals;
    }


    public void unsubscribeObservables() {
        if (disposable != null) {
            disposable.clear();
        }
    }
}
