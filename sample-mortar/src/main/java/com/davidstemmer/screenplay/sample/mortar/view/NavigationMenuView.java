package com.davidstemmer.screenplay.sample.mortar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.davidstemmer.screenplay.sample.mortar.presenter.NavigationMenuPresenter;

import javax.inject.Inject;

import mortar.Mortar;

/**
 * Created by weefbellington on 10/17/14.
 */
public class NavigationMenuView extends LinearLayout {

    @Inject NavigationMenuPresenter presenter;

    public NavigationMenuView(Context context, AttributeSet attrs) {
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
