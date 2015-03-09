package org.kyledef.sittingwearnotify.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import org.kyledef.sittingwearnotify.R;
import org.kyledef.sittingwearnotify.ui.fragments.NavigationDrawerFragment;

public abstract class BaseActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    // SetUpSideBar should be completed in the onCreate
    protected void setUpSideBar(){
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        if (mNavigationDrawerFragment != null)
            mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position){
            case 0: // Home Activity
                launchHome();
                break;
            case 1: // History Activity
                launchHistory();
                break;
            case 2: // Settings Activity
                launchSettings();
                break;
            default:
                launchHome();
                break;
        }
    }

    // http://developer.android.com/training/basics/firstapp/starting-activity.html
    public void launchHome(){
        Toast.makeText(this, "Launching Home Activity", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    public void launchHistory(){
        Toast.makeText(this, "Launching History Activity", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    public void launchSettings(){
        Toast.makeText(this, "Launching Settings Activity", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mNavigationDrawerFragment != null && !mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.base, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            launchSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
