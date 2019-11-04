package com.example.tttopic.API;

import com.example.tttopic.models.Location;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.params.Geocode;

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
    public  Call<User> show(@Query("user_id") long userId);

    @GET("/1.1/trends/closest.json")
    Call<List<Location>> closest(@Query("lat") long latitude, @Query("long") long longitude);

    @GET("/1.1/search/tweets.json")
    Search tweets(@Query("q") String var1, @Query("geocode") Geocode var2, @Query("lang") String var3, @Query("locale") String var4, @Query("result_type") String var5, @Query("count") Integer var6, @Query("until") String var7, @Query("since_id") Long var8, @Query("max_id") Long var9, @Query("include_entities") Boolean var10);
    /*
    * In retrofit v1 you could write like this
    *
    * @GET("/1.1/users/show.json")
    * void show(@Query("user_id") Long userId, Callback<User> cb);
    *
    * */
}
