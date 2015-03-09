package org.kyledef.sittingwearnotify.ui;

import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;

import org.kyledef.sittingwearnotify.R;
import org.kyledef.sittingwearnotify.utils.ActivityApiUtil;


public class Main extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpSideBar();
        startActivityService();
    }

    public void startActivityService(){
        ActivityApiUtil.getInstance(this).registerActivityIntent();
    }

}
