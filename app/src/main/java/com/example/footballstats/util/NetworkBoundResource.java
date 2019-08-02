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
    private MediatorLiveData<CacheObject> results = new MediatorLiveData<>();


    protected NetworkBoundResource() {
        init();
    }

    private void init() {

        //observe LiveData source from DB
        final LiveData<CacheObject> dbSource = loadFromDb();

        results.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(CacheObject cacheObject) {
                results.removeSource(dbSource);

                if (shouldFetch(cacheObject)) {
                    fetchFromNetwork(dbSource);
                } else {
                    results.addSource(dbSource, new Observer<CacheObject>() {
                        @Override
                        public void onChanged(CacheObject cacheObject) {
                            setValue(cacheObject);
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
        Log.d(TAG, "fetchFromNetwork: called...");

        final LiveData<RequestObject> apiResponse = createCall();

        results.addSource(apiResponse, new Observer<RequestObject>() {
            @Override
            public void onChanged(final RequestObject RequestObjectApiResponse) {
                results.removeSource(dbSource);
                results.removeSource(apiResponse);

                // called on worker thread
                saveCallResult(RequestObjectApiResponse);
                // called on main thread
                results.addSource(loadFromDb(), new Observer<CacheObject>() {
                    @Override
                    public void onChanged(CacheObject cacheObject) {
                        setValue(cacheObject);
                    }
                });
            }
        });
    }

    private void setValue(CacheObject newValue) {
        if (results.getValue() != newValue) {
            results.setValue(newValue);
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
        return results;
    }
}
