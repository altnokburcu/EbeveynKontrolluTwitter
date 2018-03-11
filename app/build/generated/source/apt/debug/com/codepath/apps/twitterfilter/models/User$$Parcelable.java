
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
public class User$$Parcelable
    implements Parcelable, ParcelWrapper<com.codepath.apps.twitterfilter.models.User>
{

    private com.codepath.apps.twitterfilter.models.User user$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<User$$Parcelable>CREATOR = new Creator<User$$Parcelable>() {


        @Override
        public User$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new User$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public User$$Parcelable[] newArray(int size) {
            return new User$$Parcelable[size] ;
        }

    }
    ;

    public User$$Parcelable(com.codepath.apps.twitterfilter.models.User user$$2) {
        user$$0 = user$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(user$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.codepath.apps.twitterfilter.models.User user$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(user$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(user$$1));
            parcel$$1 .writeLong(user$$1 .uid);
            parcel$$1 .writeString(user$$1 .name);
            parcel$$1 .writeString(user$$1 .tagline);
            parcel$$1 .writeString(user$$1 .screenName);
            parcel$$1 .writeInt(user$$1 .followersCount);
            parcel$$1 .writeString(user$$1 .profileImageUrl);
            parcel$$1 .writeInt(user$$1 .followingCount);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.codepath.apps.twitterfilter.models.User getParcel() {
        return user$$0;
    }

    public static com.codepath.apps.twitterfilter.models.User read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.codepath.apps.twitterfilter.models.User user$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            user$$4 = new com.codepath.apps.twitterfilter.models.User();
            identityMap$$1 .put(reservation$$0, user$$4);
            user$$4 .uid = parcel$$3 .readLong();
            user$$4 .name = parcel$$3 .readString();
            user$$4 .tagline = parcel$$3 .readString();
            user$$4 .screenName = parcel$$3 .readString();
            user$$4 .followersCount = parcel$$3 .readInt();
            user$$4 .profileImageUrl = parcel$$3 .readString();
            user$$4 .followingCount = parcel$$3 .readInt();
            com.codepath.apps.twitterfilter.models.User user$$3 = user$$4;
            identityMap$$1 .put(identity$$1, user$$3);
            return user$$3;
        }
    }

}
