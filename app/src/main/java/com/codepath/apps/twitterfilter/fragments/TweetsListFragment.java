package com.codepath.apps.twitterfilter.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitterfilter.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitterfilter.MyDividerItemDecoration;
import com.codepath.apps.twitterfilter.R;
import com.codepath.apps.twitterfilter.TweetAdapter;
import com.codepath.apps.twitterfilter.models.ChildModel;
import com.codepath.apps.twitterfilter.models.DbHelper;
import com.codepath.apps.twitterfilter.models.FilterModel;
import com.codepath.apps.twitterfilter.models.Tweet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;



public class TweetsListFragment extends Fragment {

    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;

    @BindView(R.id.rvTweet) RecyclerView rvTweets;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;

    private final int COMPOSE_REQUEST = 10;
    private final int DETAIL_REQUEST = 20;

    long oldest;
    private EndlessRecyclerViewScrollListener scrollListener;

    private Unbinder unbinder;

    ConnectivityManager cm;
    NetworkInfo activeNetwork;
    boolean isConnected;
    private ChildModel childModel;
    private List<FilterModel> filterList ;
    private String kullanici;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Context context;

    // inflation happens inside onCreateView
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the view
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);
        unbinder = ButterKnife.bind(this, v);
        context = v.getContext();
        filterList = new ArrayList<>();
        getList();

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
        tweets = new ArrayList<>();

        //construct the adapter from the array list
        tweetAdapter = new TweetAdapter(tweets, getContext());

        // RecyclerView setup (layout manager, use adapter)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.setAdapter(tweetAdapter);
        MyDividerItemDecoration dividerItemDecoration = new MyDividerItemDecoration(rvTweets.getContext());
        rvTweets.addItemDecoration(dividerItemDecoration);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                populateTimeline();
            }
        };
        // Adds the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);

        // check internet connection
        cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public void addItems(JSONArray response, String location) {
        try {
            oldest = Tweet.fromJSON(response.getJSONObject(0), location).uid;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // iterate through array
        for (int i = 0; i < response.length(); i++) {
            // for each entry, deserialize JSON object, convert to Tweet
            // add Tweet to list, notify adapter that dataset has changed
            try {
                boolean status=false;
             //   Log.d("deneme2",response.getJSONObject(i).getString("text"));
                String tweetContent = response.getJSONObject(i).getString("text");
                status = filter(tweetContent);
                if(status==false) {
                    //tweetcontent teki kelimeler tek tek split edilip içindeki istemediğimiz kelimeleri veriler.add fonksiyonuna
                    //göndermiycez
                    Tweet tweet = Tweet.fromJSON(response.getJSONObject(i), location);
                    if (tweet.uid < oldest) {
                        oldest = tweet.uid;
                    }

                    tweets.add(tweet);
                    tweetAdapter.notifyItemInserted(tweets.size() - 1);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        scrollListener.resetState();
    }

    public void addItems(List<Tweet> response) {
        oldest = response.get(0).uid;

        // iterate through array
        for (int i = 0; i < response.size(); i++) {
            // for each entry, deserialize JSON object, convert to Tweet
            // add Tweet to list, notify adapter that dataset has changed
            Tweet tweet = response.get(i);

            if (tweet.uid < oldest) {
                oldest = tweet.uid;
            }

            tweets.add(tweet);
            tweetAdapter.notifyItemInserted(tweets.size() - 1);
        }

        scrollListener.resetState();
    }

    public void populateTimeline() {}

    public void refreshItems(JSONArray response, String location) {
        try {
            getList();
            // clear old items before appending new ones
            tweetAdapter.clear();

            List<Tweet> new_tweets = new ArrayList<Tweet>();

            for (int i = 0; i < response.length(); i++) {

                if(!filter(response.getJSONObject(i).getString("text"))){
                    new_tweets.add(Tweet.fromJSON(response.getJSONObject(i), location));
                }

                Log.d("deneme",Tweet.fromJSON(response.getJSONObject(i), location).toString());
            }

            // add new items to adapter
            tweetAdapter.addAll(new_tweets);

            // Now we call setRefreshing(false) to signal refresh has finished
            swipeContainer.setRefreshing(false);

            scrollListener.resetState();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void fetchTimelineAsync(int page) {}

    public void activityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COMPOSE_REQUEST && resultCode == RESULT_OK) {
            Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));
            tweets.add(0, tweet);
            tweetAdapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);
        } else if (requestCode == DETAIL_REQUEST && resultCode == RESULT_OK) {
            Log.i("TweetsListFragment", "Attempting to update spontaneously" + DETAIL_REQUEST);
            Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));
            int position = -1;
            for (int i = 0; i < tweets.size(); i++) {
                if (tweets.get(i).uid == tweet.uid) {
                    position = i;
                    break;
                }
            }
            tweetAdapter.notifyItemChanged(position);
        }
    }

    public void getList(){
        DbHelper db = new DbHelper(context);
        kullanici = db.kullaniciCek();
        if(kullanici!=null){
            Log.d("kullanici",kullanici);
            isChild(kullanici);
        }
    }
    public boolean filter(String tweetContent){
        boolean status = false;
        String[] parca = tweetContent.split(" ");
        //Log.d("filtre metodu", filterList.toString());

        if(!(filterList ==null)){
            for( int j=0; j<parca.length; j++){
                //dışarıdan kelime girişi yapılacak,yapılan kelimelere göre filtreleme yapılacak
                for(int a=0; a<filterList.size(); a++){
                    if(parca[j].equals(filterList.get(a).getFilter())){
                        status=true;
                    }

                }
            }
        }
        return status;
    }

    public void getFilterFromFirebase(){

            DatabaseReference myRef = database.getReference("users").child(auth.getCurrentUser().getUid()).child("filtreler");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    filterList.clear();
                    Log.d("Veriler", dataSnapshot.getChildrenCount() + "");
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        filterList.add(snap.getValue(FilterModel.class));
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("", "Failed to read value.", error.toException());
                }

            });

    }

    public void isChild(final String name){
        if(!(auth.getCurrentUser() == null)){
            DatabaseReference myRef = database.getReference()
                    .child("users").child(auth.getCurrentUser().getUid()).child("childs");
            Log.d("getuid",auth.getCurrentUser().getUid());
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()){
                        childModel = snap.getValue(ChildModel.class);
                        if (childModel.getName().equals(name)){
                            Log.d("cocukModel",childModel.getName());
                            getFilterFromFirebase();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
