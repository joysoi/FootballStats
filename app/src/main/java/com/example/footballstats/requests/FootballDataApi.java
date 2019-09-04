package com.example.footballstats.requests;

import androidx.lifecycle.LiveData;

import com.example.footballstats.requests.responses.ApiResponse;
import com.example.footballstats.requests.responses.Feed;
import com.example.footballstats.requests.responses.LeagueStandings;
import com.example.footballstats.requests.responses.ScorersStandings;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface FootballDataApi {

    @Headers("X-Auth-Token:a093861bf0cb4946b5c1cab8a83471e4")
    @GET("/v2/competitions")
    LiveData<ApiResponse<Feed>> getMainResponse();

    @Headers("X-Auth-Token:a093861bf0cb4946b5c1cab8a83471e4")
    @GET("/v2/competitions/{id}/standings")
    LiveData<ApiResponse<LeagueStandings>> getLeagueStandingsResponse(@Path("id") String id);

    @Headers("X-Auth-Token:a093861bf0cb4946b5c1cab8a83471e4")
    @GET("/v2/competitions/{id}/scorers")
    LiveData<ApiResponse<ScorersStandings>> getScorersResponse(@Path("id") String id);
}
