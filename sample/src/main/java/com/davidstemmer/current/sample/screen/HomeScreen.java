package com.davidstemmer.current.sample.screen;

import com.davidstemmer.current.sample.R;
import com.davidstemmer.current.sample.screen.transformer.HorizontalSlideTransformer;
import com.davidstemmer.current.sample.view.HomeView;
import com.davidstemmer.flowcurrent.screen.Screen;
import com.davidstemmer.flowcurrent.screen.directors.FlowDirector;

import javax.inject.Inject;

import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/2/14.
 */
@Layout(R.layout.home_screen)
public class HomeScreen implements Screen {

    private final FlowDirector director;
    private final HorizontalSlideTransformer transformer;

    @Inject
    public HomeScreen(FlowDirector director, HorizontalSlideTransformer transformer) {
        this.director = director;
        this.transformer = transformer;
    }

    @Override
    public Screen.Director getDirector() {
        return director;
    }

    @Override
    public Screen.Transformer getTransformer() {
        return transformer;
    }

    public static class Presenter extends ViewPresenter<HomeView> {

    }
}
