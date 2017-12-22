package com.codepath.apps.twitterfilter.models;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Burcu on 22.12.2017.
 */

public class FilterModel implements DatabaseReference.CompletionListener {
    private String filter;

    public FilterModel() {
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public FilterModel(String filter) {

        this.filter = filter;
    }

    @Override
    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

    }
}
