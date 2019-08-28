package com.example.footballstats.util;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

// CacheObject: Type for the Resource data. (database cache)
// RequestObject: Type for the API response. (network request)
public abstract class NetworkBoundResource<CacheObject, RequestObject> {

    private static final String TAG = "NetworkBoundResource";
    private MediatorLiveData<CacheObject> result = new MediatorLiveData<>();

    protected NetworkBoundResource() {
        init();
    }


    private void init() {
        // Observe LiveData source from local db
        final LiveData<CacheObject> dbSource = loadFromDb();
        Log.d(TAG, "init: DB: " + dbSource.getValue());
        Log.d(TAG, "init: DB: " + loadFromDb().getValue());

        result.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(@Nullable CacheObject CacheObject) {
                Log.d(TAG, "onChanged: CACHE OBJECT: " + CacheObject);
                // Remove observer from local db. Need to decide if read local db or network
                result.removeSource(dbSource);

                // get data from network if conditions in shouldFetch(boolean) are true
                if (shouldFetch(CacheObject)) {
                    Log.d(TAG, "onChanged: CACHE OBJECT: " + CacheObject);
                    // get data from network
                    Log.d(TAG, "onChanged: SHOULD FETCH: " + true);
                    fetchFromNetwork(dbSource);
                    Log.d(TAG, "onChanged: FETCH FROM NETWORK: " + dbSource.getValue());
                } else { // Otherwise read data from local db
                    Log.d(TAG, "onChanged: SHOULD FETCH: " + false);
                    Log.d(TAG, "onChanged: DB SOURCE: " + dbSource.getValue());
                    result.addSource(dbSource, new Observer<CacheObject>() {
                        @Override
                        public void onChanged(@Nullable CacheObject CacheObject) {
                            setValue(CacheObject);
                            Log.d(TAG, "onChanged: SET VALUE: " + CacheObject);
                        }
                    });
                }
            }
        });
    }


    /**
     * 1) observe local db
     * 2) if <condition/> query the network
     * 3) stop observing the local db
     * 4) insert new data into local db
     * 5) begin observing local db again to see the refreshed data from network
     *
     * @param dbSource
     */


    private void fetchFromNetwork(final LiveData<CacheObject> dbSource) {
        Log.d(TAG, "fetchFromNetwork: called.");

        result.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(@Nullable CacheObject CacheObject) {
                setValue(CacheObject);
            }
        });


        final LiveData<RequestObject> apiResponse = createCall();

        result.addSource(apiResponse, new Observer<RequestObject>() {
            @Override
            public void onChanged(@Nullable final RequestObject RequestObjectApiResponse) {
                result.removeSource(dbSource);
                result.removeSource(apiResponse);

                // save response to local db
                if (RequestObjectApiResponse != null) {
                    saveCallResult(RequestObjectApiResponse);
                    Log.d(TAG, "onChanged: RequestObjectApiResponse: " + RequestObjectApiResponse);
                }

                // observe local db again since new result from network will have been saved
                result.addSource(loadFromDb(), new Observer<CacheObject>() {
                    @Override
                    public void onChanged(@Nullable CacheObject CacheObject) {
                        Log.d(TAG, "onChanged: LoadFromDB: " + loadFromDb().getValue());
                        Log.d(TAG, "onChanged: CACHE OBJECT: " + CacheObject);
                        setValue(CacheObject);
                    }
                });

            }
        });
    }


    private void setValue(CacheObject newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestObject item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable CacheObject data);

    // Called to get the cached data from the database.
    @NonNull
    @MainThread
    protected abstract LiveData<CacheObject> loadFromDb();

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<RequestObject> createCall();

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public final LiveData<CacheObject> getAsLiveData() {
        Log.d(TAG, "getAsLiveData: RESULT: " + result.getValue());
        return result;
    }
}
