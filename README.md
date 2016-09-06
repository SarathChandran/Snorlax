Setting up the Project
======================

1. Install homebrew for mac `http://brew.sh/`
2. type `brew install scala`
3. type `brew install sbt`
2. type `brew install typesafe-activator`
3. clone this repo from Github.

Activator Commands
------------------
- To Compile Project - type `activator compile`
- To Run Project - type `activator run` for hot reload use `activator ~run`
- To Debug Project - type `activator -jvm-debug 9999 run` (Add `remote host` in eclipse/intellij debug config with port 9999)
- App runs on port 9000 (browser load time will be slow in dev mode)

This is your new Play application
=================================

This file will be packaged with your application when using `activator dist`.

There are several demonstration files available in this template.

Controllers
===========

- HomeController.java:

  Shows how to handle simple HTTP requests.

- AsyncController.java:

  Shows how to do asynchronous programming when handling a request.

- CountController.java:

  Shows how to inject a component into a controller and use the component when
  handling requests.

Components
==========

- Module.java:

  Shows how to use Guice to bind all the components needed by your application.

- Counter.java:

  An example of a component that contains state, in this case a simple counter.

- ApplicationTimer.java:

  An example of a component that starts when the application starts and stops
  when the application stops.

Filters
=======

- Filters.java:

  Creates the list of HTTP filters used by your application.

- ExampleFilter.java

  A simple filter that adds a header to every response.