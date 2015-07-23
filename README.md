
```
=======================================================================================
+++++++++ SCREENPLAY + A minimalist application framework for Android +++++++++++++++++
=======================================================================================
```

###1. What is Screenplay?

Screenplay is a tiny, moderately opinionated Android application framework. It is designed for building apps with a particular kind of architecture: **single-activity**, with **no fragments**, **no dialogs**, and **small classes**.

It is driven by a few core principles:

1. Low complexity: monolithic UI components with [complex lifecycles](https://github.com/xxv/android-lifecycle) are bad and should be avoided
2. Low friction: objects should be easy to create, and it should be easy to pass data between them
3. High modularity: applications should be built out of small, reusable parts

Screenplay makes it possible to run all of the application code in a single Activity, without relying on Fragments. It provides a number of tools for building lean, simple apps:

**A powerful backstack:**
Screenplay is built on top of Square's [Flow](https://github.com/square/flow). In a Screenplay application, the backstack is built out objects called Scenes. Adding and removing scenes from the backstack happens synchronously, making it easy to see what is happening in the debugger.

**A unifying UI abstraction:**
Both full-screen and modal scenes are first-class citizens in a Screenplay application. Each scene on the backstack has a method, `Scene#isModal`, defines how a it is displayed. Non-modal scenes act like full-screen Activities or Fragments; modal scenes can float on top of other scenes, like a drawer or a dialog.

**View hot swapping:**
Screenplay swaps Views in and out as Scenes are pushed and popped from the backstack. Views are removed from their parent when they are no longer needed to avoid leaking memory.

**Animated scene transitions:**
Screenplay selects animations to play based on the direction of navigation (forward/back) and the state of the scene (incoming/outgoing). Animations can be specified through XML or code.

**Lightweight objects:**
Unlike Activities, Fragments or Dialogs, each scene is a POJO (Plain Old Java Object). No factory methods required: just create `new Scene(...)`, pass it some arguments, and you're good to go. No need to serialize data into a `Bundle`, or write a `Parcelable` implementation. As a result, Screenplay is DI-friendly; [Dagger](https://github.com/square/dagger) is a fun partner.

**Component-oriented architecture:**
Each scene can have zero or more Components, which are notified of scene lifecycle events. Components provide a modular way of attaching behavior to a scene, encouraging code reuse and separation of concerns.

**Separation of display and presentation:**
You don't need to extend any custom `View` subclasses in a Screenplay application. Using Components, Screenplay makes it easy to separate view presentation from display.

###2. Sample Code

The easiest way to get a feel for Screenplay is to dive into the code. Two sample projects are available:

1. The [simple sample project](https://github.com/weefbellington/screenplay/tree/master/sample-simple) is the recommended place to start. Here you will find a minimally complex Screenplay application which showcases some of its features.
2. The [Dagger 2 sample project](https://github.com/weefbellington/screenplay/tree/master/sample-dagger2) is the same application with a DI-oriented structure, if dependency injecton is your cup of tea.

###3. Scenes, Components and Transitions

The Scene is the core of the Screenplay navigation flow. It's pretty dumb: a Scene is mostly responsible for keeping track of a single View, attaching and detaching the view from its parent, and notifying `Scene.Component` objects about certain events. You don't need to put much code into a Scenes -- it's the Components that do the heavy lifting, applying business logic when a scene pushed or popped from the stack.

#####3.1 Navigating between scenes

Screenplay uses Flow to manipulate the app's backstack. The following provide a few examples of manipulating the backstack; refer to the [Flow documentation](http://corner.squareup.com/2014/01/mortar-and-flow.html) for futher information.

```java
    flow.goTo(new DetailScene(data));   // animate forward and add the DetailScene to the stack
    flow.replaceTo(new RootScene())     // animate forward and replace the stack with the RootScene
    flow.goUp();                        // animate back to the parent of the scene
    flow.goBack();                      // animate back to the previous scene
    flow.resetTo(someScene)             // animate back to a specific scene
```

It is recommended that you use a single Flow instance throughout the application. See Section (4.1) for more details.

#####3.2 The scene lifecycle 

As previously noted, the Scene has only a few responsibilities: creating a View (`Scene#setUp`), destroying a View (`Scene#tearDown`) and getting the current view (`Scene#getView`).

A Scene's lifecycle is easy to understand. For an incoming scene, setup happens in three discrete phases:

1. The `Scene` creates its View, which is attached to a parent ViewGroup
2. Scene `Components` are notified of initialization
3. A `Transformer` plays animations between the incoming and outgoing scene.

For an outgoing scene, teardown is the reverse of setup:

1. The `Transformer` plays an animation between the incoming and outgoing scene
2. The `Components` are notified of teardown
3. The `Scene` removes its View, which is detached from the parent ViewGroup

#####3.3 An example scene

The following is an example scene, which extends the `StandardScene` class. If your application is doing the normal thing and inflating views from XML, the StandardScene is the base class you should extend. Internally, the StandardScene uses Flow's [Layouts.createView()](https://github.com/square/flow/blob/master/flow/src/main/java/flow/Layouts.java) to create the View in the `Scene#setUp` method. The `@Layout` annotation defines what view will be inflated by this method.

```java
@Layout(R.layout.dialog_scene)
public class ExampleScene extends StandardScene {
    public DialogScene(DrawerPresenter drawer) {
        addComponent(new DrawerLockingComponent(drawer));
    }
}
```

#####3.4 An example scene component

The scene includes a `DrawerLockingComponent `, which locks the navigation drawer while the dialog is active. Although this particular scene only has a single Component, it is easy to include multiple components in a scene. Ideally, each component should be responsible for a small, well-defined chunk of logic: for example, binding click listeners, making a network call, or creating an animation on the screen.

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

###3.5 Regular vs. modal scenes

Normally, after a new scene is pushed onto the stack, the old scene's View is detached from its parent View to free up memory.

With a modal scene, this is not the case. The old scene's view remains attached to the parent and the new scene's view is layered on top of it. Multiple scenes may be layered in this way. Modal scenes are configured by overriding `Scene#isModal` to return `true`. Here is an example of a modal scene:

```java
@Layout(R.layout.dialog_scene)
public class DialogScene extends StandardScene {

    private final Transformer transformer;

    public DialogScene(Context context) {
        addComponent(new DrawerLockingComponent());
        this.transformer = new PopupTransformer(context);
    }

    @Override
    public boolean isModal() {
        return true;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
```

###3.6 Animated scene transitions

A `Transition` is responsible for applying animations between scenes. The `Transition` receives a `SceneCut` object, which contains the data that the `Transition` needs to create animations, including the `Flow.Direction`, and the incoming and outgoing stages.

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

Screenplay provides two `Transition` implementations to extend from: `TweenTransition` and `AnimatorTransition`. TweenTransition uses the [Animation](http://developer.android.com/reference/android/view/animation/Animation.html) class to create a transition from XML, while the AnimatorTransition uses the [Animator](http://developer.android.com/reference/android/animation/Animator.html) class to create a transition from code.

###4. Boilerplate

#####4.1 Bootstrapping

You only need a little bit of boilerplate to bootstrap a Screenplay application. Screenplay requires you to construct the following objects:

1. The `Stage` object: binds to your activity and main view.
2. The `Rigger` object: acts as a controller for your navigation logic.
3. The `Flow` object: main navigation interface

To ensure that your scene stack survives configuration changes, these objects should be stored outside of your main Activity. One way to do this is to put them in the Application class.

```java
public class SampleApplication extends Application {

    public final Stage stage = new Stage();
    public final Rigger rigger = new Rigger(stage);
    public final Flow flow = new Flow(Backstack.single(new HomeScreen()), screenplay);
    private static SampleApplication application;

    public void onCreate() { application = this; }

    public static SampleApplication getInstance()       { return application; }
    public static Stage getStage()                      { return getInstance().stage; }
    public static Screenplay getRigger()                { return getInstance().rigger; }
    public static Flow getFlow()                        { return getInstance().flow; }
}
```

(alternatively, you can use a dependency injection library such as [Dagger](http://square.github.io/dagger/))

In the onCreate() method of your main Activity, bind your `Stage` to the Activity and call `Rigger.enter()`. This is the main entry point to your application. It will create the root scene, or, in the case of a configuration change, reattach views that were previously visible on the screen.

```java
public class MainActivity extends Activity {

    private SimpleStage stage;
    private Flow flow;
    private Rigger rigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.main);

        stage = SampleApplication.getStage();
        flow = SampleApplication.getMainFlow();
        rigger = SampleApplication.getRigger();

        stage.bind(this, container, flow);
        rigger.enter(flow);
    }
}
```

#####4.2 Handling Activity lifecycle events

When the Activity is destroyed, you must:

1. call `Rigger#exit`. This performs cleanup actions such as calling `Scene#tearDown` on the current visible scene and on its components.
2. call `Stage#unbind`. This drops references to the old Activity and prevents memory leaks.

```java
    @Override
    public void onDestroy() {
        super.onDestroy()
        rigger.exit();
        stage.unbind()
    }
```

#####4.3 Managing configuration changes

The Rigger also handles configuration changes. As long as you hold onto the same `Rigger` instance, it will 'remember' the state of your screen stack, performing steps such as reattaching Views when a configuration change (such as device rotation) occurs.

By default, when a configuration change occurs, Screenplay tears down each the each scene whose view is currently visible on the screen. If instead you would like a scene and its view to be retained on configuration changes, override `Scene.teardownOnConfigurationChanges` to return `false`. Keep in mind that setting this to `false` means that the XML for the view will not be reloaded when a configuration change occurs.

###5. Odds and ends

The `Rigger` object also exposes a `SceneState` object. This is useful for preventing multiple
button presses while two Scenes are in transition:

```java
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Ignore menu click if stage is transitioning
        if (rigger.getSceneState() == SceneState.TRANSITIONING) return true;

        switch (item.getItemId()) {
            ...
        }
    }
```

###6. Download

Screenplay is currently available as a beta snapshot. Grab it via Maven:

```xml
<dependency>
    <groupId>com.davidstemmer</groupId>
    <artifactId>screenplay</artifactId>
    <version>0.6.2-SNAPSHOT</version>
    <type>aar</type>
</dependency>
```

or Gradle:

```groovy
compile 'com.davidstemmer:screenplay:0.6.2-SNAPSHOT'
```

For Gradle, you'll have to add the Sonatype OSS snapshot repo to your build script:

```groovy
repositories {
    maven {
        url "https://oss.sonatype.org/content/repositories/snapshots"
    }
}
```

###7. Contributing
TODO

###8. Acknowledgements

Many thanks to the team at Square for their support of the open-source community, without which this
project wouldn't be possible.
