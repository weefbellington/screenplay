package com.davidstemmer.screenplay.sample.simple.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeRigger;
import com.davidstemmer.screenplay.scene.Stage;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/17/14.
 */

public class PagedStage1 extends IndexedStage {

    private final Flow flow;
    private final CrossfadeRigger transformer;

    public PagedStage1() {
        super(PagedStage1.class.getName());
        this.flow = SampleApplication.getMainFlow();
        this.transformer = new CrossfadeRigger(SampleApplication.getInstance());

        addComponents(new ClickBindingComponent());
    }

    @Override
    public int getLayoutId() {
        return R.layout.paged_scene_1;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }

    private class ClickBindingComponent implements Component {
        @Override
        public void afterSetUp(Stage stage, boolean isInitializing) {
            View parent = stage.getView();
            View nextButton = parent.findViewById(R.id.next);
            nextButton.setOnClickListener(showNextScene);
        }

        @Override
        public void beforeTearDown(Stage stage, boolean isFinishing) {

        }

        private View.OnClickListener showNextScene = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flow.set(StaticScenes.SIMPLE_PAGED_SCENE_2);
            }
        };
    }
}
