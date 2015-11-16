
*Questions or just want to say hello? Try our Gitter chat!*&nbsp;&nbsp;&nbsp;[![Join the chat at https://gitter.im/weefbellington/screenplay](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/weefbellington/screenplay?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

*Don't forget about [the wiki](https://github.com/weefbellington/screenplay/wiki/Home). If you're coming from an older version, check out the
[CHANGELOG](https://github.com/weefbellington/screenplay/blob/master/CHANGELOG.md).*

```
=====================================================================================
++++++ SCREENPLAY + A minimalist, View-based application framework for Android ++++++
=====================================================================================
```

Screenplay is a tiny, moderately opinionated Android application framework. It is designed for building View-based apps with a particular kind of architecture: **single-activity**, with **no fragments**, **no dialogs**, and **small classes**. Here's a preview:

```java
public class ExampleStage extends Stage {

    private final ExampleApplication application;
    private final Rigger animationRigger;

    public ExampleStage(ExampleApplication application) {
        animationRigger = new CrossfadeRigger(application);
        addComponents(new ClickBindingComponent());
    }

    @Override
    public int getLayoutId() {
        return R.layout.example_scene;
    }

    @Override
    public Rigger getRigger() {
        return animationRigger;
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

#####Wait, what's a View-based application?

When people talk about View-based Android applications, they usually mean an application that doesn't use multiple Activities or Fragments. Instead, it uses few Activities -- often just one -- and creates screen transition effects by swapping views on and off the screen. Many in the Android community have lobbied [in favor of View-based applications](https://corner.squareup.com/2014/10/advocating-against-android-fragments.html). The idea is that by programming directly with Views, you can avoid a lot of the complexity and indirection that is associated with the Android application lifecycle.

#####What's the catch?

The downside of View-based development is that there's a lot of things you no longer get for free. The backstack is one thing that gets left behind when you're building apps out of straight Views instead of Activities or Fragments. There's a surprisingly large amount of bookeeping associated attaching and detaching views from the screen, and when you factor in things like animated transitions, stuff gets complicated in a hurry.

#####Hasn't somebody already figured out this stuff?

Libraries like [Flow](https://github.com/square/flow) have been created to address the problem of the missing backstack. Screenplay is designed to handle the rest of the equation.

#####So what the heck can this library do for me?

Screenplay is designed to take the pain out of View-based application development. It consists of the following parts:

1. A View controller (the `Screenplay` class) for automatically attaching and detaching Views from the screen.
1. Lightweight view container objects (the `Stage` class) that can be used to create full-screen views, dialogs, drawers, and panels.
1. A declarative approach to animated View transitions (the `Stage.Rigger` class).
1. A component-oriented architecture which can be used to bind behavior to view containers (the `Stage.Component` class).
1. Navigation, routing and backstack management, via the Flow plugin or your own custom logic.

Taken together, these components provide a baseline for View-based application development. Screenplay reduces boilerplate, providing a straightforward, modular approach to building applications. It is designed to be accessible to junior and senior developers alike, with a structure that encourages separation of view presentation from display.

####Show me the code!
---

The easiest way to get a feel for Screenplay is to dive into the code, and the [sample project](https://github.com/weefbellington/screenplay/tree/master/sample-simple) is the recommended place to start. Here you will find a minimally complex Screenplay application which showcases some of its features.


####Your best friend, the wiki
---


The main screenplay documentation has moved to the wiki. View the [table of contents](https://github.com/weefbellington/screenplay/wiki/Home), or reading the manual back to front it your type of thing, you can start by reading about [core features](https://github.com/weefbellington/screenplay/wiki/Core-features).

####Downloads
---

Screenplay is available via Maven from the jcenter repo.

#####Main project download

Use Maven to add Screenplay to your project:

```xml
<dependency>
    <groupId>com.davidstemmer.screenplay</groupId>
    <artifactId>screenplay</artifactId>
    <version>1.0.1</version>
</dependency>
```

or Gradle:

```groovy
compile 'com.davidstemmer.screenplay:screenplay:1.0.1'
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

####Conributing
---

Contributions are welcome! Please open an issue in the github issue tracker to discuss bugs and new feature requests. For simple questions, try our live chat on Gitter: https://gitter.im/weefbellington/screenplay.

####Acknowledgements
---
Many thanks to the team at Square for their support of the open-source community, without which this
project wouldn't be possible. Thanks especially to the team at Square for creating Flow, which was the original inspiration for this project.

Thanks to the Android Arsenal to adding Screenplay to their tool library. If you like Screenplay, please feel free to the badge to your site.

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Screenplay-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2709)
