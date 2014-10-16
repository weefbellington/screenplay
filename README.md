Screenplay
==========

**Screenplay** is the artsy cousin to Square's [Flow](www.example.com). Where Flow provides
the basic tools for building a View-based navigation flow -- backstack management, view inflation --
Screenplay is concerned with the narrative details: scene transitions, layout management and a
lifecycle for each.

###Setting the stage

The building block of a Screenplay app is a `Scene`. The Scene knows how to do
only a few things by itself: create a View (`Scene.setUp`), destroy a View (`Scene.tearDown`) and get
the current view (`Scene.getView`). It delegates other tasks to the `Director` and `Transformer`
objects. The Director is responsible for adding Views to the parent layout. The Transformer is
responsible for applying animations between scenes.

App navigation using Screenplay is very straightforward. Begin by creating a `Screenplay` object and
passing it to a Flow:

```java
    HomeScene homeScreen = new HomeScreen();
    RelativeLayout container = (RelativeLayout) activity.findViewById(R.id.main);
    Screenplay screenplay = Screenplay(activity, container);

    Flow flow = new Flow(Backstack.single(homeScreen), screenplay);
    flow.resetTo(homeScreen);
```

Navigation is the same as in any other Flow application, using `Flow.goTo()`, `Flow.goBack()` and
`Flow.goUp()`:

```java
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case android.R.id.home:
            if (isNavigationDrawerOpen()) {
                flow.goBack();
            } else {
                flow.goTo(new NavigationDrawerScene());
            }
            return true;
    }
    return super.onOptionsItemSelected(item);
}
```


The Screenplay object also exposes a `getScreenState()` method, which returns a `FlowState` object. This is
useful for preventing multiple button presses while two Scenes are in transition:

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Ignore menu click if stage is transitioning
        if (screenplay.getScreenState() == SceneState.TRANSITIONING) return true;

        switch (item.getItemId()) {
            ...
        }

    }

###Directors and Transformers

In a Screenplay app, the application calls `Flow.goTo()` or `Flow.goBack()`, the type of layout
change that is applied depends on the type of Screen.Director that is associated with the next
scene. Screenplay provides two concrete `Director` implementations. The `PageDirector` manages
full-screen layout changes. After all animations complete, the PageDirector removes the previous
screen from its parent layout. The `ModalDirector` manages partial-screen layout changes. It does
not remove the previous Scene from the layout, allowing you to layer Scenes on top of each other.
This is useful for creating dialog, drawers and other partial-screen containers that you want to be
added to the backstack. Both the `PagedDirector` and the `ModalDirector` remove the Scene when
`Flow.goBack()` is called.

```java
@Layout(R.layout.navigation_drawer)
public class NavigationDrawerScene extends StandardScene {

    private final ModalDirector director;
    private final NavigationDrawerTransformer transformer;

    @Inject
    public NavigationDrawerScene() {
        this.director = new ModalDirector();
        this.transformer = new NavigationDrawerTransformer();
    }

    @Override
    public Director getDirector() {
        return director;
    }

    @Override
    public Transformer getTransformer() {
        return transition;
    }
}
```

A Transformer is responsible for applying animations between Stages. The Transformer receives
a `SceneCut` object, which contains the data that the Transformer needs to create animations,
including the `Flow.Direction`, and the next (incoming) and previous (outgoing) stages.

```java
@Singleton
public class HorizontalSlideTransformer extends TweenTransformer {

    private static final Params params = new Params();

    static {
        params.forwardIn    = R.anim.slide_in_right;
        params.backIn       = R.anim.slide_in_left;
        params.backOut      = R.anim.slide_out_right;
        params.forwardOut   = R.anim.slide_out_left;
    }

    @Inject
    public HorizontalSlideTransformer(Application context) {
        super(context, params);
    }
}
```

Screenplay provides two Transformer implementations to extend from: `TweenTransformer`
and `AnimatorTransformer`. TweenTransformer uses the older [Animation](www.example.com) class, while
the AnimatorTransformer uses the newer [Animator](www.example.com) class \[note: not yet complete\].

###That's, all folks!

Screenplay is designed to be very simple and extensible. It works well in concert with its friends
Dagger, Flow and Mortar. You can see them all together in the [sample project](www.example.com).

Many thanks to the team at Square for their work on Flow, without which this project wouldn't be
possible.