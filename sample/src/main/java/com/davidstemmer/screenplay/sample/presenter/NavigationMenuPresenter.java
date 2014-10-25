package com.davidstemmer.screenplay.sample.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.ModalScene;
import com.davidstemmer.screenplay.sample.scene.PagedScene1;
import com.davidstemmer.screenplay.sample.scene.SimpleScene;
import com.davidstemmer.screenplay.sample.view.NavigationMenuView;
import com.davidstemmer.screenplay.scene.Scene;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/25/14.
 */
public class NavigationMenuPresenter extends ViewPresenter<NavigationMenuView> implements DrawerLayout.DrawerListener {

    private final DrawerPresenter drawer;
    private final Flow flow;
    private final SimpleScene simpleScene;
    private final PagedScene1 pagedScene;
    private final ModalScene modalScene;

    private Scene nextScene;

    @Inject
    public NavigationMenuPresenter(DrawerPresenter drawerPresenter,
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
