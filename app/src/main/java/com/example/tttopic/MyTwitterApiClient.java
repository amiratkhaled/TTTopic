package com.example.tttopic;

import com.example.tttopic.models.Location;
import com.example.tttopic.models.Place;
import com.example.tttopic.models.TrendsResult;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sunnat629 on 9/29/17.
 */

public class MyTwitterApiClient extends TwitterApiClient {
    public MyTwitterApiClient(TwitterSession session) {
        super(session);
    }

    public GetUsersShowAPICustomService getCustomService() {
        return getService(GetUsersShowAPICustomService.class);
    }
}

interface GetUsersShowAPICustomService {
    @GET("/1.1/users/show.json")
    Call<User> show(@Query("user_id") long userId);

    @GET("/1.1/trends/closest.json")
    Call<List<Location>> closest(@Query("lat") double latitude, @Query("long") double longitude);

    @GET("/1.1/trends/place.json")
    Call<List<TrendsResult>> place(@Query("id") long woeid);

    /*
    * In retrofit v1 you could write like this
    *
    * @GET("/1.1/users/show.json")
    * void show(@Query("user_id") Long userId, Callback<User> cb);
    *
    * */
}
