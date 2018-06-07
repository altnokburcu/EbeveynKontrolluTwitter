package com.codepath.apps.twitterfilter.models;

/**
 * Created by burcu on 3/11/18.
 */

public class FirebaseUserModel {
private String mail;
private String password;

    public FirebaseUserModel() {
    }

    public FirebaseUserModel(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
