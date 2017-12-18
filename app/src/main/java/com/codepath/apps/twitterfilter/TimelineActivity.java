package com.codepath.apps.twitterfilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.twitterfilter.fragments.TweetsListFragment;
import com.codepath.apps.twitterfilter.fragments.TweetsPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity {

    private final int COMPOSE_REQUEST = 10;

    @BindView(R.id.viewpager) ViewPager viewPager;
    TweetsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        //Intent i = new Intent(this, AddFilterActivity.class);
       // startActivity(i);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");

        adapter = new TweetsPagerAdapter(getSupportFragmentManager(), this);

        // set up the adapter for the pager
        viewPager.setAdapter(adapter);

        // setup the Tab Layout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    public void onComposeAction(MenuItem miCompose) {
        Intent intent = new Intent(this, ComposeActivity.class);
        intent.putExtra("reply", false);
        startActivityForResult(intent, COMPOSE_REQUEST);
    }

    public void onProfileView(MenuItem miProfile) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void onEbvAction(MenuItem miEbv) {
        Intent intent = new Intent(this, EbeveynActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TweetsListFragment fragment = (TweetsListFragment) adapter.getRegisteredFragment(viewPager.getCurrentItem());
        fragment.activityResult(requestCode, resultCode, data);
    }
}
