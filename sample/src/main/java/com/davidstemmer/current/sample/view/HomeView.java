package com.davidstemmer.current.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import mortar.Mortar;

/**
 * Created by weefbellington on 10/2/14.
 */
public class HomeView extends LinearLayout{

    public HomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Mortar.inject(context, this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
