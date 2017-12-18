package com.codepath.apps.twitterfilter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitterfilter.fragments.UserTimelineFragment;
import com.codepath.apps.twitterfilter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        context = this;

        final String screenName = getIntent().getStringExtra("screen_name");

        // create the user fragment
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        // display the user timeline fragment inside the container (dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // make change
        ft.replace(R.id.flContainer, userTimelineFragment);
        // commit
        ft.commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");

        client = TwitterApp.getRestClient();
        client.getUserInfo(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User user = null;
                try {
                    user = User.fromJSON(response);

                    // populate user headline
                    populateUserHeadline(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void populateUserHeadline(final User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvHandle = (TextView) findViewById(R.id.tvHandle);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        tvName.setText(user.name);
        tvHandle.setText("@" + user.screenName);
        tvTagline.setText(user.tagline);

        String follower_count = format(user.followersCount);
        String following_count = format(user.followingCount);

        // Span to make text black
        ForegroundColorSpan redForegroundColorSpan = new ForegroundColorSpan(Color.rgb(0, 0, 0));
        // Span to make text bold
        StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD);

        // Use a SpannableStringBuilder so that both the text and the spans are mutable
        SpannableStringBuilder ssb_a = new SpannableStringBuilder(follower_count);
        SpannableStringBuilder ssb_b = new SpannableStringBuilder(following_count);

        // Apply the color span
        ssb_a.setSpan(redForegroundColorSpan, 0, ssb_a.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb_a.setSpan(bold, 0, ssb_a.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ssb_b.setSpan(redForegroundColorSpan, 0, ssb_b.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb_b.setSpan(bold, 0, ssb_b.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Add a blank space
        ssb_a.append(" Followers");
        ssb_b.append(" Following");

        tvFollowers.setText(ssb_a, TextView.BufferType.EDITABLE);
        tvFollowing.setText(ssb_b, TextView.BufferType.EDITABLE);

        Glide
                .with(this)
                .load(user.profileImageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 20, 0))
                .into(ivProfileImage);

        tvFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FollowersActivity.class);
                intent.putExtra("user_id", user.uid);
                startActivity(intent);
            }
        });

        tvFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FriendsActivity.class);
                intent.putExtra("user_id", user.uid);
                startActivity(intent);
            }
        });

    }

    public double truncate(int n) {
        double num = (double) n;

        if (n >= 1000000000.0) {
            num = num / 1000000000.0;
        } else if (n >= 1000000.0) {
            num = num / 1000000.0;
        } else if (n >= 10000.0) {
            num = num / 1000.0;
        }

        return num;
    }

    public String format(int n) {
        if (n >= 1000000000) {
            return String.format("%.2fB", truncate(n));
        } else if (n >= 1000000) {
            return String.format("%.1fM", truncate(n));
        } else if (n >= 10000) {
            return String.format("%.1fK", truncate(n));
        }
        return String.format(String.valueOf(n));
    }
}