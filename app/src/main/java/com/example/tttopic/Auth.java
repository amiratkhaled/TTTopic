package com.example.tttopic;


import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;


public class Auth extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Twitter.initialize(this);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Fabric.with(this,  new Crashlytics());
    }

    public static class Constants {
        // Twitter
        private static final String TWITTER_KEY = "Ddo5yTv9Ld4O2fxoIzwbh285f";
        private static final String TWITTER_SECRET = "9qmVZWVVWOOeSzdc6oqAid1n5GhLYISbczlLMoHOLXlJKgytRr";
        // Collect Tweet Service
        public static final int COLLECT_TWEET_CODE = 10;
        // Storage Service
        public static final String STORAGE_FILE = "digest.json";
        // Location Service
        public static final int LOCATION_PERMISSION_CODE = 20;
    }
}