package com.android.example.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.android.example.newsapp.MainActivity.LOG_TAG;

/**
 * Created by root on 17/8/17.
 */

public class fetchUtils {

    private fetchUtils() {
    }

    public static ArrayList<News> fetchNewsData(String request_url) {


        URL url = createUrl(request_url);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String jsonResponse = "";
        jsonResponse = makeHttpRequest(url);
        ArrayList<News> news = jsonExtracter(jsonResponse);
        return news;

    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (url != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }


    public static ArrayList<News> jsonExtracter(String json) {
        ArrayList<News> newses = new ArrayList<News>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray newsArray = jsonObject.getJSONArray("articles");
            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentNews = newsArray.getJSONObject(i);
                String author = currentNews.getString("author");
                String title = currentNews.getString("title");
                String story = currentNews.getString("description");
                String timeAnddate = currentNews.getString("publishedAt");
                if (timeAnddate != "null") {
                    News news = new News(title, "Date: " + timeAnddate.substring(0, 10), "Time: " + timeAnddate.substring(11, 19), story, author);
                    newses.add(news);
                } else {
                    News news = new News(title, "Date: " + "No Data", "Time: " + "No Data", story, author);
                    newses.add(news);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newses;
    }
}
