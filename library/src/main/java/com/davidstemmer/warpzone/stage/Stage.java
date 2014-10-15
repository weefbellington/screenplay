package com.davidstemmer.warpzone.stage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.warpzone.WarpPipe;
import com.davidstemmer.warpzone.flow.WarpZone;

/**
 * Created by weefbellington on 10/2/14.
 */
public interface Stage {

    public Director getDirector();
    public Transformer getTransformer();

    public static interface Director {
        public View createView(Context context, ViewGroup parent, Stage stage);
        public View destroyView(Context context, ViewGroup parent, Stage stage);
        public View getView();
        public void layoutIncomingStage(Context context, ViewGroup parent, WarpPipe pipe);
        public void layoutOutgoingStage(Context context, ViewGroup parent, WarpPipe pipe);
    }

    public static interface Transformer {
        public void applyAnimations(WarpPipe pipe, WarpZone listener);
    }

}
