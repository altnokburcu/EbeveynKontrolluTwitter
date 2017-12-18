package com.codepath.apps.twitterfilter.models;

import com.codepath.apps.twitterfilter.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;


@Table(database = MyDatabase.class)
@Parcel(analyze={Tweet.class})
public class Tweet extends BaseModel {

    // attributes of the tweet class
    @Column
    public String body;

    @Column
    @PrimaryKey
    public long uid; // database ID for the tweet

    @Column
    public String createdAt;

    @Column
    @ForeignKey(saveForeignKeyModel = true)
    public User user;

    @Column
    public String mediaUrl;

    @Column
    public String url; // to remove from tweet body

    @Column
    public boolean retweeted;

    @Column
    public boolean favorited;

    @Column
    public int retweet_count;

    @Column
    public int favorite_count;

    @Column
    public String location;

    public Tweet() {
    }

    // deserialize JSON - take in JSON object, return Tweet object
    public static Tweet fromJSON(JSONObject jsonObject, String location) throws JSONException {
        Tweet tweet = new Tweet();
        // extract values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

        if (jsonObject.has("entities") && jsonObject.getJSONObject("entities").has("media")) {
            tweet.mediaUrl = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url");
            tweet.url = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("url");

            tweet.body = tweet.body.replace(tweet.url, "");
        } else {
            tweet.mediaUrl = "";
            tweet.url = "";
        }

        tweet.retweeted = jsonObject.getBoolean("retweeted");
        tweet.favorited = jsonObject.getBoolean("favorited");

        tweet.retweet_count = jsonObject.getInt("retweet_count");
        tweet.favorite_count = jsonObject.getInt("favorite_count");

        tweet.location = location;

        // enter tweet into database
        tweet.save();

        return tweet;
    }
}
