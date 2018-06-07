package com.codepath.apps.twitterfilter.models;

/**
 * Created by burcu on 3/12/18.
 */

public class ChildModel {
    private String key;
    private String name;

    public  ChildModel(){

    }
    public ChildModel(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
