package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.scene.XmlStage;

/**
 * Flow#resetTo uses Object#equals in order to determine which scene to go back to. In order to
 * avoid having to hold a reference to each scene if we want to go back to it, override
 * Object#equals. Two IndexedScenes are considered equivalent if their id field matches.
 */
public abstract class IndexedStage extends XmlStage {

    public final String id;

    protected IndexedStage(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexedStage that = (IndexedStage) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
