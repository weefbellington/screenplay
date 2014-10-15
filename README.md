screenplay
==========

Screenplay is a library for creating single-activity Android applications with animated transitions.
It is a companion library to Flow: navigation between screens is powered by the Flow backstack,
while Screenplay does the work of attaching and detaching views from the layout, animating
cuts between screens, and keeping track of screen state.

###Setting the stage

Like most Flow applications, an app using Screenplay is based around the concept of a single
Activity. Layouts are built out of simple Views which are added and removed from the screen at
appropriate times. The building block of a Screenplay app is a `Scene`. The Scene knows how to do
only a few things by itself: create a View (`Scene.setUp`), destroy a View (`Scene.tearDown`) and get
the current view (`Scene.getView`). It delegates other tasks to the `Director` and `Transformer`
objects. The Director is responsible for adding Views to the parent layout. The Transformer is
responsible for applying animations between scenes.

App navigation using Screenplay is very straightforward. Begin by create a Screenplay object and
pass it to a Flow:

TODO EXAMPLE HERE

Back and up button handling is the same as in any other Flow application:

TODO EXAMPLE HERE

The Screenplay object also exposes a getState() method, which returns a FlowState object. This is
useful for preventing multiple button presses while two Scenes are in transition:

TODO EXAMPLE HERE

###Directors and Transformers

When the application calls flow.goTo() or Flow.goBack(), the type of layout change that is applied
depends on the type of Screen.Director that is associated with the next scene. Screenplay provides
two concrete Director implementations. The `PageDirector` manages full-screen layout changes.
After all animations complete, the PageDirector removes the previous screen from its parent layout.
The `ModalDirector` manages partial-screen layout changes. It does not remove the previous Scene
from the layout, allowing you to layer Scenes on top of each other. This is useful for creating
dialog, drawers and other partial-screen containers that you want to be added to the backstack.
Both the PagedDirector and the ModalDirector remove the Scene when Flow.goBack() is called.

TODO EXAMPLE HERE

A Transformer is responsible for applying animations between Stages. The Transformer receives
a `SceneCut` object, which contains the data that the Transformer needs to create animations,
including the `Flow.Direction`, and the next (incoming) and previous (outgoing) stages.

Screenplay provides two Transformer implementations to extend from: `TweenTransformer`
and `AnimatorTransformer`. TweenTransformer uses the older [Animation](www.example.com) class, while
the AnimatorTransformer uses the newer [Animator](www.example.com) class \[note: not yet complete\].

###That's, all folks!

Screenplay is designed to be very simple and extensible. It works well in concert with its friends
Dagger, Flow and Mortar. You can see them all together in the [sample project](www.example.com).

Many thanks to the team at Square for their work on Flow, without which this project wouldn't be
possible.