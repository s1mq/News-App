package com.example.android.newsapp;

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

    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }
}
