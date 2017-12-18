package com.codepath.apps.twitterfilter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitterfilter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.ivProfileImage) public ImageView ivProfileImage;
    @BindView(R.id.tvUsername) public TextView tvUsername;
    @BindView(R.id.tvHandle) public TextView tvHandle;
    @BindView(R.id.tvBody) public TextView tvBody;
    @BindView(R.id.ivMediaImage) public ImageView ivMediaImage;
    @BindView(R.id.tvTimestamp) public TextView tvTimestamp;
    @BindView(R.id.btnReply) public ImageButton btnReply;
    @BindView(R.id.btnRetweet) public ImageButton btnRetweet;
    @BindView(R.id.btnFav) public ImageButton btnFav;
    @BindView(R.id.tvRTCount) public TextView tvRTCount;
    @BindView(R.id.tvFavCount) public TextView tvFavCount;

    Tweet tweet;

    Context context;
    Context mContext;
    TwitterClient client;
    TweetAdapter adapter;

    int COMPOSE_REQUEST = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        context = this;
        client = TwitterApp.getRestClient();

        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        mContext = TweetAdapter.getContext();

        tvUsername.setText(tweet.user.name);
        tvBody.setText(tweet.body);
        tvHandle.setText(String.format("@" + tweet.user.screenName));
        tvTimestamp.setText(getDateTime(tweet.createdAt));
        tvRTCount.setText(tweet.retweet_count + "");
        tvFavCount.setText(tweet.favorite_count + "");

        Glide
                .with(this)
                .load(tweet.user.profileImageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 20, 0))
                .into(ivProfileImage);

        if (tweet.mediaUrl != "") {
            ivMediaImage.setVisibility(View.VISIBLE);
            Glide
                    .with(this)
                    .load(tweet.mediaUrl)
                    .into(ivMediaImage);
        } else {
            ivMediaImage.setVisibility(View.GONE);
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ComposeActivity.class);
                intent.putExtra("reply", true);
                intent.putExtra("tweet", Parcels.wrap(tweet));
                ((AppCompatActivity) mContext).startActivityForResult(intent, COMPOSE_REQUEST);
            }
        });

        if (tweet.retweeted) {
            btnRetweet.setImageResource(R.drawable.ic_vector_retweet);
            btnRetweet.setColorFilter(Color.rgb(29, 191, 99));
            tvRTCount.setTextColor(Color.rgb(29, 191, 99));
        } else {
            btnRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
            btnRetweet.setColorFilter(Color.rgb(170, 184, 194));
            tvRTCount.setTextColor(Color.rgb(170, 184, 194));
        }

        if (tweet.favorited) {
            btnFav.setImageResource(R.drawable.ic_vector_heart);
            btnFav.setColorFilter(Color.rgb(224,36,94));
            tvFavCount.setTextColor(Color.rgb(224,36,94));
        } else {
            btnFav.setImageResource(R.drawable.ic_vector_heart_stroke);
            btnFav.setColorFilter(Color.rgb(170, 184, 194));
            tvFavCount.setTextColor(Color.rgb(170, 184, 194));
        }

        btnRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tweet.retweeted) {
                    client.retweet(tweet.uid, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("TwitterClient", response.toString());
                            tweet.retweeted = true;
                            tweet.retweet_count += 1;
                            //adapter.notifyDataSetChanged();
                            btnRetweet.setImageResource(R.drawable.ic_vector_retweet);
                            btnRetweet.setColorFilter(Color.rgb(29, 191, 99));
                            tvRTCount.setText(tweet.retweet_count + "");
                            tvRTCount.setTextColor(Color.rgb(29, 191, 99));
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            Log.d("TwitterClient", response.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("TwitterClient", responseString);
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d("TwitterClient", throwable.getMessage());
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            Log.d("TwitterClient", throwable.getMessage());
                            throwable.printStackTrace();
                        }
                    });
                } else {
                    client.unRetweet(tweet.uid, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("TwitterClient", response.toString());
                            tweet.retweeted = false;
                            tweet.retweet_count -= 1;
                            //adapter.notifyDataSetChanged();
                            btnRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
                            btnRetweet.setColorFilter(Color.rgb(170, 184, 194));
                            tvRTCount.setText(tweet.retweet_count + "");
                            tvRTCount.setTextColor(Color.rgb(170, 184, 194));
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            Log.d("TwitterClient", response.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("TwitterClient", responseString);
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d("TwitterClient", throwable.getMessage());
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            Log.d("TwitterClient", throwable.getMessage());
                            throwable.printStackTrace();
                        }
                    });
                }
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tweet.favorited) {
                    client.favorite(tweet.uid, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("TwitterClient", response.toString());
                            tweet.favorited = true;
                            tweet.favorite_count += 1;
                            btnFav.setImageResource(R.drawable.ic_vector_heart);
                            btnFav.setColorFilter(Color.rgb(224,36,94));
                            tvFavCount.setText(tweet.favorite_count + "");
                            tvFavCount.setTextColor(Color.rgb(224,36,94));
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            Log.d("TwitterClient", response.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("TwitterClient", responseString);
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d("TwitterClient", throwable.getMessage());
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            Log.d("TwitterClient", throwable.getMessage());
                            throwable.printStackTrace();
                        }
                    });
                } else {
                    client.unFavorite(tweet.uid, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("TwitterClient", response.toString());
                            tweet.favorited = false;
                            tweet.favorite_count -= 1;
                            btnFav.setImageResource(R.drawable.ic_vector_heart_stroke);
                            btnFav.setColorFilter(Color.rgb(170, 184, 194));
                            tvFavCount.setText(tweet.favorite_count + "");
                            tvFavCount.setTextColor(Color.rgb(170, 184, 194));
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            Log.d("TwitterClient", response.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("TwitterClient", responseString);
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d("TwitterClient", throwable.getMessage());
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            Log.d("TwitterClient", throwable.getMessage());
                            throwable.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    public String getDateTime(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.formatDateTime(this, dateMillis, DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME
                    | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_ABBREV_ALL);

            relativeDate = relativeDate.replace(" ago", "");
            relativeDate = relativeDate.replace(" sec.", "s");
            relativeDate = relativeDate.replace(" min.", "m");
            relativeDate = relativeDate.replace(" hr.", "h");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent();
        in.putExtra("tweet", Parcels.wrap(tweet));
        setResult(RESULT_OK, in);
        finish();
    }
}
