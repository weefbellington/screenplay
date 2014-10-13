screenplay
==========

Screenplay is a library for creating single-page Android applications with animated transitions.
It is a companion library to Flow: navigation between screens is powered by the Flow backstack,
while Screenplay does the work of attaching and detaching views from the layout, animating
transitions between screens, and keeping track of screen state. Multiple navigation modes are
supported, making it it possible to shift fluidly between full-screen and modal navigation while
maintaining consistent back button behavior.

###Setting the stage: Scenes and the Screenplay

The building block of a Screenplay application is a `Scene`. The `Scene` interface defines only
two methods. `Scene.getDirector()` returns a `Director` object, in charge of attaching
and detaching the View from its container. `Scene.getTransformer()` returns a `Transformer` object,
which applies animations that play when the scene is transitioning in or out.

The `Screenplay` object controls navigation between scenes. It provides all the familiar navigation
methods of a `Flow`: goForward(), goBack(), and so on. However, a Screenplay also allows you to
switch between flows, using `Screenplay.changeFlow()`. The `changeFlow` method creates a new Flow
with a separate `Flow.Listener`, enabling different kinds of navigation behavior while maintaining
a unified backstack.

App navigation using Screenplay is very straightforward. You simply create a Screenplay with a
root context and a layout container, and create the initial Flow:

EXAMPLE HERE

From there, you can simply call goForward and goBack to page between screens:

EXAMPLE HERE

By default, transitions between Scenes are full-screen, and offscreen Views are garbage collected
after the transition completes. However, for instances when a View should be displayed on top of
another view, you can call changeFlow to switch to modal navigation:

EXAMPLE HERE

When you press the back button or call Screenplay.goBack(), Screenplay will exit the modal flow
automatically, returning you to the default paged navigation.

###The Director: binding your Scene together

TODO

###Transformers: Not a big truck