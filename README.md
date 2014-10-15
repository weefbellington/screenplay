screenplay
==========

Screenplay is a library for creating single-activity Android applications with animated transitions.
It is a companion library to Flow: navigation between screens is powered by the Flow backstack,
while Screenplay does the work of attaching and detaching views from the layout, animating
cuts between screens, and keeping track of screen state.

##WELCOME TO THE WARP ZONE

Like most Flow applications, an app using Warpzone is based around the concept of a single
Activity. Layouts are built out of simple Views which are added and removed from the screen at
appropriate times. The building block of a Warpzone app is a `Stage`. The Stage interface defines
only two methods. `Stage.getDirector()` returns a `Director` object, in charge of creating a View.
`Scene.getTransformer()` returns a `Transformer` object, which applies animations to the current
between Stages.

The `WarpZone` object controls navigation between scenes. It provides all the familiar navigation
methods of a Flow: goTo(), goBack(), and so on. In addition, the WarpZone object allows you to
switch between flows, using `WarpZone.forkFlow()` and a `WarpWhistle`. A WarpWhistle is a factory for a
[Flow.Listener](www.example.com). Two types of WarpWhistles are provided out-of-the-box: a
`PageFlow.Whistle` and a `ModalFlow.Whistle`.

App navigation using Screenplay is very straightforward. You simply create the WarpZone with a
root context, a layout container, and an initial Stage.

TODO EXAMPLE HERE

From there, you can simply call goTo and goBack to page between Stages:

TODO EXAMPLE HERE

By default, transitions between Scenes are full-screen, and offscreen Views are garbage collected
after the transition completes. However, for instances when a View should be displayed on top of
another view, you can call forkFlow to switch to modal navigation:

TODO EXAMPLE HERE

When you press the back button or call WarpZone.goBack(), Warpzone will exit the modal flow
automatically, returning you to the default paged navigation.

##DIRECTORS: setting the Stage

The Director is a simple interface that is responsible for creating the View that is associated
with a Stage. Warpzone provides a concrete implementation through the SimpleDirector class, which
uses `flow.Layouts.createView()` (see the [Flow documentation](www.example.com) for details).

TODO EXAMPLE HERE

##TRANSFORMERS: plumbing for your animations

A Transformer is responsible for applying animations between Stages. The Transformer receives
a `WarpPipe` object, which contains the data that the Transformer needs to create animations,
including the `Flow.Direction`, and the next (incoming) and previous (outgoing) stages.

Warpzone provides two Transformer implementations to extend from: `TweenTransformer`
and `AnimatorTransformer`. TweenTransformer uses the older [Animation](www.example.com) class, while
