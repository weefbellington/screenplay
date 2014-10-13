package com.davidstemmer.warpzone.stage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.warpzone.WarpListener;
import com.davidstemmer.warpzone.WarpPipe;

/**
 * Created by weefbellington on 10/2/14.
 */
public interface Stage {

    public Director getDirector();
    public Transformer getTransformer();

    public static interface Director {
        public View create(Context context, Object screen, ViewGroup parent);
        public View destroy(Context context, Object screen, ViewGroup parent);
        public View getView();
    }

    public static interface Transformer {
        public void transform(WarpPipe pipe, WarpListener listener);
    }

}
