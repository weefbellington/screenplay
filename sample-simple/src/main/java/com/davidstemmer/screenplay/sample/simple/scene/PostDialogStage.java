package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.HorizontalSlideRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/17/14.
 */
public class PostDialogStage extends IndexedStage {

    private final HorizontalSlideRigger transformer;

    public PostDialogStage() {
        super(PagedStage2.class.getName());
        this.transformer = new HorizontalSlideRigger(SampleApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.post_dialog_scene;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }
}
