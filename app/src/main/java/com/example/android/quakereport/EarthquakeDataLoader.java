package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by jiwanpokharel89 on 9/17/2017.
 */

public class EarthquakeDataLoader extends AsyncTaskLoader<List<EarthquakeData>> {

    private String url;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    public EarthquakeDataLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<EarthquakeData> loadInBackground() {
        if(this.url == null)
            return null;
        return QueryUtils.fetchEarthquakeData(url); //Fetches data and returns.
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
