package com.codepath.apps.twitterfilter;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.twitterfilter.fragments.FollowersFragment;

public class FollowersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        long uid = getIntent().getLongExtra("user_id", 0);

        // create the user fragment
        FollowersFragment followersFragment = FollowersFragment.newInstance(uid);

        // display the user timeline fragment inside the container (dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // make change
        ft.replace(R.id.flContainer, followersFragment);
        // commit
        ft.commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");
    }
}
