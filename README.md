# Weather App

## Running the app

There are multiple ways to run the app:

- Run `weatherapp.jar`
  
  Required libraries are bundled in the JAR file. 

- Run `./gradlew run` in the terminal
  
  This will download necessary libraries, build the project and run it.

To build the JAR file bundled with all dependencies on your own, run `./gradlew fullJar`, the output is in `./build/libs`

## Requirements

_Java version 9 or higher is required._

_Internet connection is required for building and running the app._

## Tools & libraries

We used the **Gradle** build tool to build the project, manage dependencies, build-related tasks and project configuration.

The following libraries are used (listed as dependencies in the `build.gradle` file)

- [Google Gson](https://github.com/google/gson)