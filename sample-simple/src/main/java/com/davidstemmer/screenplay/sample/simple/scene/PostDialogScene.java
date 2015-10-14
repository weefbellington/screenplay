package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.HorizontalSlideTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/17/14.
 */
public class PostDialogScene extends IndexedScene {

    private final HorizontalSlideTransformer transformer;

    public PostDialogScene() {
        super(PagedScene2.class.getName());
        this.transformer = new HorizontalSlideTransformer(SampleApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.post_dialog_scene;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
