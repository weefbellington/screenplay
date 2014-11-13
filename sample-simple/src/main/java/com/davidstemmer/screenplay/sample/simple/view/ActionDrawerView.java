package com.davidstemmer.screenplay.sample.simple.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.ActionDrawerResult;
import com.davidstemmer.screenplay.scene.component.ResultHandler;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/22/14.
 */
public class ActionDrawerView extends LinearLayout {

    private final Flow flow;
    private ResultHandler<ActionDrawerResult> resultHandler;

    public ActionDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.flow = SampleApplication.getMainFlow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.yes).setOnClickListener(yesListener);
        findViewById(R.id.no).setOnClickListener(noListener);
    }

    public void bind(ResultHandler<ActionDrawerResult> resultHandler) {
        this.resultHandler = resultHandler;
    }


    private final OnClickListener yesListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            resultHandler.setResult(ActionDrawerResult.YES);
            flow.goBack();
        }
    };

    private final OnClickListener noListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            resultHandler.setResult(ActionDrawerResult.NO);
            flow.goBack();
        }
    };
}
