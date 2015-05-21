package com.davidstemmer.screenplay.sample.simple.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.PagedScene1;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 5/21/15.
 */
public class PagedView3 extends RelativeLayout {

    private final Flow flow;

    public PagedView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        flow = SampleApplication.getMainFlow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.back_two_scenes).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                flow.resetTo(new PagedScene1());
            }
        });
    }

}
