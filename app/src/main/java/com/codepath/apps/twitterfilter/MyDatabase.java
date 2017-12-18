package com.codepath.apps.twitterfilter;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {

    public static final String NAME = "RestClientDatabase";

    public static final int VERSION = 1;
}
