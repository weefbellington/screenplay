package com.davidstemmer.screenplay;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import com.davidstemmer.screenplay.scene.rigger.PageRigger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import flow.Flow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by weefbellington on 2/8/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk=18, manifest="./src/main/AndroidManifest.xml")
public class RiggerTests {

    private final PageRigger pageRigger = new PageRigger();

    private RelativeLayout parent;
    private View child;

    @Before
    public void setUp() throws Exception {
        Activity activity = new Activity();
        parent = new RelativeLayout(activity);
        child = new View(activity);
    }

    @Test
    void testLayoutIncomingViewForward() {
        pageRigger.layoutIncoming(parent, child, Flow.Direction.FORWARD);
        assertThat(parent.getChildAt(0), equalTo(child));
    }

    @Test
    void testLayoutIncomingViewBackward() {
        pageRigger.layoutIncoming(parent, child, Flow.Direction.BACKWARD);
        assertThat(parent.getChildAt(0), equalTo(child));
    }

    @Test
    void testLayoutOutgoingViewForward() {
        parent.addView(child);
        pageRigger.layoutOutgoing(parent, child, Flow.Direction.FORWARD);
        assertThat(parent.getChildAt(0), is(nullValue()));
    }
}
