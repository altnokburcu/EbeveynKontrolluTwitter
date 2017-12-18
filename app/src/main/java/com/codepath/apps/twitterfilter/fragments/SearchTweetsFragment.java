package com.codepath.apps.twitterfilter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.twitterfilter.TwitterApp;
import com.codepath.apps.twitterfilter.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by tanvigupta on 7/5/17.
 */

public class SearchTweetsFragment extends TweetsListFragment {

    TwitterClient client;

    public static SearchTweetsFragment newInstance(String query) {
        SearchTweetsFragment searchTweetsFragment = new SearchTweetsFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        searchTweetsFragment.setArguments(args);

        return searchTweetsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApp.getRestClient();

        populateTimeline();
    }

    @Override
    public void populateTimeline() {
        // unpackage bundle that comes from activity
        String query = getArguments().getString("query");
        // Make network request to get data from Twitter API
        client.getSearchResults(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
                try {
                    addItems(response.getJSONArray("statuses"), "search");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
                Toast.makeText(getContext(), "No internet, search unavailable", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
                Toast.makeText(getContext(), "No internet, search unavailable", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
                Toast.makeText(getContext(), "No internet, search unavailable", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void fetchTimelineAsync(int page) {
        // unpackage bundle that comes from activity
        String query = getArguments().getString("query");
        // Send the network request to fetch the updated data
        client.getSearchResults(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    refreshItems(response.getJSONArray("statuses"), "search");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                swipeContainer.setRefreshing(false);
            }
        });
    }
}
