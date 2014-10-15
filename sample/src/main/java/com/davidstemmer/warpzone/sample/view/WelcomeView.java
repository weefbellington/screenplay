package com.davidstemmer.warpzone.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.davidstemmer.warpzone.sample.stage.WelcomeScene;

import javax.inject.Inject;

import mortar.Mortar;

/**
 * Created by weefbellington on 10/2/14.
 */
public class WelcomeView extends RelativeLayout {

    @Inject WelcomeScene.Presenter presenter;

    public WelcomeView(Context context, AttributeSet attrs) {
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
