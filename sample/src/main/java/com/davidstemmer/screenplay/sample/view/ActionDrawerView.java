package com.davidstemmer.screenplay.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.davidstemmer.screenplay.sample.scene.ActionDrawerScene;

import javax.inject.Inject;

import mortar.Mortar;

/**
 * Created by weefbellington on 10/22/14.
 */
public class ActionDrawerView extends LinearLayout {

    @Inject ActionDrawerScene.Presenter presenter;

    public ActionDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;;
        Mortar.inject(context, this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }
}
