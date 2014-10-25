package com.davidstemmer.screenplay.sample.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

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
public class NavigationMenuPresenter extends ViewPresenter<NavigationMenuView> {

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
        showNextSceneAfterDelay(simpleScene);
        drawer.close();
    }

    @OnClick(R.id.nav_item_paged_scenes)
    void pagedScenesClicked() {
        showNextSceneAfterDelay(pagedScene);
        drawer.close();
    }

    @OnClick(R.id.nav_item_modal_scenes)
    void modalScenesClicked() {
        showNextSceneAfterDelay(modalScene);
        drawer.close();
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        ButterKnife.inject(this, (Activity) getView().getContext());
    }

    private final Handler mDrawerHandler = new Handler();
    private void showNextSceneAfterDelay(final Scene nextScene) {
        // Clears any previously posted runnables, for double clicks
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flow.replaceTo(nextScene);
            }
        }, 250);
        // The millisecond delay is arbitrary and was arrived at through trial and error
        drawer.close();
    }
}
