# commons-sandbox
A workspace for developing reusable components for economic agent based models (ABMs).

## Getting started
The following instructions will help walk you through the process of downloading and installing the necessary software.

### Install Java 8
Download and install the Java Development Kit (JDK) 8 from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). 

### Install Activator

Once your JDK has been installed, then you can download and install Play. Mac, Linux, and Windows users can [download](https://www.playframework.com/download) Play (and its dependencies) direct from the website.   
Additionally, Mac users can install the Play framework (and dependencies) via Homebrew (assuming of course that you have installed [Homebrew](http://brew.sh/)) using the following command in a terminal:

    $ brew install activator

## Code coverage
The project uses the [sbt-scoverage](https://github.com/scoverage/sbt-scoverage) plugin to generate test coverage statistics. To run the tests with coverage enabled simply run the following command in a terminal from the project root directory:

    $ sbt clean coverage test

After the tests have finished you can then run

    $ sbt coverageReport

to generate the reports. The generated code coverage reports can be found inside `target/scoverage-report`.

## API documentation

The API documentation for the project is very much a work in progress. To generate the most current version of the documentation simply run the `sbt doc` command from the project root directory:

    $ sbt doc
    
The above command generates the documentation and places it under the `target` directory. The root file for the documentation is located at `target/scala-2.11/api/index.html`. To view the documentation, simply open this file in your favorite browser. 

## Contributing
