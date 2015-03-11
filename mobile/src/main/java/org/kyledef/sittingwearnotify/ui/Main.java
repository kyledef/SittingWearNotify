package org.kyledef.sittingwearnotify.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.kyledef.sittingwearnotify.R;
import org.kyledef.sittingwearnotify.receivers.ActivityChangeReceiver;
import org.kyledef.sittingwearnotify.utils.ActivityApiUtil;
import org.kyledef.sittingwearnotify.utils.ActivityChangeListener;
import org.kyledef.sittingwearnotify.utils.Constants;


public class Main extends BaseActivity implements ActivityChangeListener{

    private ActivityChangeReceiver activityReceiver;
    private TextView resultView;
    private ImageView resultImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultView = ((TextView)findViewById(R.id.activity_status));
        resultImage = ((ImageView)findViewById(R.id.activity_status_image));
        setUpSideBar();
        startActivityService();
        getActivityResult();
    }

    private void getActivityResult() {
        activityReceiver = new ActivityChangeReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_ACTIVITY);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(activityReceiver, intentFilter);

    }

    public void startActivityService(){
        ActivityApiUtil.getInstance(this).registerActivityIntent();
    }

    @Override
    protected void onDestroy(){
        if (activityReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(activityReceiver);
        super.onDestroy();
    }

    @Override
    public void onActivityChange(final String activity, final int confidence) {

        resultView.post(new Runnable() {
            @Override
            public void run() {
                resultView.setText("Activity: " + activity + " with a confidence of " + confidence);
                changeActivityImage(activity);
            }
        });
    }

    public void changeActivityImage(String activity){
        switch(activity){
            case Constants.STANDING:
                resultImage.setImageDrawable(getResources().getDrawable(R.drawable.standing));
                break;
            case Constants.WALKING:
                resultImage.setImageDrawable(getResources().getDrawable(R.drawable.walking));
                break;
            case Constants.SITTING:
                resultImage.setImageDrawable(getResources().getDrawable(R.drawable.sitting));
                break;
            case Constants.RUNNING:
                resultImage.setImageDrawable(getResources().getDrawable(R.drawable.running));
                break;
            default:
                resultImage.setImageDrawable(getResources().getDrawable(R.drawable.question_mark));
        }
    }
}
