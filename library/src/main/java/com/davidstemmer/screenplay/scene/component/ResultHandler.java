package com.davidstemmer.screenplay.scene.component;

/**
 * Created by weefbellington on 11/10/14.
 */
public class ResultHandler<R> {

    private final R defaultResult;
    private R result;

    public ResultHandler(R defaultResult) {
        this.defaultResult = defaultResult;
        this.result = defaultResult;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public R getResult() {
        return result;
    }

    public void reset() {
        this.result = defaultResult;
    }
}
