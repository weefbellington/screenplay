
*Questions or just want to say hello? Try our Gitter chat!*&nbsp;&nbsp;&nbsp;[![Join the chat at https://gitter.im/weefbellington/screenplay](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/weefbellington/screenplay?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

*Don't forget about [the wiki](https://github.com/weefbellington/screenplay/wiki/Home). If you're coming from an older version, check out the
[CHANGELOG](https://github.com/weefbellington/screenplay/blob/master/CHANGELOG.md).*

```
=====================================================================================
++++++ SCREENPLAY + A minimalist, View-based application framework for Android ++++++
=====================================================================================
```

####What is it?
---

Screenplay is a lightweight Android framework for building applications without the need for Fragments or multiple Activities. Its feature set is small by design: Screenplay is only concerned with swapping views on the screen and playing animations between them.

Screenplay provides the following toolkit:

- the **Screenplay** object: a controller that automatically attaches and detaches Views from the screen in response to navigation events
- the **Stage** interface: defines a screen. Stages can be configured to be full-screen or display above other screens (like a dialog)
- the **Stage.Rigger** interface: applies animated transitions to a screen
- the **Stage.Component** interface: used to apply behavior to screen on attach/detach events

Several implementations for the `Stage`, `Stage.Rigger` and `Stage.Component` are also provided out-of-the-box:

- **XMLStage**: a stage that inflates views from an XML layout file
- **TweenRigger**: animates screen transitions from an XML animation files
- **AnimatorRigger**: animates screen transitions with an `android.animation.Animator`
- **InstanceStateComponent**: saves view state when a screen is detached and restores it on reattach


Additionally, Screenplay provides plugin support. The current plugins are:

- **Flow plugin**: provides a `Flow.Dispatcher` that allows screenplay to respond to `Flow` navigation events. See the [wiki page](https://github.com/weefbellington/screenplay/wiki/The-Flow-Plugin) for more info.


####Your best friend, the wiki
---

The main screenplay documentation has moved to the wiki. Start with the [table of contents](https://github.com/weefbellington/screenplay/wiki/Home) or [core features](https://github.com/weefbellington/screenplay/wiki/Core-features).

####Show me the code!
---

**Want to dive in?**

The [sample project](https://github.com/weefbellington/screenplay/tree/master/sample-simple) is probably the easiest place to start. Here you will find a minimally complex Screenplay application which showcases some of its features.


**Clicking too hard?**

Good news! Here is some sample code you can just scroll through.

```java
public class ExampleStage extends Stage {

    private final ExampleApplication application;
    private final Rigger rigger;

    public ExampleStage(ExampleApplication application) {
        rigger = new CrossfadeRigger(application);
        addComponents(new ClickBindingComponent());
    }

    @Override
    public int getLayoutId() {
        return R.layout.example_scene;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

    private class ClickBindingComponent implements Component {
        @Override
        public void afterSetUp(Stage stage, boolean isInitializing) {
            View parent = stage.getView();
            View nextButton = parent.findViewById(R.id.next);
            nextButton.setOnClickListener(showNextStage);
        }

        @Override
        public void beforeTearDown(Stage stage, boolean isFinishing) {}

        private View.OnClickListener showNextStage = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add a new scene to the history and trigger a transition
                Flow flow = application.getMainFlow();
                flow.set(new NextStage(application));
            }
        };
    }
}
```

####Downloads
---

Screenplay is available via Maven from the jcenter repo.

#####Main project download

Use Maven to add Screenplay to your project:

```xml
<dependency>
    <groupId>com.davidstemmer.screenplay</groupId>
    <artifactId>screenplay</artifactId>
    <version>1.0.2</version>
</dependency>
```

or Gradle:

```groovy
compile 'com.davidstemmer.screenplay:screenplay:1.0.2'
```

#####Flow plugin download

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

####Contributing
---

Contributions are welcome! Please open an issue in the github issue tracker to discuss bugs and new feature requests. For simple questions, try our live chat on Gitter: https://gitter.im/weefbellington/screenplay.

####Acknowledgements
---
Many thanks to the team at Square for their support of the open-source community, without which this
project wouldn't be possible. Thanks especially to the team at Square for creating Flow, which was the original inspiration for this project.

Thanks to the Android Arsenal to adding Screenplay to their tool library. If you like Screenplay, please feel free to the badge to your site. Ignore this directive if you hate badges.

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Screenplay-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2709)
