package com.davidstemmer.screenplay.scene;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import mortar.Blueprint;

/**
 * Created by weefbellington on 2/27/15.
 */
public class UniqueBlueprint implements Blueprint {

    private final String scopeName;
    private final Collection modules;

    /**
     *
     * @param clazz
     * @param modules
     */
    public UniqueBlueprint(Class clazz, Object... modules) {
        this(clazz, Arrays.asList(modules));
    }

    /**
     * Generates a scope name from the class, salting it with a unique UUID.
     * @param clazz
     * @param modules
     */
    public UniqueBlueprint(Class clazz, Collection modules) {
        this(clazz.getName(), modules);
    }

    /**
     * Use this form of the constructor if you want a custom scope name.
     * @param scopeName the scope name
     * @param modules a collection of @Module objects
     */
    public UniqueBlueprint(String scopeName, Collection modules) {
        this.scopeName = scopeName + UUID.randomUUID();
        this.modules = modules;
    }

    @Override
    public final String getMortarScopeName() {
        return scopeName;
    }

    @Override
    public final Object getDaggerModule() {
        return modules;
    }
}
