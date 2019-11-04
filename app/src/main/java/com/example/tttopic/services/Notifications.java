package com.example.tttopic.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.example.tttopic.R;
import com.example.tttopic.ShowingTweets;

public class Notifications extends IntentService {


    public Notifications() {
        super("Notifier");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.drawable.tw__composer_logo_white);
        builder.setContentTitle("TTTopics");
        builder.setContentText("Y a des neaveaux Tweets à voire !");
        builder.setAutoCancel(true);

        Intent showTweets = new Intent(getApplicationContext(), ShowingTweets.class);
        PendingIntent openActivity = PendingIntent.getActivity(getApplicationContext(), 0, showTweets, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(openActivity);

        NotificationManager notifMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifMgr.notify(30, builder.build());

    }
}
