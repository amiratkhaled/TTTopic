package com.example.tttopic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tttopic.services.GPSTracker;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;


public class MainActivity extends AppCompatActivity {

    Button btnShowLocation;
    GPSTracker gps;

    static public TwitterSession s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);

        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
        }, 10);

        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        s = session;
        System.err.println("khaled session is "+ s.getUserName());
        if (session == null) {
            // no session => login
            startActivity(new Intent(this, LoginActivity.class));
            //System.err.println("khaled session is "+ session.getUserName());
        } else {
            // user logged-in => show tweet
            startActivity(new Intent(this, ShowingTweets.class));
            System.err.println("khaled session is "+ session.getUserName());
        }
        //finish();

        /*gps = new GPSTracker(MainActivity.this);
        btnShowLocation = (Button) findViewById(R.id.button);
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // create class object
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                //gps.getLocation(MainActivity.this,gps);

            }
        });*/

    }
}
