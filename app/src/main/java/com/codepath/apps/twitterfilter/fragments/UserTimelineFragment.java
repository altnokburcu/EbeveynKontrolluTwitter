package com.codepath.apps.twitterfilter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.twitterfilter.TwitterApp;
import com.codepath.apps.twitterfilter.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class UserTimelineFragment extends TweetsListFragment {

    TwitterClient client;

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userTimelineFragment.setArguments(args);

        return userTimelineFragment;
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
        String screenName = getArguments().getString("screen_name");
        // Make network request to get data from Twitter API
        client.getUserTimeline(oldest - 1, screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
            }

            // use this method since array response is expected
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("TwitterClient", response.toString());
                addItems(response, "user");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
                if (!isConnected) {
                    Toast.makeText(getContext(), "No internet, cannot load tweets", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", throwable.getMessage());
                throwable.printStackTrace();
                if (!isConnected) {
                    Toast.makeText(getContext(), "No internet, cannot load tweets", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", throwable.getMessage());
                throwable.printStackTrace();
                if (!isConnected) {
                    Toast.makeText(getContext(), "No internet, cannot load tweets", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void fetchTimelineAsync(int page) {
        // unpackage bundle that comes from activity
        String screenName = getArguments().getString("screen_name");
        // Send the network request to fetch the updated data
        client.getUserTimeline(0, screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                refreshItems(response, "user");
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
