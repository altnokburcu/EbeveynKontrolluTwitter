package com.codepath.apps.twitterfilter.models;

import com.codepath.apps.twitterfilter.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by burcu
 */

@Table(database = MyDatabase.class)
@Parcel(analyze={User.class})
public class User extends BaseModel {

    // attributes of this class
    @Column
    public String name;

    @Column
    @PrimaryKey
    public long uid;

    @Column
    public String screenName;

    @Column
    public String profileImageUrl;

    @Column
    public String tagline;

    @Column
    public int followingCount;

    @Column
    public int followersCount;

    public User() {
    }

    // deserialize the JSON
    public static User fromJSON(JSONObject json) throws JSONException {
        User user = new User();

        // extract values
        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");
        user.tagline = json.getString("description");
        user.followersCount = json.getInt("followers_count");
        user.followingCount = json.getInt("friends_count");

        String s = user.profileImageUrl;
        s = s.replace("normal", "bigger");
        user.profileImageUrl = s;

        // enter user into database
        user.save();

        return user;
    }
}
