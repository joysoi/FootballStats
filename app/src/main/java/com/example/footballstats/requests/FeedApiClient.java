package com.example.footballstats.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.footballstats.requests.responses.Feed;
import com.example.footballstats.requests.responses.LeagueStandings;
import com.example.footballstats.requests.responses.ScorersStandings;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.schedulers.Schedulers;

@Singleton
public class FeedApiClient {
    private final FootballDataApi footballDataApi;
    //feed response
    private MediatorLiveData<Feed> feedMediatorLiveData = new MediatorLiveData<>();
    //standings response
    private MediatorLiveData<LeagueStandings> standingsMediatorLiveData = new MediatorLiveData<>();
    //scorers response
    private MediatorLiveData<ScorersStandings> scorersMediatorLiveData = new MediatorLiveData<>();


    @Inject
    public FeedApiClient(FootballDataApi footballDataApi) {
        this.footballDataApi = footballDataApi;
    }

    public void transformFeedToLiveData() {
        final LiveData<Feed> source = LiveDataReactiveStreams.fromPublisher(
                footballDataApi.getMainResponse()
                        .subscribeOn(Schedulers.io())
        );

        feedMediatorLiveData.addSource(source, new Observer<Feed>() {
            @Override
            public void onChanged(Feed feed) {
                feedMediatorLiveData.setValue(feed);
                feedMediatorLiveData.removeSource(source);
            }
        });
    }

    public void transformStandingsToLiveData(String id){
        final LiveData<LeagueStandings> source = LiveDataReactiveStreams.fromPublisher(
                footballDataApi.getLeagueStandingsResponse(id)
                .subscribeOn(Schedulers.io())
        );

        standingsMediatorLiveData.addSource(source, new Observer<LeagueStandings>() {
            @Override
            public void onChanged(LeagueStandings leagueStandings) {
                standingsMediatorLiveData.setValue(leagueStandings);
                standingsMediatorLiveData.removeSource(source);
            }
        });
    }

    public void transformScorersToLiveData(String id){
        final LiveData<ScorersStandings> source = LiveDataReactiveStreams.fromPublisher(
                footballDataApi.getScorersResponse(id)
                        .subscribeOn(Schedulers.io())
        );

        scorersMediatorLiveData.addSource(source, new Observer<ScorersStandings>() {
            @Override
            public void onChanged(ScorersStandings scorersStandings) {
                scorersMediatorLiveData.setValue(scorersStandings);
                scorersMediatorLiveData.removeSource(source);
            }
        });
    }

    public LiveData<Feed> observeFeedFromApiClient() {
        return feedMediatorLiveData;
    }

    public LiveData<LeagueStandings> observeStandingsFromApiClient() {
        return standingsMediatorLiveData;
    }

    public LiveData<ScorersStandings> observeScorersFromApiClient() {
        return scorersMediatorLiveData;
    }
}
