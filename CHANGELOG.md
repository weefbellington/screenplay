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

## 0.5.3 CHANGELOG

- Begin version tracking