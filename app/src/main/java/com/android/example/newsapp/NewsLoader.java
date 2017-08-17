package com.android.example.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by root on 17/8/17.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mUri;

    public NewsLoader(Context context, String uri) {
        super(context);
        mUri = uri;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUri == null) {
            return null;
        }
        List<News> news = fetchUtils.fetchNewsData(mUri);
        return news;
    }
}
