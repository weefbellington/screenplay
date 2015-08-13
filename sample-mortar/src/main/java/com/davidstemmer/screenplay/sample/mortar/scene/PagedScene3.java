package com.davidstemmer.screenplay.sample.mortar.scene;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.VerticalSlideTransformer;
import com.davidstemmer.screenplay.scene.XmlScene;

import javax.inject.Inject;

import flow.Layout;

/**
 * Created by weefbellington on 10/17/14.
 */
@Layout(R.layout.paged_scene_3)
public class PagedScene3 extends XmlScene {

    private final VerticalSlideTransformer transformer;

    @Inject
    public PagedScene3(VerticalSlideTransformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
