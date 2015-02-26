Screenplay
==========

Screenplay is a minimalist framework for building Android applications. The core features that
Screenplay provides are:

- **a view-centric navigation system**, powered by Square's [Flow](http://corner.squareup.com/2014/01/mortar-and-flow.html).
- **view-based alternatives** to floating fragments, dialogs and drawers, with full backstack support
- **an animation system** for applying transitions between scenes,
- **reusable components** for adding granular behavior to scenes, and
- **view state reattachment** to manage configuration changes.

A typical Screenplay app is constructed from a single Activity and multiple Views. As navigation
events occur, objects called `Scenes` are pushed and popped from the Flow backstack. Each scene
transition consists of three discrete phases:

1. The `Scene` creates a view,
2. Scene `Components` receive callbacks and apply behavior
3. A `Transformer` plays animations between the incoming and outgoing scene.

These steps are applied by the `Screenplay` object, which implements the `Flow.Listener` interface.
Screenplay knows how to reverse these steps when the back button is pressed.

###Setting the stage

Configuring a Screenplay app is straightforward. Create a `Screenplay.Director`, which binds the
Activity. Construct the `Screenplay` object, and create a new `Flow`. In order to ensure
that your Flow survives configuration changes, these objects should be stored outside of your main
Activity. One way to do this is to put them in the Application class:

```java
public class SampleApplication extends Application {

    public final SimpleActivityDirector director = new SimpleActivityDirector();
    public final Screenplay screenplay = new Screenplay(director);
    public final Flow mainFlow = new Flow(Backstack.single(new HomeScreen()), screenplay);
    private static SampleApplication application;

    public void onCreate() { application = this; }

    public static SampleApplication getInstance()       { return application; }
    public static SimpleActivityDirector getDirector()  { return getInstance().director; }
    public static Screenplay getScreenplay()            { return getInstance().screenplay; }
    public static Flow getMainFlow()                    { return getInstance().mainFlow; }
}
```

*(alternatively, use a dependency injection library such as [Dagger](http://square.github.io/dagger/))*

In the onCreate() method of your main Activity, bind your `Director` and call
`Screenplay.enter(flow)` to initialize the screen state:

```java
public class MainActivity extends Activity {

    private SimpleActivityDirector director;
    private Flow flow;
    private Screenplay screenplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.main);

        director = SampleApplication.getDirector();
        flow = SampleApplication.getMainFlow();
        screenplay = SampleApplication.getScreenplay();

        director.bind(this, container);
        screenplay.enter(flow);
    }
}
```

Calling `enter` will initialize the Flow to the current screen, and also re-attach any Views that
were detached during configuration changes.

Once you've created your Flow, navigation is the same as in any other Flow application:

```java
    flow.goTo(new DetailScene()); // animates forward to the DetailScene
    flow.goUp();                  // animate back to the parent of the scene
    flow.goBack();                // animate back to the previous scene
```

One final detail: when the Activity is destroyed, is important to drop references to it to
avoid memory leaks. Using the `SimpleActivityDirector`, you can drop the old Activity reference by
calling `unbind()` in your Activity's `onDestroy()` callback.

```java
    @Override
    public void onDestroy() {
        super.onDestroy()
        director.unbind()
    }
```

If you're using `MortarActivityDirector`, call `dropView()` instead. In either case, the Director
should be rebound the next time that `onCreate()`.

###Anatomy of a Scene

The building block of a Screenplay app is a `Scene`. The Scene knows how to do
only a few things by itself: create a View (`Scene.setUp`), destroy a View (`Scene.tearDown`) and
get the current view (`Scene.getView`).

The standard scene implementation uses Flow's [Layouts.createView()](https://github.com/square/flow/blob/master/flow/src/main/java/flow/Layouts.java)
to set up the scene. You can pass a list of `Component`s to the scene constructor, which can be used
to apply behaviors after the scene is set up and before it is torn down:

```java
@Layout(R.layout.dialog_scene)
public class DialogScene extends StandardScene {
    public DialogScene(DrawerLockingComponent component) {
        addComponent(component);
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


###Regular vs. stacking scenes

In a Screenplay app, when the application calls `Flow.goTo()` or `Flow.goBack()`, the way that the
incoming scene is attached depends on the whether the scene is stacking or not. Each scene has an
`isStacking()` method which specifies this behavior. By default, it is `false`.

Normally, when a new scene is set up, the incoming scene's view is attached to the window and the
outgoing scene's view is detached from the window. By setting `isStacking` to `true`, you specify
that the incoming scene should stack on top of the outgoing scene. This allows you to achieve a
variety of effects, such as floating dialogs, drawers and popovers. The following is an example of
a stacking scene:

```java
@Layout(R.layout.dialog_scene)
public class DialogScene extends StandardScene {

    private final PopupTransformer transformer;
    private final StackRigger rigger;

    public DialogScene() {
        super(new DrawerLockingComponent());
        this.transformer = new PopupTransformer(SampleApplication.getInstance());
    }

    @Override
    public boolean isStacking() {
        return true;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
```

###Transformers and animated scene transitions

A `Transformer` is responsible for applying animations between scenes. The `Transformer` receives a
`SceneCut` object, which contains the data that the `Transformer` needs to create animations,
including the `Flow.Direction`, and the incoming and outgoing stages.

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

The `Screenplay` object also exposes a `SceneState` object. This is useful for preventing multiple
button presses while two Scenes are in transition:

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
It provides two classes, a [MortarActivityDirector](https://github.com/weefbellington/screenplay/blob/master/library/src/main/java/com/davidstemmer/screenplay/MortarActivityDirector.java)
and a [ScopedScene](https://github.com/weefbellington/screenplay/blob/master/library/src/main/java/com/davidstemmer/screenplay/scene/ScopedScene.java),
which are designed to support applications powered by Mortar.

###Download

Screenplay is currently available as a beta snapshot. Grab it via Maven:

```xml
<dependency>
    <groupId>com.davidstemmer</groupId>
    <artifactId>screenplay</artifactId>
    <version>0.6.0-SNAPSHOT</version>
    <type>aar</type>
</dependency>
```

or Gradle:

```groovy
compile 'com.davidstemmer:screenplay:0.6.0-SNAPSHOT'
```

For Gradle, you'll have to add the Sonatype OSS snapshot repo to your build script:

```groovy
repositories {
    maven {
        url "https://oss.sonatype.org/content/repositories/snapshots"
    }
}
```

###Sample Code

Two sample projects are available. The first uses Dagger, Mortar and Butterknife. You can see them
all together in the
[mortar sample project](https://github.com/weefbellington/screenplay/tree/master/sample-mortar).
If you'd rather see a more stripped-down example, there is also a
[simple sample project](https://github.com/weefbellington/screenplay/tree/master/sample-simple)
which only depends on Flow.

Many thanks to the team at Square for their support of the open-source community, without which this
project wouldn't be possible.