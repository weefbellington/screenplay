Screenplay
==========

###Prologue

Screenplay is a minimalist framework for building Android applications, powered by Square's [Flow](http://corner.squareup.com/2014/01/mortar-and-flow.html).
Instead of Fragments and Dialogs, Screenplay provides `Scenes`. In a Screenplay application,
**everything is built from simple Views**, which are associated with Scenes on the backstack.

Transitions between Scenes may be animated, similar to Fragment or Activity transitions. But unlike
Fragments or Activities, Scenes are lightweight objects that do not require any special voodoo to
create. Each scene is just a POJO (Plain Old Java Object). Just create `new Scene(...)`, pass it
some arguments, and you're good to go.

A scene's lifecycle is also easy to understand. A incoming scene is created in three discrete
phases:

1. The `Scene` creates its View, which is attached to a parent ViewGroup
2. Scene `Components` apply initialization behaviors
3. A `Transformer` plays animations between the incoming and outgoing scene.

An outgoing scene is created in a similar way:

1. The `Transformer` signals that the animation is complete
2. The `Components` apply teardown behaviors
3. The `Scene` removes its View, which is detached from the parent ViewGroup

These steps are applied by the `Screenplay` object, which acts as a simple controller for your
navigation logic. It also handles the task of reattaching your views on configuration changes -- as long
as you hold onto the same `Screenplay` object, it will 'remember' the state of your screen stack
across configuration changes.

###Setting the stage

You only need a little bit of boilerplate to configure a Screenplay application. Screenplay requires
you to construct the following objects:

1. The `Screenplay.Director` object: binds to your activity and main view.
2. The `Screenplay` object: acts as a controller for your navigation logic.
3. The `Flow` object: main navigation interface

To ensure that your scene stack survives configuration changes, these objects should be stored
outside of your main Activity. One way to do this is to put them in the Application class.

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

(alternatively, you can use a dependency injection library such as [Dagger](http://square.github.io/dagger/))

In the onCreate() method of your main Activity, bind your `Director` to the Activity and call
`Screenplay.enter(flow)`. This is the main entry point to your application. It will initialize the
Flow to the root scene, or, in the case of a configuration change, rebind your scene scene stack
and reattach views that were previously visible on the screen.

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

Once you've created your Flow, navigation is the same as in any other Flow application:

```java
    flow.goTo(new DetailScene()); // animates forward to the DetailScene
    flow.goUp();                  // animate back to the parent of the scene
    flow.goBack();                // animate back to the previous scene
```

One final detail: when the Activity is destroyed, is important to call `unbind()` on your `Director`
object. This drops references to the old Activity and prevents memory leaks:

```java
    @Override
    public void onDestroy() {
        super.onDestroy()
        director.unbind()
    }
```

(if you're using `MortarActivityDirector`, call `dropView()` instead)

###Anatomy of a Scene

The building block of a Screenplay app is a `Scene`. The Scene knows how to do
only a few things by itself: create a View (`Scene.setUp`), destroy a View (`Scene.tearDown`) and
get the current view (`Scene.getView`).

The reference implementation is the `StandardScene`. This is the scene that your scenes should
extend from if they're being inflated from XML. Internally, it uses Flow's [Layouts.createView()](https://github.com/square/flow/blob/master/flow/src/main/java/flow/Layouts.java)
to create the View. Scenes can be hooked up to `Components`, which receive callbacks after the scene
is set up and before it is torn down. They are used to apply behaviors to the scene. For example,
this DialogScene has a Component that locks the navigation drawer while the dialog is active:

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
    public void beforeTearDown(Context context, Scene scene, boolean isFinishing) {
        drawer.setLocked(false);
    }
}

```

###Regular vs. stacking scenes

The way that the a scene is displayed depends on the whether it is configured to stack or not. Normally,
after a new scene is pushed onto the stack, the old scene's View is detached from its parent so that
its memory can be released.

Stacking scenes work differently. When a stacking scene is created, is will be layered on top of the
scene below it. You can layer as many stacking scenes on top of each other as you want by setting
`Scene.isStacking` to `true`. The following is an example of dialog implemented as a stacking scene:

```java
@Layout(R.layout.dialog_scene)
public class DialogScene extends StandardScene {

    private final PopupTransformer transformer;

    public DialogScene(Context context) {
        addComponent(new DrawerLockingComponent());
        this.transformer = new PopupTransformer(context);
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

###View persistence on configuration changes

By default, when a configuration change occurs, Screenplay tears down each the each scene
whose view is currently visible on the screen. If instead you would like a view  to be retained on
configuration changes, override `Scene.teardownOnConfigurationChanges` to return `true`. Keep in mind, though, that if you enable this flag, the XML for the view will not be
reloaded when a configuration change occurs.

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