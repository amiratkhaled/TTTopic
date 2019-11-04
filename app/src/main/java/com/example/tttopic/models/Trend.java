package com.example.tttopic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trend implements Comparable<Trend> {



    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("promoted_content")
    @Expose
    private Object promotedContent;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("tweet_volume")
    @Expose
    private long tweetVolume;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getPromotedContent() {
        return promotedContent;
    }

    public void setPromotedContent(Object promotedContent) {
        this.promotedContent = promotedContent;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public long getTweetVolume() {
        return tweetVolume;
    }

    public void setTweetVolume(long tweetVolume) {
        this.tweetVolume = tweetVolume;
    }

    @Override
    public int compareTo(Trend another) {
        if (tweetVolume > another.getTweetVolume())
            return 1;

        if (tweetVolume< another.tweetVolume)
            return -1;

        return 0;
    }
}