package com.android.example.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private NewsAdapter newsAdapter;
    private static final int NEWS_LOADER_ID = 1;
    private ImageView noInternet;
    private ImageView loadingimage;

    private static final String NEWS_URL = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=f2c4e9763afd41b1aa28511618c81969";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noInternet = (ImageView) findViewById(R.id.noInternetImage);
        loadingimage = (ImageView) findViewById(R.id.loadingImage);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            displayInfo();

        } else {
            View locationIndicator = (View) findViewById(R.id.loading_indicator);
            locationIndicator.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        displayInfo();
        super.onStart();
    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(MainActivity.this, NEWS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        loadingimage.setVisibility(View.GONE);

        if (newsAdapter != null) {
            newsAdapter.clear();
        }
        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();

    }

    public void displayInfo() {
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(newsAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);
    }
}
