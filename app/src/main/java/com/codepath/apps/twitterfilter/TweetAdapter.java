package com.codepath.apps.twitterfilter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by tanvigupta on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;

    Context context;
    static Context mContext;

    TwitterClient client;

    private final int COMPOSE_REQUEST = 10;
    private final int DETAIL_REQUEST = 20;


    public interface TweetAdapterListener {
        public void onItemSelected(View view, int position);
    }

    // pass Tweets array into constructor
    public TweetAdapter(List<Tweet> tweets, Context context) {
        mTweets = tweets;
        mContext = context;
        //mListener = tweetAdapterListener;
    }

    // inflate layout and cache references into ViewHolder for each row
    // only called when entirely new row is to be created
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);

        return viewHolder;
    }

    // bind values based on position of the element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data according to position
        final Tweet tweet = mTweets.get(position);

        client = TwitterApp.getRestClient();

        // populate the views according to this data
        holder.tvUserName.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvHandle.setText(String.format("@" + tweet.user.screenName));
        holder.tvTimestamp.setText(getRelativeTimeAgo(tweet.createdAt));
        holder.tvRTCount.setText(tweet.retweet_count + "");
        holder.tvFavCount.setText(tweet.favorite_count + "");

        Glide
                .with(context)
                .load(tweet.user.profileImageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, 20, 0))
                .into(holder.ivProfileImage);

        if (!tweet.mediaUrl.equals("")) {
            holder.ivMediaImage.setVisibility(View.VISIBLE);
            Glide
                    .with(context)
                    .load(tweet.mediaUrl)
                    .bitmapTransform(new RoundedCornersTransformation(context, 30, 0))
                    .into(holder.ivMediaImage);
        } else {
            holder.ivMediaImage.setImageResource(0);
            holder.ivMediaImage.setVisibility(View.GONE);
        }

        holder.btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ComposeActivity.class);
                intent.putExtra("reply", true);
                intent.putExtra("tweet", Parcels.wrap(tweet));
                ((AppCompatActivity) mContext).startActivityForResult(intent, COMPOSE_REQUEST);
            }
        });

        holder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("screen_name", tweet.user.screenName);
                mContext.startActivity(intent);
            }
        });

        if (tweet.retweeted) {
            holder.btnRetweet.setImageResource(R.drawable.ic_vector_retweet);
            holder.btnRetweet.setColorFilter(Color.rgb(29, 191, 99));
            holder.tvRTCount.setTextColor(Color.rgb(29, 191, 99));
        } else {
            holder.btnRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
            holder.btnRetweet.setColorFilter(Color.rgb(170, 184, 194));
            holder.tvRTCount.setTextColor(Color.rgb(170, 184, 194));
        }

        if (tweet.favorited) {
            holder.btnFav.setImageResource(R.drawable.ic_vector_heart);
            holder.btnFav.setColorFilter(Color.rgb(224,36,94));
            holder.tvFavCount.setTextColor(Color.rgb(224,36,94));
        } else {
            holder.btnFav.setImageResource(R.drawable.ic_vector_heart_stroke);
            holder.btnFav.setColorFilter(Color.rgb(170, 184, 194));
            holder.tvFavCount.setTextColor(Color.rgb(170, 184, 194));
        }

        holder.btnRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tweet.retweeted) {
                    client.retweet(tweet.uid, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("TwitterClient", response.toString());
                            tweet.retweeted = true;
                            tweet.retweet_count += 1;
                            notifyDataSetChanged();
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
                            notifyDataSetChanged();
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

        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tweet.favorited) {
                    client.favorite(tweet.uid, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("TwitterClient", response.toString());
                            tweet.favorited = true;
                            tweet.favorite_count += 1;
                            notifyDataSetChanged();
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
                            notifyDataSetChanged();
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

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();

            relativeDate = relativeDate.replace(" ago", "");
            relativeDate = relativeDate.replace(" sec.", "s");
            relativeDate = relativeDate.replace(" min.", "m");
            relativeDate = relativeDate.replace(" hr.", "h");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

    public static Context getContext() {
        return mContext;
    }

    // create ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ivProfileImage) public ImageView ivProfileImage;
        @BindView(R.id.tvUserName) public TextView tvUserName;
        @BindView(R.id.tvBody) public TextView tvBody;
        @BindView(R.id.tvHandle) public TextView tvHandle;
        @BindView(R.id.tvTimestamp) public TextView tvTimestamp;
        @BindView(R.id.ivMediaImage) public ImageView ivMediaImage;
        @BindView(R.id.btnReply) public ImageButton btnReply;
        @BindView(R.id.btnRetweet) public ImageButton btnRetweet;
        @BindView(R.id.btnFav) public ImageButton btnFav;
        @BindView(R.id.tvRTCount) public TextView tvRTCount;
        @BindView(R.id.tvFavCount) public TextView tvFavCount;

        // constructor
        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Tweet tweet = mTweets.get(position);
                Intent in = new Intent(context, DetailActivity.class);
                in.putExtra("tweet", Parcels.wrap(tweet));
                ((AppCompatActivity) mContext).startActivityForResult(in, DETAIL_REQUEST);
            }
        }
    }
}
