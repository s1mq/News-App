package com.example.android.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter for RecyclerView
 */

public class NewsItemRecyclerAdapter extends RecyclerView.Adapter<NewsItemRecyclerAdapter.ViewHolder> {

    public List<NewsItem> mNewsItems;
    public MainActivity mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView section;
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            section = itemView.findViewById(R.id.section);
            title = itemView.findViewById(R.id.title);
        }
    }

    public NewsItemRecyclerAdapter(MainActivity context, List<NewsItem> newsItems) {
        mNewsItems = newsItems;
        mContext = context;
    }

    @Override
    public NewsItemRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        ViewHolder vh = new ViewHolder(listItem);
        return vh;
    }

    @Override
    public void onBindViewHolder(NewsItemRecyclerAdapter.ViewHolder holder, int position) {
        final NewsItem currentNewsItem = mNewsItems.get(position);

        holder.section.setText(currentNewsItem.getSection());
        holder.title.setText(currentNewsItem.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentNewsItem.getUrl());

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                mContext.startActivity(websiteIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }
}
