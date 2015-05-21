package com.davidstemmer.screenplay.sample.simple.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.PagedScene2;
import com.davidstemmer.screenplay.sample.simple.scene.PagedScene3;
import com.davidstemmer.screenplay.sample.simple.scene.WelcomeScene;
import com.davidstemmer.screenplay.sample.simple.scene.WelcomeScene2;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/19/14.
 */
public class PagedView1 extends RelativeLayout {

    private final Flow flow;

    public PagedView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.flow = SampleApplication.getMainFlow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flow.goTo(new PagedScene2());
            }
        });
        findViewById(R.id.forward_two_scenes).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            Backstack newBackstack = flow.getBackstack().buildUpon()
                .push(new PagedScene2())
                .push(new PagedScene3())
                .build();
            flow.forward(newBackstack);
            }
        });
        findViewById(R.id.new_backstack).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Backstack newBackstack = Backstack.emptyBuilder()
                        .push(new WelcomeScene())
                        .push(new WelcomeScene2())
                        .build();
                flow.forward(newBackstack);
            }
        });
    }
}
