package com.android.example.newsapp;

/**
 * Created by root on 17/8/17.
 */

public class News {
    private String mTitle, mDate, mTime, mStory, mAuthor;

    public News(String title, String date, String time, String story, String author) {
        mDate = date;
        mStory = story;
        mTime = time;
        mTitle = title;
        mAuthor = author;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmStory() {
        return mStory;
    }

    public String getmAuthor() {
        return mAuthor;
    }
}
