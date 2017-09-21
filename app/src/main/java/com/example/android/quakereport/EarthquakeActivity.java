/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthquakeData>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    EarthquakeDataAdapter earthquakeDataAdapter;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        earthquakeDataAdapter= new EarthquakeDataAdapter(this,new ArrayList<EarthquakeData>());

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(earthquakeDataAdapter);
        emptyTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(emptyTextView);
        Log.i(LOG_TAG,"***********************onCreate()*********************************");
        getLoaderManager().initLoader(1,null,this);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //extract corresponding URL for the list element and an implicit intent to open in a browser.
                String url = ((EarthquakeData) earthquakeDataAdapter.getItem(i)).getUrl();
               // String url = earthquakeDataArrayList.get(i).getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

    }



    @Override
    public Loader<List<EarthquakeData>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG,"***********************onCreate()*********************************");
        return new EarthquakeDataLoader(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<EarthquakeData>> loader, List<EarthquakeData> earthquakeDatas) {
        //Set empty view text
        emptyTextView.setText(R.string.empty_text_view_text);
        earthquakeDataAdapter.clear();
        Log.i(LOG_TAG,"***********************onLoadFinished()*********************************");
        if(earthquakeDatas !=null && !earthquakeDatas.isEmpty())
            earthquakeDataAdapter.addAll(earthquakeDatas);
    }

    @Override
    public void onLoaderReset(Loader<List<EarthquakeData>> loader) {
        Log.i(LOG_TAG,"***********************onLoaderReset()*********************************");
        earthquakeDataAdapter.clear();
    }


}
