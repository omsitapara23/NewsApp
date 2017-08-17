package com.android.example.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 17/8/17.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        final News currentNews = getItem(position);
        TextView titleText = (TextView) listItem.findViewById(R.id.title);
        titleText.setText(currentNews.getmTitle());

        TextView dateText = (TextView) listItem.findViewById(R.id.date);
        dateText.setText(currentNews.getmDate());

        TextView timeText = (TextView) listItem.findViewById(R.id.time);
        timeText.setText(currentNews.getmTime());

        TextView storyText = (TextView) listItem.findViewById(R.id.story);
        storyText.setText(currentNews.getmStory());

        TextView authorText = (TextView) listItem.findViewById(R.id.author);
        authorText.setText(currentNews.getmAuthor());

        return listItem;
    }
}
