package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.component.DrawerLockingComponent;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.PopupTransformer;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.davidstemmer.screenplay.scene.rigger.ModalRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

import javax.inject.Inject;

import flow.Layout;

/**
 * Created by weefbellington on 10/2/14.
 */
@Layout(R.layout.dialog_scene)
public class DialogScene extends StandardScene {

    private final PopupTransformer transformer;
    private final ModalRigger rigger;

    @Inject
    public DialogScene() {
        super(new DrawerLockingComponent());
        this.transformer = new PopupTransformer(SampleApplication.getInstance());
        this.rigger = new ModalRigger();
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
