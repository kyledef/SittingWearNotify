package org.kyledef.sittingwearnotify.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import org.kyledef.sittingwearnotify.utils.Constants;

// https://blacode.wordpress.com/2014/12/26/user-activity-recognition-through-new-activityrecognitionapi-in-android-activityrecognitionclient-deprecated/

public class ActivityRecognitionIntentService extends IntentService {

    protected int prevActivity = -1;
    public static final String TAG = "MyActRecognitionService";


    public ActivityRecognitionIntentService() {
        super("ActivityRecognitionIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        DetectedActivity detectedActivity = result.getMostProbableActivity();

        Log.d(TAG, getActivityName(detectedActivity.getType()) + " Detected with " + detectedActivity.getConfidence() + " Confidence");
        pushActivity(getActivityName(detectedActivity.getType()), detectedActivity.getConfidence());


        if (prevActivity != detectedActivity.getType()){
            
            onChangedActivity(prevActivity, detectedActivity.getType(), detectedActivity.getConfidence());
        }

    }

    protected void pushActivity(String currActivity, int confidence){
        Intent intent = new Intent(Constants.BROADCAST_ACTIVITY);
        intent.putExtra(Constants.ACTIVITY,currActivity);
        intent.putExtra(Constants.CONFIDENCE, confidence);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    protected void onChangedActivity(int previousActivity, int currActivity, int confidence){
        prevActivity = currActivity;
        Log.d(TAG, "previous: " + getActivityName(previousActivity) + " current: " + getActivityName(currActivity));

        Intent intent = new Intent(Constants.BROADCAST_ACTIVITY_CHANGE);
        intent.putExtra(Constants.PREV_ACTIVITY, getActivityName(previousActivity))
            .putExtra(Constants.ACTIVITY, getActivityName(currActivity))
            .putExtra(Constants.CONFIDENCE, confidence);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private String getActivityName(int activity){
        switch (activity){
            case DetectedActivity.IN_VEHICLE:
                return Constants.DRIVING;
            case DetectedActivity.ON_BICYCLE:
                return Constants.RIDING;
            case DetectedActivity.ON_FOOT:
                return Constants.STANDING;
            case DetectedActivity.STILL:
                return Constants.SITTING;
            case DetectedActivity.WALKING:
                return Constants.WALKING;
            case DetectedActivity.RUNNING:
                return Constants.RUNNING;
            case DetectedActivity.UNKNOWN:
                return Constants.UNKNOWN;
            default:
                return Constants.UNKNOWN;
        }
    }
}
