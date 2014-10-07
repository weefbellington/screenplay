package com.davidstemmer.screenplay.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.davidstemmer.screenplay.sample.scene.HomeScene;

import javax.inject.Inject;

import mortar.Mortar;

/**
 * Created by weefbellington on 10/2/14.
 */
public class HomeView extends LinearLayout{

    @Inject HomeScene.Presenter presenter;

    public HomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
