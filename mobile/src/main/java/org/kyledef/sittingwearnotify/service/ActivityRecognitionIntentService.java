package org.kyledef.sittingwearnotify.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

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

        if (prevActivity == -1){
            prevActivity = detectedActivity.getType();
        }else{
            if (prevActivity != detectedActivity.getType()){
                onChangedActivity(prevActivity, detectedActivity.getType());
            }
        }
    }

    protected void onChangedActivity(int previousActivity, int currActivity){
        prevActivity = currActivity;
        Log.d(TAG, "previous: " + getActivityName(previousActivity) + " current: " + getActivityName(currActivity));
    }

    private String getActivityName(int activity){
        switch (activity){
            case DetectedActivity.IN_VEHICLE:
                return "driving";
            case DetectedActivity.ON_BICYCLE:
                return "riding";
            case DetectedActivity.ON_FOOT:
                return "standing";
            case DetectedActivity.STILL:
                return "riding";
            case DetectedActivity.WALKING:
                return "walking";
            case DetectedActivity.RUNNING:
                return "running";
            case DetectedActivity.UNKNOWN:
                return "unknown";
            default:
                return "unknown";
        }
    }
}
