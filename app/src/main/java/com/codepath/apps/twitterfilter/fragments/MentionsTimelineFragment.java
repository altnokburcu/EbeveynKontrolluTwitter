package com.codepath.apps.twitterfilter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.twitterfilter.TwitterApp;
import com.codepath.apps.twitterfilter.TwitterClient;
import com.codepath.apps.twitterfilter.models.Tweet;
import com.codepath.apps.twitterfilter.models.Tweet_Table;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MentionsTimelineFragment extends TweetsListFragment {

    TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApp.getRestClient();

        populateTimeline();
    }

    @Override
    public void populateTimeline() {
        // Make network request to get data from Twitter API
        client.getMentionsTimeline(oldest - 1, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
            }

            // use this method since array response is expected
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("TwitterClient", response.toString());
                addItems(response, "mentions");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
                if (!isConnected) {
                    List<Tweet> tweets = SQLite.select().from(Tweet.class).where(Tweet_Table.location.is("mentions")).queryList();
                    addItems(tweets);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", throwable.getMessage());
                throwable.printStackTrace();
                if (!isConnected) {
                    List<Tweet> tweets = SQLite.select().from(Tweet.class).where(Tweet_Table.location.is("mentions")).queryList();
                    addItems(tweets);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", throwable.getMessage());
                throwable.printStackTrace();
                if (!isConnected) {
                    List<Tweet> tweets = SQLite.select().from(Tweet.class).where(Tweet_Table.location.is("mentions")).queryList();
                    addItems(tweets);
                }
            }
        });
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        client.getMentionsTimeline(0, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                refreshItems(response, "mentions");
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
