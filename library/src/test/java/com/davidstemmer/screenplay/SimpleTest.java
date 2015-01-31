package com.davidstemmer.screenplay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by weefbellington on 1/31/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest="./src/main/AndroidManifest.xml")
public class SimpleTest {

    @Test
    public void shouldFail() {
        assert(false);
    }
}
