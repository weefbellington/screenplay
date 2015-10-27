
```
===================================================================================
+++++ SCREENPLAY + A minimalist, View-based application framework for Android +++++
===================================================================================
```

[![Join the chat at https://gitter.im/weefbellington/screenplay](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/weefbellington/screenplay?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

*Questions or just want to say hello? Try our real-time discussion channel on Gitter!*

###Introduction

#####What is Screenplay?

Screenplay is a tiny, moderately opinionated Android application framework. It is designed for building View-based apps with a particular kind of architecture: **single-activity**, with **no fragments**, **no dialogs**, and **small classes**.

#####Wait, what's a View-based application?

When people talk about View-based Android applications, they usually mean an application that doesn't use multiple Activities or Fragments. Instead, it uses few Activities -- often just one -- and creates screen transition effects by swapping views on and off the screen. Many in the Android community have lobbied [in favor of View-based applications](https://corner.squareup.com/2014/10/advocating-against-android-fragments.html). The idea is that by programming directly with Views, you can avoid a lot of the complexity that is associated with the Activity/Fragment lifecycle.

#####What's the catch?

The downside of View-based development is that there's a lot of things you no longer get for "free". The backstack is one thing that you leave behind. Other parts that you need to manage yourself include attaching and detaching views from their container and animating transitions between Views. There's a surprisingly large amount of bookkeeping associated with these tasks.

#####Hasn't somebody already figured out this stuff?

Libraries like [Flow](https://github.com/square/flow) have been created to address the problem of the missing backstack. Screenplay is designed to handle the rest of the equation.

#####What's the rest of the equation?

I'm glad you asked! The Screenplay manages a number of things for you:

1. It acts as a controller for your Views, managing View creation and automatically detaching Views from the screen when they are no longer needed.
2. It also animates between Views during screen transitions
3. It and provides a component-oriented interface which applies behavior to screens. The modular architecture encourages separation of View display from presentation.

###2. Core principles

Screenplay is driven by a few core principles:

1. Low complexity: monolithic UI components with [complex lifecycles](https://github.com/xxv/android-lifecycle) are bad and should be avoided.
2. Low friction: objects should be easy to create, and it should be easy to pass data between them.
3. High modularity: applications should be built out of small, reusable parts.

Screenplay makes it possible to run all of the application code in a single Activity, without relying on Fragments. It provides a number of tools for building lean, simple apps:

**A unifying UI abstraction:**
The building block of a screenplay application is the Stage. Each Stage is associated with single View. Simple screens consist of a single Stage. In more complex scenarios, modal stages can be used to create effects like dialogs, drawers, panels, etc.

**Lightweight objects:**
Unlike Activities, Fragments or Dialogs, each Stage is a POJO (Plain Old Java Object). There are no factory methods, and no need to serialize data into a `Bundle`, or write a `Parcelable` implementation just to pass data between screens.  Just create `new Stage(...)`, pass it some arguments, and you're good to go. As a result, Screenplay is DI-friendly; [Dagger](https://github.com/square/dagger) is a fun partner.

**View hot swapping:**
Screenplay swaps Views in and out as Stages are pushed and popped from the backstack. Views are removed from their parent when they are no longer needed to avoid leaking memory.

**Animated transitions:**
Screenplay selects animations to play based on the direction of navigation (forward/back) and the state of the Stage (incoming/outgoing). Animations can be specified through XML or code.

**Component-oriented architecture:**
Each Stage can be augmented with Components, which are notified of lifecycle events. Components provide a modular way of attaching behavior to a Stage, encouraging code reuse and separation of concerns.

**Separation of display and presentation:**
You don't need to put any business logic in `View` subclasses in a Screenplay application. Screenplay's powerful component-oriented architecture makes it easy to separate view presentation from display.

**Plugin support:**
Screenplay includes optional support for [Flow](https://github.com/square/flow) which is provided as a separate module. Flow provides an interface for managing the backstack, including pushing and popping new Stages from the stack, managing the history, etc.

###3. Sample Code

The easiest way to get a feel for Screenplay is to dive into the code, and the [sample project](https://github.com/weefbellington/screenplay/tree/master/sample-simple) is the recommended place to start. Here you will find a minimally complex Screenplay application which showcases some of its features.

###4. Stages, Components and Transitions

The Stage is the core of the Screenplay navigation flow. Its responsibilities are very narrow: a Stage's main role is detaching and attaching view from its parent. Stages are lightweight and shouldn't include a lot of code -- it's the Components that do the heavy lifting, applying business logic when a Stage pushed or popped from the stack.

#####4.1 Navigating between Stages

Screenplay doesn't manage the backstack itself; instead it delegates this responsibility to tested 3rd party libraries. The [Flow plugin](https://github.com/weefbellington/screenplay/tree/flow-plugin-library/flow-plugin) is the current officially supported method. See Section (7.2) for details.

The following provide a few examples of manipulating the backstack using Flow. Refer to the [Flow documentation](http://corner.squareup.com/2014/01/mortar-and-flow.html) for futher information.

```java
    flow.set(new DetailStage(data), Flow.Direction.FORWARD); // add a stage
    flow.set(new RootStage(), Flow.Direction.REPLACE);       // add a stage, make it the root
    flow.set(existingStage, Flow.Direction.BACK);            // pop to a stage
    flow.goBack();                                           // go back one stage
```

It is recommended that you use a single Flow instance throughout the application. See Section (5.1) for more details.

It's entirely possible to manage the backstack without using Flow, or to write your own navigation plugin. A call to `Screenplay#dispatch` will trigger a screenplay navigation event; see the (ScreenplayDispatcher)[https://github.com/weefbellington/screenplay/blob/flow-plugin-library/flow-plugin/src/main/java/com/davidstemmer/flow/plugin/screenplay/ScreenplayDispatcher.java] class in the flow-plugin library for an example.

#####4.2 The Stage lifecycle 

The Stage has only a few responsibilities: creating a View (`Stage#setUp`), destroying a View (`Stage#tearDown`) and getting the current view (`Stage#getView`).

A Stage's lifecycle is easy to understand. For an incoming Stage, setup happens in three discrete phases:

1. The `Stage` creates its View, which is attached to a parent ViewGroup
2. Each `Stage.Component` is notified of initialization
3. A `Stage.Rigger` plays animations between the incoming and outgoing Stage.

For an outgoing Stage, teardown is the reverse of setup:

1. A `Stage.Rigger` plays an animation between the incoming and outgoing Stage
2. Each `Stage.Component` is notified of teardown
3. The `Stage` removes its View, which is detached from the parent ViewGroup

#####4.3 An example Stage

The following is an example Stage, which extends the `XmlStage` class. It provides a layout ID, which is used to inflate the View. It also provides a `Rigger`, which is used to animate the transition between Stages.

```java
public class ExampleStage extends XmlStage {

    private final Rigger rigger = new CrossfadeRigger();;

    public ExampleStage(DrawerPresenter drawer) {
        addComponent(new DrawerLockingComponent(drawer));
    }
    @Override
    public int getLayoutId() {
        return R.layout.example_stage;
    }
    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
```

#####4.4 An example Stage.Component

The example above includes a `DrawerLockingComponent `, which locks the navigation drawer while the dialog is active. Although this particular Stage only has a single Component, it is easy to include multiple components in a Stage. Ideally, each component should be responsible for a small, well-defined chunk of logic: for example, binding click listeners, making a network call, or creating an animation.

```java
public class DrawerLockingComponent implements Stage.Component {

    private final DrawerPresenter drawer;

    public DrawerLockingComponent(DrawerPresenter drawer) {
        this.drawer = drawer;
    }

    @Override
    public void afterSetUp(Context context, Stage stage, boolean isInitializing) {
        drawer.setLocked(true);
    }

    @Override
    public void beforeTearDown(Context context, Stage stage, boolean isFinishing) {
        drawer.setLocked(false);
    }
}
```

#####4.5 Regular vs. modal Stages

Normally, after a new Stage is pushed onto the stack, the old Stage's View is detached from its parent View to free up memory.

With a modal Stage, the old Stages's view remains attached to the parent. Multiple Stages may be layered in this way. To make a Stage modal, override `Stage#isModal` to return `true`. Here is an example of a modal Stage:

```java
public class DialogStage extends XmlStage {

    private final Rigger rigger;

    public DialogStage(Context context) {
        this.rigger = new PopupRigger(context);
    }
    @Override
    public boolean isModal() {
        return true;
    }
    @Override
    public int getLayoutId() {
        return R.layout.dialog_stage;
    }
    @Override
    public Rigger getRigger() {
        return transformer;
    }
}
```

#####4.6 Stage animations 

A `Rigger` is responsible for applying animations between Stages. The Rigger receives a `Stage.Transition` object, which contains the data that the Rigger needs to create animations, including the `Screenplay.Direction`, the incoming and outgoing Stages, and a `TransitionCallback` that must be called when the transition is complete.

```java
public class HorizontalSlideRigger extends TweenRigger {

    private static final Params params = new Params();

    static {
        params.forwardIn    = R.anim.slide_in_right;
        params.backIn       = R.anim.slide_in_left;
        params.backOut      = R.anim.slide_out_right;
        params.forwardOut   = R.anim.slide_out_left;
    }

    public HorizontalSlideRigger(Application context) {
        super(context, params);
    }
}
```

Screenplay provides two Rigger implementations to extend from: `TweenRigger` and `AnimatorRigger`. TweenRigger uses the [Animation](http://developer.android.com/reference/android/view/animation/Animation.html) class to create a transition, while the AnimatorTransition uses the [Animator](http://developer.android.com/reference/android/animation/Animator.html) class.

###5. Working with Flow

If you're working with the Flow plugin, a bit of configuration is required in your main Activity.

#####5.1 Bootstrapping

 If you're using the Flow plugin, you'll need to create your main Flow. To ensure that the Flow object survives configuration changes, you can put it in a singleton class, or you can parcel the history object and recreate your Flow with each configuration change. We'll take the former approach here:

```java
public class SampleApplication extends Application {

    private final Flow flow = new Flow(Backstack.single(new HomeStage()));
    private static SampleApplication application;

    public void onCreate() { application = this; }

    public static SampleApplication getInstance()       { return application; }
    public static Flow getFlow()                        { return getInstance().flow; }
}
```

(creating a static variable on your Application has the advantage of simplicity, but for a better approach, consider using a dependency injection library such as [Dagger](http://square.github.io/dagger/) to inject your Flow into classes that require it)

In the onCreate() method of your main Activity, create your `ScreenplayDispatcher` object and bind it to your container view. All Stages will be inflated into the container view:

```java
public class MainActivity extends Activity {

    private Flow flow;
    private ScreenplayDispatcher dispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.main);

        flow = SampleApplication.getMainFlow();
        dispatcher = new ScreenplayDispatcher(this, container);
        dispatcher.setUp(flow);
    }
}
```

#####5.2 Handling Activity lifecycle events

1. When the Activity is destroyed, you must call `ScreenplayDispatcher#tearDown`. This performs cleanup actions such as calling `Screenplay#tearDownVisibleStages`, ensuring that your components receive the correct callbacks.
2. Override onBackPressed to route back button to the dispatcher:
```
public class MainActivity extends Activity {

    @Override public void onBackPressed() {
        if (!dispatcher.handleBackPressed()) {
            super.onBackPressed();
        }
    }
}
```

###6. Odds and ends

#####6.1 Checking the transition state

The Screenplay object also exposes a `isTransitioning` method. This is useful for preventing multiple
button presses while two Stages are in transition:

```java
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Ignore menu click if stage is transitioning
        if (screenplay.isTransitioning()) return true;
        
        switch (item.getItemId()) {
            ...
        }
    }
```

#####6.2 Configuration changes

By default, when a configuration change occurs, when `Screenplay#tearDownVisibleStages` is called, each Stage's view is released and a new one is created. If instead you would like a Stage and its view to be retained on configuration changes, override `Stage.teardownOnConfigurationChanges` to return `false`. Keep in mind that setting this to `false` means that the XML for the view will not be reloaded when a configuration change occurs.

###7. Downloads

Screenplay is available via Maven from the jcenter repo.

#####7.1 Main project download

Use Maven to add Screenplay to your project:

```xml
<dependency>
    <groupId>com.davidstemmer.screenplay</groupId>
    <artifactId>screenplay</artifactId>
    <version>1.0.0</version>
</dependency>
```

or Gradle:

```groovy
compile 'com.davidstemmer.screenplay:screenplay:1.0.0'
```

#####7.2 Flow plugin download

The flow-plugin provides a ScreenplayDispatcher that allows you to use Flow to manage the backstack. The version is pegged to the most recent version of Flow. Use maven to add it to your project:

```xml
<dependency>
    <groupId>com.davidstemmer.screenplay</groupId>
    <artifactId>flow-plugin</artifactId>
    <version>0.12</version>
</dependency>
```

or Gradle:

```groovy
compile 'com.davidstemmer.screenplay:flow-plugin:0.12'
```

###8. Contributing
Contributions are welcome! Please open an issue in the github issue tracker to discuss bugs and new feature requests. For simple questions, try our live chat on Gitter: https://gitter.im/weefbellington/screenplay.

###9. Acknowledgements

Many thanks to the team at Square for their support of the open-source community, without which this
project wouldn't be possible. Thanks especially to the team at Square for creating Flow, which was the original inspiration for this project.
