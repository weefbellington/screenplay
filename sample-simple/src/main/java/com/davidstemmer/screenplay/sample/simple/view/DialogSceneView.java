package com.davidstemmer.screenplay.sample.simple.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */

public class DialogSceneView extends LinearLayout {

    private final Flow flow;

    public DialogSceneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.flow = SampleApplication.getMainFlow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.ok).setOnClickListener(okListener);
    }

    private final OnClickListener okListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            flow.goBack();
        }
    };

}
