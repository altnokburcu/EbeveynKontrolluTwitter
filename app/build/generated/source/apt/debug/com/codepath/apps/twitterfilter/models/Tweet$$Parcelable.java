
package com.codepath.apps.twitterfilter.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2018-03-11T22:11+0300")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Tweet$$Parcelable
    implements Parcelable, ParcelWrapper<com.codepath.apps.twitterfilter.models.Tweet>
{

    private com.codepath.apps.twitterfilter.models.Tweet tweet$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Tweet$$Parcelable>CREATOR = new Creator<Tweet$$Parcelable>() {


        @Override
        public Tweet$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Tweet$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Tweet$$Parcelable[] newArray(int size) {
            return new Tweet$$Parcelable[size] ;
        }

    }
    ;

    public Tweet$$Parcelable(com.codepath.apps.twitterfilter.models.Tweet tweet$$2) {
        tweet$$0 = tweet$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(tweet$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.codepath.apps.twitterfilter.models.Tweet tweet$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(tweet$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(tweet$$1));
            parcel$$1 .writeLong(tweet$$1 .uid);
            parcel$$1 .writeString(tweet$$1 .createdAt);
            parcel$$1 .writeString(tweet$$1 .mediaUrl);
            parcel$$1 .writeInt(tweet$$1 .favorite_count);
            parcel$$1 .writeString(tweet$$1 .location);
            parcel$$1 .writeString(tweet$$1 .body);
            com.codepath.apps.twitterfilter.models.User$$Parcelable.write(tweet$$1 .user, parcel$$1, flags$$0, identityMap$$0);
            parcel$$1 .writeString(tweet$$1 .url);
            parcel$$1 .writeInt(tweet$$1 .retweet_count);
            parcel$$1 .writeInt((tweet$$1 .retweeted? 1 : 0));
            parcel$$1 .writeInt((tweet$$1 .favorited? 1 : 0));
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.codepath.apps.twitterfilter.models.Tweet getParcel() {
        return tweet$$0;
    }

    public static com.codepath.apps.twitterfilter.models.Tweet read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.codepath.apps.twitterfilter.models.Tweet tweet$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            tweet$$4 = new com.codepath.apps.twitterfilter.models.Tweet();
            identityMap$$1 .put(reservation$$0, tweet$$4);
            tweet$$4 .uid = parcel$$3 .readLong();
            tweet$$4 .createdAt = parcel$$3 .readString();
            tweet$$4 .mediaUrl = parcel$$3 .readString();
            tweet$$4 .favorite_count = parcel$$3 .readInt();
            tweet$$4 .location = parcel$$3 .readString();
            tweet$$4 .body = parcel$$3 .readString();
            User user$$0 = com.codepath.apps.twitterfilter.models.User$$Parcelable.read(parcel$$3, identityMap$$1);
            tweet$$4 .user = user$$0;
            tweet$$4 .url = parcel$$3 .readString();
            tweet$$4 .retweet_count = parcel$$3 .readInt();
            tweet$$4 .retweeted = (parcel$$3 .readInt() == 1);
            tweet$$4 .favorited = (parcel$$3 .readInt() == 1);
            com.codepath.apps.twitterfilter.models.Tweet tweet$$3 = tweet$$4;
            identityMap$$1 .put(identity$$1, tweet$$3);
            return tweet$$3;
        }
    }

}
