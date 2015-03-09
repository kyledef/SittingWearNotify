package org.kyledef.sittingwearnotify.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import org.kyledef.sittingwearnotify.service.ActivityRecognitionIntentService;


public class ActivityApiUtil implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final int REQUEST_CODE = 256;
    public static final int REQUEST_INTERVAL = 500;
    public static final String TAG = "ActivityApiUtil";

    private static ActivityApiUtil instance;
    Context activity;
    GoogleApiClient client;
    private boolean isConnected = false;


    public static ActivityApiUtil getInstance(Context activity){
        if (instance == null)instance = new ActivityApiUtil(activity);
        return instance;
    }

    private ActivityApiUtil(Context activity) {
        this.activity = activity;
        setUpGoogleApiClient();
    }

    public void setUpGoogleApiClient() {
        client = new GoogleApiClient.Builder(activity)
            .addApi(ActivityRecognition.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build();
    }

    public void registerActivityIntent(){
        if (!isConnected){
            client.connect();
            isConnected = true;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
//        Toast.makeText(activity, "Connected to Activity API", Toast.LENGTH_SHORT).show();

        // Use to Test if we can successfully connect to the Service
        Intent i = new Intent(activity, ActivityRecognitionIntentService.class);
        PendingIntent activityPendingIntent = PendingIntent.getService(activity, REQUEST_CODE, i, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(client, REQUEST_INTERVAL, activityPendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {
//        Toast.makeText(activity, "Suspended Activity ", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Connection with API recognized");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Toast.makeText(activity, "Unable to connect " + connectionResult.getErrorCode(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Connection with API Failed");
    }
}