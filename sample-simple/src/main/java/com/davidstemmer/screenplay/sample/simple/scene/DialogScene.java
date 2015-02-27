package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.component.DrawerLockingComponent;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.PopupTransformer;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Layout;

/**
 * Created by weefbellington on 10/2/14.
 */
@Layout(R.layout.dialog_scene)
public class DialogScene extends StandardScene {

    private final PopupTransformer transformer;

    public DialogScene() {
        this.transformer = new PopupTransformer(SampleApplication.getInstance());
        addComponents(new DrawerLockingComponent());
    }

    @Override
    public boolean isStacking() {
        return true;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
