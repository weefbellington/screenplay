## 0.6.4 CHANGELOG

Fix bug in which the initial scene's `tearDown` method was never being called (issue #91)

## 0.6.3 CHANGELOG

- throw an exception when trying to add a scene to the backstack twice (issue #56)
- add support for methods that add or remove multiple: `Flow#forward`, `Flow#backward` and `Flow#resetTo` now work correctly (issue #84)
- add workaround for `Attempt to read from field 'int android.view.View.mViewFlags' on a null object reference` crash (issue #88)

## 0.6.2 CHANGELOG

*Bug fixes*

Fix issue where not all views were being removed when `replaceTo` was called with a stacking scene
showing.

## 0.6.1 CHANGELOG

**New features**

*Helper methods*

Added two helper methods, `Screenplay.isSceneAttached` and `Screenplay.isSceneInBackstack`, for
querying whether a scene is already in the backstack.

**Bug fixes**

`Scene.tearDown` and `Component.tearDown` will now be called on scenes that remain in the backstack
when the activity is destroyed (see item #2, below)

**API breaks**

1. The `Director` object is now called `Stage`. There is a `MutableStage` class and an
`ImmutableStage`. The `MutableStage` can be bound to a new activity but the `ImmutableStage` cannot
-- its fields are final. The `ImmutableStage` is intended to be used with DI frameworks such
as Dagger 2 which can recreate the object graph and its dependencies when the main Activity is
destroyed.
2. A new method, `Screenplay.exit`, has been added. This must be called in `Activity.onDestroy`. It
triggers teardown callbacks on scenes that remain on the backstack.

## 0.6.0 CHANGELOG

**New features**

*Support for multiple stacked scenes*

If there are multiple stacked views (dialogs, drawers, etc.) in a scene, the Screenplay will now
"remember" the state when you navigate to a different scene and restore the state correctly when you
navigate back.

*Persistent scopes*

The `ScopedScene` now has a persistent scope that is only destroyed when it is popped off of the
stack.

*Better handling of configuration changes*

Scenes now have a `Scene.teardownOnConfigurationChanges` method (true by default). If true, the
Scene's `tearDown` and `setUp` methods will be called when a configuration change occurs. This is
useful if you want to load a different XML resource on configuration changes.

**API breaks**

1. The `Rigger` class has been removed. It has been replaced with a boolean flag, `Scene.isStacking`.
2. The `ScopedScene` now creates a scope in the constructor, instead of in `Scene.setUp`. This
avoids the need to do 'late injection` on the Scene. Injected fields are available immediately and
use the scene's scope, not the parent scope.
3. The `Scene.setUp` method now takes a `Flow.Direction` as a parameter. This is intended to support
tearing down the `ScopedScene` when it is popped off of the stack.
4. The `afterSetUp` and `beforeTearDown` callbacks on the `Component` no longer take a View as an
argument as this was redundant. Access the view with `Scene.getView`.
5. The `Scene.tearDown` and `Component.beforeTearDown` methods now have a boolean parameter,
`isFinishing`. This is `true` if the scene is being popped off of the stack, `false` otherwise.
5. The `Scene.setUp` and `Component.afterSetUp` methods now have a boolean parameter,
`isInitializing`. This is `true` if the scene is being pushed onto the stack, `false` otherwise.
6. Components are no longer passed through the scene's constructor. use the `addComponents()` method
instead.
7. Components no longer pass a context argument. Use `Scene.getView.getContext` instead.
8. `ScopedScene` changed to `MortarScopedScene`


## 0.5.3 CHANGELOG

- Begin version tracking
