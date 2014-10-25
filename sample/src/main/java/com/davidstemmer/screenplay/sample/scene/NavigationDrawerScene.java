package com.davidstemmer.screenplay.sample.scene;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.presenter.DrawerPresenter;
import com.davidstemmer.screenplay.sample.scene.transformer.NavigationDrawerTransformer;
import com.davidstemmer.screenplay.sample.view.NavigationDrawerView;
import com.davidstemmer.screenplay.scene.Scene;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.davidstemmer.screenplay.scene.director.ModalDirector;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/7/14.
 */

@Layout(R.layout.navigation_drawer)
public class NavigationDrawerScene extends StandardScene {

    private final ModalDirector director;
    private final NavigationDrawerTransformer transformer;

    @Inject
    public NavigationDrawerScene(ModalDirector director, NavigationDrawerTransformer transformer) {
        this.director = director;
        this.transformer = transformer;
    }


    @Override
    public Director getDirector() {
        return director;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    public static class Presenter extends ViewPresenter<NavigationDrawerView> implements DrawerLayout.DrawerListener {

        private final DrawerPresenter drawer;
        private final Flow flow;
        private final SimpleScene simpleScene;
        private final PagedScene1 pagedScene;
        private final ModalScene modalScene;

        private Scene nextScene;

        @Inject
        public Presenter(DrawerPresenter drawerPresenter,
                         Flow flow,
                         SimpleScene simpleScene,
                         PagedScene1 pagedScene,
                         ModalScene modalScene) {

            this.drawer = drawerPresenter;
            this.flow = flow;
            this.simpleScene = simpleScene;
            this.pagedScene = pagedScene;
            this.modalScene = modalScene;
        }

        @OnClick(R.id.nav_item_simple_scene)
        void welcomeClicked() {
            nextScene = simpleScene;
            drawer.getLayout().closeDrawer(getView());
        }

        @OnClick(R.id.nav_item_paged_scenes)
        void pagedScenesClicked() {
            nextScene = pagedScene;
            drawer.getLayout().closeDrawer(getView());
        }

        @OnClick(R.id.nav_item_modal_scenes)
        void modalScenesClicked() {
            nextScene = modalScene;
            drawer.getLayout().closeDrawer(getView());
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            ButterKnife.inject(this, (Activity) getView().getContext());
            drawer.getLayout().setDrawerListener(this);
        }

        @Override
        public void onDrawerSlide(View view, float v) {

        }

        @Override
        public void onDrawerOpened(View view) {

        }

        @Override
        public void onDrawerClosed(View view) {
            if (nextScene == null) {
                return;
            }
            if (flow.getBackstack().current().getScreen() != nextScene) {
                flow.resetTo(nextScene);
                nextScene = null;
            }
        }

        @Override
        public void onDrawerStateChanged(int i) {

        }

    }


}
