package com.davidstemmer.screenplay.sample.simple;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.davidstemmer.screenplay.MutableStage;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;


public class MainActivity extends ActionBarActivity {

    private MutableStage stage;
    private Flow flow;
    private Screenplay screenplay;
    private DrawerLayout drawerLayout;
    private DrawerHelper drawerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_parent);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.main);

        stage = SampleApplication.getStage();
        flow = SampleApplication.getMainFlow();
        screenplay = SampleApplication.getScreenplay();
        drawerHelper = SampleApplication.getDrawerHelper();

        stage.bind(this, container, flow);
        drawerHelper.bind(drawerLayout);

        flow.setDispatcher(screenplay);
        //screenplay.enter();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerHelper.syncToggleState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerHelper.onConfigurationChanged(newConfig);
    }

    @Override public void onBackPressed() {
        if (!flow.goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            if (drawerHelper.isLockedOpen() || drawerHelper.isLockedShut()) {
               return true;
            }
            else if (drawerHelper.onOptionsItemSelected(item)) {
               return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override public void onDestroy() {
        super.onDestroy();
        //screenplay.exit();
        stage.unbind();
        drawerHelper.unbind();
    }
}
