package com.davidstemmer.screenplay.sample.mortar.scene;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.VerticalSlideRigger;
import com.davidstemmer.screenplay.scene.XmlStage;

import javax.inject.Inject;

import flow.Layout;

/**
 * Created by weefbellington on 10/17/14.
 */
@Layout(R.layout.paged_scene_3)
public class PagedStage3 extends XmlStage {

    private final VerticalSlideRigger transformer;

    @Inject
    public PagedStage3(VerticalSlideRigger transformer) {
        this.transformer = transformer;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }
}
