package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    /**
     * URL for news items data from the Guardian website
     */
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search?q=estonia";

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    private RecyclerView mRecyclerView;
    private NewsItemRecyclerAdapter mNewsItemRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * Constant value for the news item loader ID. Can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWSITEM_LOADER_ID = 1;

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String listSize = sharedPreferences.getString(
                getString(R.string.settings_newsitem_list_size_key),
                getString(R.string.settings_newsitem_list_size_default));

        String keyword = sharedPreferences.getString(
                getString(R.string.settings_keyword_key),
                getString(R.string.settings_keyword_default));

        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", keyword);
        uriBuilder.appendQueryParameter("page-size", listSize);
        uriBuilder.appendQueryParameter("api-key", "test");

        // Create a new loader for the given URL
        return new NewsItemLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> newsItems) {

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mRecyclerView.setVisibility(View.VISIBLE);
        mNewsItemRecyclerAdapter = new NewsItemRecyclerAdapter(MainActivity.this, new ArrayList<NewsItem>());

        if (newsItems != null && !newsItems.isEmpty()) {
            mNewsItemRecyclerAdapter = new NewsItemRecyclerAdapter(MainActivity.this, newsItems);
            mRecyclerView.setAdapter(mNewsItemRecyclerAdapter);
        } else {
            if (networkInfo != null && networkInfo.isConnected()) {

                mRecyclerView.setVisibility(View.GONE);
                mEmptyStateTextView.setText(R.string.no_news_found);

            } else {

                // Set empty state text to display "No news found"
                mRecyclerView.setVisibility(View.GONE);
                mEmptyStateTextView.setText(R.string.no_internet_connection);
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {

        mNewsItemRecyclerAdapter = new NewsItemRecyclerAdapter(MainActivity.this, new ArrayList<NewsItem>());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data.
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            Log.i(LOG_TAG, "TEST: calling initLoader() ...");
            loaderManager.initLoader(NEWSITEM_LOADER_ID, null, this);
        } else {
            // Otherwise display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }
}
