# Building Reactive Systems with Quarkus

This repository contains two sample applications that demonstrates how to implement a reactive application from top-to-bottom with Quarkus. This reactive applications form a reactive system by communicating through an event-driven manner. The domain is simple: An employee registration service offers an HTTP-based API to perform CRUD-operations on employee-related data. This service publishes events to a Kafka log whenever its state changes. These events are picked up by another reactive application that consumes them and updates a Solr search index. Apache Solr features no reactive drivers, so we'll make use of the `@Blocking` annotation to do all Solr-related work on dedicated worker threads. The application is built with version 2.12.3.Final of Quarkus.

The code presented in this repository is the joint work of Boris Fresow and Markus Günther. It's also showcased as part of an article series on *Building Reactive Systems with Quarkus* written by both authors and published for the German JavaMagazin. The articles are in German, but if you want to check them out, they are available both in print and online:

* [#1: Reaktive Programmierung mit Quarkus](https://entwickler.de/java/quarkus-reaktive-programmierung-java) (JavaMagazin 2/2023)
* [#2: Mutiny: intuitiv verständlich?](https://entwickler.de/java/reactive-library-mutiny-java) (JavaMagazin 3/2023)
* #3: Reaktive Anwendungen mit Quarkus entwickeln (*in Vorbereitung*)
* #4: Reaktive Systeme mit Quarkus entwickeln (*in Vorbereitung*)

If you want to learn more about Quarkus, be sure to check out its [website](https://quarkus.io/).

## Running the application

You'll need a locally running Apache Kafka and Apache Solr. We provide a `docker-compose` script for your convenience that sets up a single Kafka broker and a Solr server with a pre-defined core (document collection). See the `docker-compose.yml` script in directory `docker`.

To see the event publication and consumption in action, you'll need to alter the state in some way. This can be done by creating a new employee record using the HTTP API of the employee registration service. This triggers the publication of an event that encapsulates the relevant state changes. The employee indexing service will pick that event up and update the Solr index as appropriate.

You can start both applications in developer mode. `cd` into the resp. directory and issue the following command via the CLI.

```shell
$ mvn compile quarkus:dev
```

## License

This work is released under the terms of the MIT license.