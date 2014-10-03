package com.davidstemmer.current.sample.screen;

import android.os.Bundle;
import android.view.View;

import com.davidstemmer.current.sample.R;
import com.davidstemmer.current.sample.screen.transformer.PopupTransformer;
import com.davidstemmer.flowcurrent.Current;
import com.davidstemmer.flowcurrent.screen.Screen;
import com.davidstemmer.flowcurrent.screen.directors.FlowDirector;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/2/14.
 */
@Layout(R.layout.popup_screen)
public class PopupScreen implements Screen {

    @Inject PopupTransformer transformer;
    @Inject FlowDirector director;

    @Override
    public Director getDirector() {
        return director;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    @Singleton
    public static class Presenter extends ViewPresenter<View> {

        @Inject Current current;

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            ButterKnife.inject(this, getView());
            super.onLoad(savedInstanceState);
        }

        @OnClick(R.id.ok) void dismiss() {
            current.goBack();
        }
    }
}
