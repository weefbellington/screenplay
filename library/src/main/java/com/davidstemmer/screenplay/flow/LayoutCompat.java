package com.davidstemmer.screenplay.flow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import flow.Layout;

/**
 * Created by weefbellington on 9/27/14.
 */
public class LayoutCompat {

    /** Create an instance of the view specified in a {@link flow.Layout} annotation. */
    public static android.view.View createView(Context context, ViewGroup parent, Object screen) {
        return createView(context, parent, screen.getClass());
    }

    /** Create an instance of the view specified in a {@link flow.Layout} annotation. */
    public static android.view.View createView(Context context, Object screen) {
        return createView(context, null, screen.getClass());
    }

    /** Create an instance of the view specified in a {@link flow.Layout} annotation. */
    public static android.view.View createView(Context context, ViewGroup parent, Class<?> screenType) {
        Layout screen = screenType.getAnnotation(Layout.class);
        if (screen == null) {
            throw new IllegalArgumentException(
                    String.format("@%s annotation not found on class %s", Layout.class.getSimpleName(),
                            screenType.getName()));
        }

        int layout = screen.value();
        return inflateLayout(context, parent, layout);
    }

    private static android.view.View inflateLayout(Context context, ViewGroup parent, int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, parent, false);
    }

    private LayoutCompat() {
    }

}
