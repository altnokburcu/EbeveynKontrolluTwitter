package com.codepath.apps.twitterfilter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitterfilter.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitterfilter.MyDividerItemDecoration;
import com.codepath.apps.twitterfilter.R;
import com.codepath.apps.twitterfilter.UserAdapter;
import com.codepath.apps.twitterfilter.models.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tanvigupta on 7/6/17.
 */

public class UsersListFragment extends Fragment {

    UserAdapter userAdapter;
    ArrayList<User> users;

    @BindView(R.id.rvUser) RecyclerView rvUsers;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;

    long oldest;
    private EndlessRecyclerViewScrollListener scrollListener;

    private Unbinder unbinder;

    // inflation happens inside onCreateView
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the view
        View v = inflater.inflate(R.layout.fragments_users_list, container, false);
        unbinder = ButterKnife.bind(this, v);

        oldest = 0;

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // initialize arraylist
        users = new ArrayList<>();

        //construct the adapter from the array list
        userAdapter = new UserAdapter(users, getContext());

        // RecyclerView setup (layout manager, use adapter)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvUsers.setLayoutManager(linearLayoutManager);
        rvUsers.setAdapter(userAdapter);
        MyDividerItemDecoration dividerItemDecoration = new MyDividerItemDecoration(rvUsers.getContext());
        rvUsers.addItemDecoration(dividerItemDecoration);

        // TODO: Endless scroll follower/following equivalent
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if (getCursor() != 0L) {
                    populateTimeline();
                }
            }
        };
        // Adds the scroll listener to RecyclerView
        rvUsers.addOnScrollListener(scrollListener);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void addItems(JSONArray response) {
        try {
            oldest = User.fromJSON(response.getJSONObject(0)).uid;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // iterate through array
        for (int i = 0; i < response.length(); i++) {
            // for each entry, deserialize JSON object, convert to Tweet
            // add Tweet to list, notify adapter that dataset has changed
            try {
                User user = User.fromJSON(response.getJSONObject(i));

                if (user.uid < oldest) {
                    oldest = user.uid;
                }

                users.add(user);
                userAdapter.notifyItemInserted(users.size() - 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        scrollListener.resetState();
    }

    public void populateTimeline() {}

    public void refreshItems(JSONArray response) {
        try {
            // clear old items before appending new ones
            userAdapter.clear();

            List<User> new_users = new ArrayList<User>();

            for (int i = 0; i < response.length(); i++) {
                new_users.add(User.fromJSON(response.getJSONObject(i)));
            }

            // add new items to adapter
            userAdapter.addAll(new_users);

            // Now we call setRefreshing(false) to signal refresh has finished
            swipeContainer.setRefreshing(false);

            scrollListener.resetState();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void fetchTimelineAsync(int page) {}

    public void setCursor(long l) {}

    public long getCursor() {
        return 0L;
    }
}
