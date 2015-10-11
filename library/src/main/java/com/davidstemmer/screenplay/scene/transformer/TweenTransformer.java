package com.davidstemmer.screenplay.scene.transformer;

import android.content.Context;
import android.view.animation.Animation;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.scene.Scene;

import static android.view.animation.AnimationUtils.loadAnimation;

/**
 * Created by weefbellington on 10/2/14.
 */
public class TweenTransformer implements Scene.Transformer {

    private final AnimResources params;
    private final Context context;


    public TweenTransformer(Context context, AnimResources params) {
        this.context = context;
        this.params = params;
    }

    @Override
    public void applyAnimations(SceneCut transition) {

        int out = params.getAnimationOut(transition.direction);
        int in = params.getAnimationIn(transition.direction);

        TweenAnimationListener animationListener = new TweenAnimationListener(transition);

        if (in == -1 && out == -1) {
            transition.end();
        }

        if (out != -1) {
            Animation anim = loadAnimation(context, out);
            for (Scene outgoingScene : transition.outgoingScenes) {
                animationListener.addAnimation(anim);
                outgoingScene.getView().startAnimation(anim);
            }
        }
        if (in != -1) {
            Animation anim = loadAnimation(context, in);
            for (Scene incomingScene : transition.incomingScenes) {
                animationListener.addAnimation(anim);
                incomingScene.getView().startAnimation(anim);
            }
        }

    }

}
