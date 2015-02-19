package com.davidstemmer.screenplay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by weefbellington on 1/31/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk=18, manifest="./src/test/AndroidManifest.xml")
public class SimpleTest {

    @Test
    public void shouldFail() {
        assert(true);
    }
}
