package com.davidstemmer.screenplay.sample.simple.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.ActionDrawerResult;
import com.davidstemmer.screenplay.sample.simple.scene.ActionDrawerScene;
import com.davidstemmer.screenplay.sample.simple.scene.DialogScene;
import com.davidstemmer.screenplay.sample.simple.scene.PagedScene1;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class ModalSceneView extends LinearLayout {

    private final Flow flow;

    public ModalSceneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.flow = SampleApplication.getMainFlow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        findViewById(R.id.show_action_drawer).setOnClickListener(new ActionDrawerButtonListener());
        findViewById(R.id.show_dialog).setOnClickListener(new DialogButtonListener());
        findViewById(R.id.show_page).setOnClickListener(new PageSceneListener());
    }

    private class DialogButtonListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            flow.goTo(new DialogScene());
        }
    }

    private class ActionDrawerButtonListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            flow.goTo(new ActionDrawerScene(new ActionDrawerCallback()));
        }
    }

    private class PageSceneListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            flow.goTo(new PagedScene1());
        }
    }

    private class ActionDrawerCallback implements ActionDrawerScene.Callback {
        @Override
        public void onExitScene(ActionDrawerResult result) {
            switch (result) {
                case YES:
                    Toast.makeText(getContext(), "Result is YES", Toast.LENGTH_LONG).show();
                    break;
                case NO:
                    Toast.makeText(getContext(), "Result is NO", Toast.LENGTH_LONG).show();
                    break;
                case CANCELLED:
                    Toast.makeText(getContext(), "Result is CANCELLED", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}