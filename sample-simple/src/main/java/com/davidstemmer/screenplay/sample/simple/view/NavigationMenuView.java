package com.davidstemmer.screenplay.sample.simple.view;

import android.content.Context;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davidstemmer.screenplay.sample.simple.DrawerHelper;
import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.StackedScene;
import com.davidstemmer.screenplay.sample.simple.scene.PagedScene1;
import com.davidstemmer.screenplay.sample.simple.scene.WelcomeScene;
import com.davidstemmer.screenplay.scene.Scene;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/17/14.
 */
public class NavigationMenuView extends LinearLayout {

    private final Flow flow;
    private final DrawerHelper drawerHelper;
    private final WelcomeScene welcomeScene;
    private final PagedScene1 pagedScene;
    private final StackedScene stackedScene;

    public NavigationMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.flow = SampleApplication.getMainFlow();
        this.drawerHelper = SampleApplication.getDrawerHelper();
        this.welcomeScene = new WelcomeScene();
        this.pagedScene = new PagedScene1();
        this.stackedScene = new StackedScene();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.nav_item_simple_scene).setOnClickListener(new WelcomeSceneListener());
        findViewById(R.id.nav_item_paged_scenes).setOnClickListener(new PagedSceneListener());
        findViewById(R.id.nav_item_modal_scenes).setOnClickListener(new ModalSceneListener());
    }

    private int selected = R.id.nav_item_simple_scene;

    private class WelcomeSceneListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            setSelected(v);
            showNextSceneAfterDelay(welcomeScene);
            drawerHelper.close();
        }
    }


    private class PagedSceneListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            setSelected(v);
            showNextSceneAfterDelay(pagedScene);
            drawerHelper.close();
        }
    }

    private class ModalSceneListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            setSelected(v);
            showNextSceneAfterDelay(stackedScene);
            drawerHelper.close();
        }
    }


    private void setSelected(View selectedView) {
        setSelected(selectedView.getId());
    }

    private void setSelected(int id) {
        selected = id;
        for (int i = 0; i < getChildCount(); i++) {
            TextView child = (TextView) getChildAt(i);
            if (id == child.getId()) {
                child.setSelected(true);
            } else {
                child.setSelected(false);
            }
        }
    }

    private final Handler mDrawerHandler = new Handler();
    private void showNextSceneAfterDelay(final Scene nextScene) {
        // Clears any previously posted runnables, for double clicks
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flow.replaceTo(nextScene);
            }
        }, 250);
        // The millisecond delay is arbitrary and was arrived at through trial and error
        drawerHelper.close();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setSelected(selected);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);

        savedState.selected = this.selected;
        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState savedState = (SavedState)state;
        super.onRestoreInstanceState(savedState.getSuperState());

        this.selected = savedState.selected;
    }


    private static class SavedState extends BaseSavedState {

        private int selected;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.selected = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.selected);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
            new Parcelable.Creator<SavedState>() {
                public SavedState createFromParcel(Parcel in) {
                    return new SavedState(in);
                }
                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
    }
}
