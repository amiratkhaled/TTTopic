package com.example.tttopic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tttopic.models.StorageHelper;
import com.example.tttopic.models.Trend;
import com.example.tttopic.services.GPSTracker;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ShowingTweets extends AppCompatActivity {

    private ListView mTweetsList;

    public ArrayList<Trend> trends;
    Location loc;


    public void setUpTT() {
        GPSTracker gps = new GPSTracker(ShowingTweets.this);
        loc = gps.getLocation();

        double a = loc.getLatitude();
        double b = loc.getLongitude();
        MyTwitterApiClient p = new MyTwitterApiClient(MainActivity.s);
        p.getCustomService().closest(a, b)
                .enqueue(new Callback<List<com.example.tttopic.models.Location>>() {
                    @Override
                    public void success(Result<List<com.example.tttopic.models.Location>> result) {

                        //woeid = ;

                        new MyTwitterApiClient(MainActivity.s).getCustomService().place(((long) result.data.get(0).getWoeid()))
                                .enqueue(new Callback<List<com.example.tttopic.models.TrendsResult>>() {
                                    @Override
                                    public void success(Result<List<com.example.tttopic.models.TrendsResult>> result) {
                                        System.err.println("size is " + result.data.get(0).getTrends().size());

                                        trends = new ArrayList<>(result.data.get(0).getTrends());
                                        CustomAdapter customAdapter = new CustomAdapter();
                                        mTweetsList.setAdapter(customAdapter);
                                        Collections.sort(trends, Collections.reverseOrder());

                                        for (int i = 0; i < trends.size(); i++) {
                                            System.err.println("url " + trends.get(i).getUrl());

                                        }


                                    }

                                    @Override
                                    public void failure(TwitterException exception) {
                                        Log.e("Failed", exception.toString());
                                    }
                                });


                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.e("Failed", exception.toString());
                    }
                });
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return trends.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlayout, null);

            TextView content = (TextView) convertView.findViewById(R.id.content);
            TextView rank = (TextView) convertView.findViewById(R.id.rank);

            content.setText(trends.get(position).getName());
            rank.setText(trends.get(position).getTweetVolume() + " tweets");

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_tweets);


        mTweetsList = (ListView) findViewById(R.id.list);
        //requestLocationPermission();
        //setUpTweetServiceAlarm();
        //setUpNotificationAlarm();
        //setUpViews();

        setUpTT();

        mTweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String url = trends.get(position).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        setUpTweetList();
    }
    /*private void setUpTweetServiceAlarm() {
        // TODO : Only create the alarm if it does not already exist
        Intent getTweetIntent = new Intent(this, PopularTrendingTweetService.class);
        PendingIntent alarmIntent = PendingIntent.getService(this, Auth.Constants.COLLECT_TWEET_CODE, getTweetIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, alarmIntent);
    }

    private void setUpNotificationAlarm() {
        // TODO : Only create the alarm if it does not already exist
        Intent notificationIntent = new Intent(this, NotificationService.class);
        PendingIntent alarmIntent = PendingIntent.getService(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 20);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
    }*/


    private void setUpTweetList() {

        try {

            Date today = Calendar.getInstance().getTime();
            List<Long> tweets = new StorageHelper(ShowingTweets.this).getTweetsOfDay(today);

            if (tweets.isEmpty()) {
                Toast.makeText(ShowingTweets.this, "No tweets to display at this time", Toast.LENGTH_LONG).show();
                return;
            }
            for (int i = 0; i < tweets.size(); i++) {
                System.out.println("twetes " + tweets.get(i));
            }

            /*TweetViewFetchAdapter<CompactTweetView> adapter =
                    new TweetViewFetchAdapter<CompactTweetView>(ShowTweetActivity.this);

            adapter.setTweetIds(tweets, new Callback<List<Tweet>>() {
                @Override
                public void success(Result<List<Tweet>> result) {
                }

                @Override
                public void failure(TwitterException e) {
                    Toast.makeText(ShowTweetActivity.this, "Could not load tweets", Toast.LENGTH_LONG).show();
                }
            });
            mTweetsList.setAdapter(adapter);*/

        } catch (JSONException | IOException e) {
            //Logger.e("TWEET", e.getMessage(), e);
        }
    }
}

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_tweet, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            //Twitter.logOut();
            Intent i = new Intent(this, TwitterLoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestLocationPermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(ShowTweetActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if ((permissionCheck == PackageManager.PERMISSION_GRANTED)) {

            Toast.makeText(this, "Location activated", Toast.LENGTH_SHORT).show();

        } else {

            ActivityCompat.requestPermissions(ShowTweetActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Auth.Constants.LOCATION_PERMISSION_CODE);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case Auth.Constants.LOCATION_PERMISSION_CODE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Location activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Cannot load tweets, need the location of the device.", Toast.LENGTH_LONG).show();
                    finish();
                }

                break;

            default:
                break;
        }
    }}*/
