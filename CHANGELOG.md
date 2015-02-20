#CHANGELOG

##0.6.0

* New features *

- Support for multiple stacked scenes

If there are multiple stacked views (dialogs, drawers, etc.) in a scene, the Screenplay will now
"remember" the state when you navigate to a different scene and restore the state correctly when you
navigate back.

* API breaks *

- The `Rigger` class has been removed. It has been replaced with a boolean flag, `Scene.isStacking`.

##0.5.3

- Begin version tracking