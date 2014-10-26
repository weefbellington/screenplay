Screenplay
==========

Screenplay is a companion library to Square's [Flow](http://corner.squareup.com/2014/01/mortar-and-flow.html). Where Flow provides
the essential elements for building a View-based navigation flow (a backstack, simplified view
inflation) Screenplay assembles these into higher-level components for building animated screen
transitions. The core features that Screenplay supports are:

- **Paged** (full-screen) navigation flows
- **Modal** (pop-up) navigation flows
- **Animated** transitionsfor incoming and outgoing views
- **View** **state** **reattachment** for configuration changes

Displaying a screen in Screenplay consists of four discrete phases:

1. A `Scene` creates the view,
2. Scene `Components` receive callbacks and apply behavior
3. The `Rigger` attaches the scene to the layout
4. A `Transformer` plays animations between the incoming and outgoing scene.

These steps are applied by the `Screenplay` object, which implements the `Flow.Listener` interface. The
Screenplay object knows how to reverse these steps when the back button is pressed, and how to
re-attach the screen state after a configuration change (such as rotating the phone from portrait to
landscape mode).

###Setting the stage

App navigation using Screenplay is very straightforward. Begin by creating a `Screenplay.Director`,
which holds the Activity and the scene container. Then construct the `Screenplay` and create a new
`Flow`, and call `Screenplay.enter(flow)` to initialize the screen state:

```java

public class MainActivity extends Activity {
    private final SimpleActivityDirector director = new SimpleActivityDirector();
    private final Screenplay screenplay = new Screenplay(director);
    private final Flow flow = new Flow(Backstack.single(new HomeScreen()), screenplay);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.id.main);
        RelativeLayout container = (RelativeLayout) activity.findViewById(R.id.main);

        director.bind(this, container);
        screenplay.enter(flow);
    }
}
```

Once you've created your Flow, navigation is the same as in any other Flow application:

```java
    flow.goTo(new DetailScene()); // animates forward to the DetailScene
    flow.goUp();                  // animate back to the parent of the scene
    flow.goBack();                // animate back to the previous scene
```

###Anatomy of a Scene

The building block of a Screenplay app is a `Scene`. The Scene knows how to do
only a few things by itself: create a View (`Scene.setUp`), destroy a View (`Scene.tearDown`) and get
the current view (`Scene.getView`).

The standard scene implementation uses Flow's [Layouts.createView()](https://github.com/square/flow/blob/master/flow/src/main/java/flow/Layouts.java)
to set up the scene. You can pass a list of `Component`s to the scene constructor, which can be used
to apply behaviors after the scene is set up and before it is torn down:

```java
@Layout(R.layout.dialog_scene)
public class DialogScene extends StandardScene {
    public DialogScene(DrawerLockingComponent component) {
        super(component);
    }
}
```

```java
public class DrawerLockingComponent implements Scene.Component {

    private final DrawerPresenter drawer;

    public DrawerLockingComponent(DrawerPresenter drawer) {
        this.drawer = drawer;
    }

    @Override
    public void afterSetUp(Context context, Scene scene) {
        drawer.setLocked(true);
    }

    @Override
    public void beforeTearDown(Context context, Scene scene) {
        drawer.setLocked(false);
    }
}

```


###Riggers and Transformers

In a Screenplay app, the application calls `Flow.goTo()` or `Flow.goBack()`, the type of layout
change that is applied depends on the type of ``Screen.Rigger`` that is associated with the next
scene. Screenplay provides two concrete `Rigger` implementations.

- The `PageRigger` manages full-screen layout changes. After all animations complete, the PageRigger
removes the previous screen from its parent layout.
- The `ModalRigger` manages partial-screen layout changes. It does not remove the previous Scene
from the layout, allowing you to layer Scenes on top of each other. This is useful for creating
dialogs, drawers and multi-pane layouts.

Both the `PagedRigger` and the `ModalRigger` remove the Scene at the top of the stack when `Flow.goBack()` is called.

```java
@Layout(R.layout.navigation_drawer)
public class NavigationDrawerScene extends StandardScene {

    private final ModalRigger rigger;
    private final NavigationDrawerTransformer transformer;

    public NavigationDrawerScene() {
        this.rigger = new ModalRigger();
        this.transformer = new NavigationDrawerTransformer();
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

    @Override
    public Transformer getTransformer() {
        return transition;
    }
}
```

A `Transformer` is responsible for applying animations between Stages. The `Transformer` receives
a `SceneCut` object, which contains the data that the `Transformer` needs to create animations,
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

    public HorizontalSlideTransformer(Application context) {
        super(context, params);
    }
}
```

Screenplay provides two `Transformer` implementations to extend from: `TweenTransformer`
and `AnimatorTransformer`. TweenTransformer uses the [Animation](http://developer.android.com/reference/android/view/animation/Animation.html) class, while
the AnimatorTransformer uses the [Animator](http://developer.android.com/reference/android/animation/Animator.html) class.

###Odds and ends

Because the Activity is created and destroyed several times, Screenplay drop the reference to the
old Activity after configuration changes to avoid  memory leak. Using the SimpleActivityDirector,
you should make sure have to call `unbind()` in the `onDestroy()` callback:

```java
    @Override
    public void onDestroy() {
        super.onDestroy()
        if (isFinishing()) {
            director.unbind()
        }
    }
```

The `Screenplay` object also exposes a `getScreenState()` method, which returns a `FlowState` object. This is
useful for preventing multiple button presses while two Scenes are in transition:

```java
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Ignore menu click if stage is transitioning
        if (screenplay.getScreenState() == SceneState.TRANSITIONING) return true;

        switch (item.getItemId()) {
            ...
        }
    }
```

###Mortar support

Screenplay provides support for (but does not require) Square's [Mortar](http://corner.squareup.com/2014/01/mortar-and-flow.html).
It provides two classes, a `MortarActivityPresenter` and a `ScopedScene`, which are designed to
support applications powered by Mortar. See the Javadocs (*coming soon*) for details on their usage.

###That's, all folks!

Screenplay is designed to be simple and extensible. It works well in concert with its friends
Dagger, Flow, Butterknife and Mortar. You can see them all together in the [sample project](https://github.com/weefbellington/screenplay/tree/master/sample/src/main).

Many thanks to the team at Square for their support of the open-source community, without which this project wouldn't be
possible.

Maven artifact is coming soon!