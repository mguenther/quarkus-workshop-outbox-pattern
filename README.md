# Reactive Quarkus Workshop - Transactional Messaging

This repository contains a demo system that showcases the use of the following patterns within a reactive Quarkus application.

* [Transactional Outbox Pattern](https://microservices.io/patterns/data/transactional-outbox.html)
* [Polling Publisher Pattern](https://microservices.io/patterns/data/polling-publisher.html)

Both of these patterns work in conjunction to achieve transactional messaging semantics when doing event collaboration between two different applications. The domain is pretty simple: There is an _Employee Registration Service_ which provides the means to perform CRUD-operations on employee-related data. Every state change is not only persisted in the corresponding database table, but also in the so called outbox, which stores the change of state as an event within the same transaction as the actual change of employee data. An asynchronous process (based on the Quartz integration Quarkus) fetches these unpublished events afterward and publishes them to a dedicated Kafka log. These events are then picked up by another reactive Quarkus application that consumes them in order to update a Solr search index.

The application is built with 3.1.0.Final of Quarkus.

## Running the application

You'll need a locally running Apache Kafka and Apache Solr. We provide a `docker-compose` script for your convenience that sets up a single Kafka broker and a Solr server with a pre-defined core (document collection). See the `docker-compose.yml` script in directory `docker`.

To see the event publication and consumption in action, you'll need to alter the state in some way. This can be done by creating a new employee record using the HTTP API of the employee registration service. This triggers the publication of an event that encapsulates the relevant state changes. The employee indexing service will pick that event up and update the Solr index as appropriate.

You can start both applications in developer mode. `cd` into the resp. directory and issue the following command via the CLI.

```shell
$ mvn compile quarkus:dev
```

## License

This work is released under the terms of the MIT license.