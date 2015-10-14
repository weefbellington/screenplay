package com.davidstemmer.screenplay.sample.simple;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.davidstemmer.screenplay.flow.ScreenplayDispatcher;
import com.davidstemmer.screenplay.sample.simple.scene.WelcomeScene;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;
import flow.History;


public class MainActivity extends ActionBarActivity {

    private Flow flow;
    private DrawerLayout drawerLayout;
    private DrawerHelper drawerHelper;
    private ScreenplayDispatcher dispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_parent);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.main);

        drawerHelper = SampleApplication.getDrawerHelper();
        drawerHelper.bind(this, drawerLayout);

        flow = SampleApplication.getMainFlow();

        dispatcher = new ScreenplayDispatcher(this, container);
        dispatcher.setUp(flow);

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
            flow.removeDispatcher(dispatcher);
            flow.setHistory(History.single(new WelcomeScene(getApplication())), Flow.Direction.REPLACE);
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

        dispatcher.tearDown(flow);
        drawerHelper.unbind();
    }

}
