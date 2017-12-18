package com.codepath.apps.twitterfilter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.twitterfilter.TwitterApp;
import com.codepath.apps.twitterfilter.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by tanvigupta on 7/6/17.
 */

public class FollowersFragment extends UsersListFragment {

    TwitterClient client;

    static long cursor;

    public static FollowersFragment newInstance(long uid) {
        FollowersFragment followersFragment = new FollowersFragment();
        cursor = -1L;
        Bundle args = new Bundle();
        args.putLong("user_id", uid);
        followersFragment.setArguments(args);

        return followersFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApp.getRestClient();

        populateTimeline();
    }

    @Override
    public void setCursor(long l) {
        cursor = l;
    }

    @Override
    public long getCursor() {
        return cursor;
    }

    @Override
    public void populateTimeline() {
        // unpackage bundle that comes from activity
        long uid = getArguments().getLong("user_id");
        // Make network request to get data from Twitter API
        client.getFollowersList(uid, getCursor(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
                try {
                    addItems(response.getJSONArray("users"));
                    setCursor(response.getLong("next_cursor"));
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
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void fetchTimelineAsync(int page) {
        // unpackage bundle that comes from activity
        long uid = getArguments().getLong("user_id");
        // Send the network request to fetch the updated data
        client.getFollowersList(uid, -1L, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    refreshItems(response.getJSONArray("statuses"));
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
