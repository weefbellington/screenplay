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
import com.davidstemmer.screenplay.sample.simple.scene.StaticScenes;
import com.davidstemmer.screenplay.stage.Stage;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;
import flow.History;

/**
 * Created by weefbellington on 10/17/14.
 */
public class NavigationMenuView extends LinearLayout {

    private final Flow flow;
    private final DrawerHelper drawerHelper;
    private final MenuItemListener showWelcomeScene = new MenuItemListener(StaticScenes.WELCOME_SCENE);
    private final MenuItemListener showSimpleNavigationScene = new MenuItemListener(StaticScenes.SIMPLE_PAGED_SCENE_1);
    private final MenuItemListener showAdvancedNavigationScene = new MenuItemListener(StaticScenes.COMPLEX_PAGED_SCENE_1);
    private final MenuItemListener showModalNavigationScene = new MenuItemListener(StaticScenes.MODAL_VIEWS_SCENE);

    public NavigationMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.flow = SampleApplication.getMainFlow();
        this.drawerHelper = SampleApplication.getDrawerHelper();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.nav_item_welcome_scene).setOnClickListener(showWelcomeScene);
        findViewById(R.id.nav_item_simple_navigation).setOnClickListener(showSimpleNavigationScene);
        findViewById(R.id.nav_item_advanced_navigation).setOnClickListener(showAdvancedNavigationScene);
        findViewById(R.id.nav_item_modal_navigation).setOnClickListener(showModalNavigationScene);
    }

    private int selected = R.id.nav_item_welcome_scene;

    private class MenuItemListener implements View.OnClickListener {

        private Stage target;

        public MenuItemListener(Stage target) {
            this.target = target;
        }

        @Override
        public void onClick(View v) {
            setSelected(v);
            showNextSceneAfterDelay(target);
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
    private void showNextSceneAfterDelay(final Stage nextStage) {
        // Clears any previously posted runnables, for double clicks
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                History newHistory = History.single(nextStage);
                flow.setHistory(newHistory, Flow.Direction.REPLACE);
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
