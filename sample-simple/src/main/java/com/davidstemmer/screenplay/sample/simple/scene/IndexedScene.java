package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.scene.StandardScene;

/**
 * Created by weefbellington on 11/3/14.
 */
public abstract class IndexedScene extends StandardScene {

    public final String id;

    protected IndexedScene(String id) {
        this.id = id;
    }

    protected IndexedScene(String id, Component... components) {
        super(components);
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexedScene that = (IndexedScene) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
