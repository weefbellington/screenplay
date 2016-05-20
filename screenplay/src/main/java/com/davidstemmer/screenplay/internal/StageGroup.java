package com.davidstemmer.screenplay.internal;

import com.davidstemmer.screenplay.stage.Stage;
import java.util.ArrayDeque;

public class StageGroup {
    private ArrayDeque<Stage> stages;

    public ArrayDeque<Stage> getStages() {
        return new ArrayDeque<>(stages);
    }

    public Stage getRoot() {
        return stages.getFirst();
    }
}
