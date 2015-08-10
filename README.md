# commons-sandbox
[![Build Status](https://travis-ci.org/ScalABM/commons-sandbox.svg?branch=master)](https://travis-ci.org/ScalABM/commons-sandbox)
[![Coverage Status](https://coveralls.io/repos/ScalABM/commons-sandbox/badge.svg?branch=master&service=github)](https://coveralls.io/github/ScalABM/commons-sandbox?branch=master)
[![Codacy Badge](https://www.codacy.com/project/badge/010943975d84466b85975ff788fe76cb)](https://www.codacy.com/app/drobert-pugh/commons-sandbox)
[![GitHub license](https://img.shields.io/github/license/ScalABM/commons-sandbox.svg)]()

A workspace for developing reusable components for scalable agent-based models of complex systems. The core API is 
being developed in [Scala](http://www.scala-lang.org/) and [Akka](http://akka.io/) while the  actual models 
themselves will be built to run as web applications using the [Play framework](https://www.playframework.com/) in 
order to leverage cutting-edge, browser-based tools for data analysis and visualization.

## Getting started
The following instructions will help walk you through the process of downloading and installing 
the necessary software.

### Install Java 8
Download and install the Java Development Kit (JDK) 8 from 
[Oracle](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). 

### Install Activator

Once you have installed JDK 8, then you can download and install 
[Typesafe Activator](https://www.typesafe.com/community/core-tools/activator-and-sbt). Activator 
includes the sbt build tool, a quick-start GUI, and a catalog of template applications.  Mac 
users can install the Activator via Homebrew (assuming of course that you have installed 
[Homebrew](http://brew.sh/)) using the following command in a terminal to install Activator.

    $ brew install typesafe-activator

## Code coverage
Out project uses the [sbt-scoverage](https://github.com/scoverage/sbt-scoverage) plugin to 
generate test coverage statistics. To run all tests with coverage enabled simply run the following 
command in a terminal from the project root directory.

    $ sbt clean coverage test

After the tests have finished you can then run

    $ sbt coverageReport

to generate the reports. The generated code coverage reports can be found inside the following 
folder.

    commons-sandbox/target/scala-2.11/scoverage-report

## API documentation

The API documentation for the project is very much a work in progress. To generate the most 
current version of the documentation simply run the following command from the project root 
directory.

    $ sbt doc
    
To view the documentation, simply open the following file in your favorite browser. 
 
    commons-sandbox/target/scala-2.11/api/index.html
