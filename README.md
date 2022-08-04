# Modular Architecture - Monetary Api Application

This project is a sample production-ready implementation for demonstrating the power of Hexagonal Architecture (aka Ports And Adapters Pattern) written in Java.


### Hexagonal Architecture Flow Diagram (click to enlarge)
[![Hexagonal Architecture Flow Diagram](docs/images/hexagonal-flow-diagram_sm.jpg)](docs/images/hexagonal-flow-diagram.jpg)


### Motivation

We want to write clean, maintainable, well-defined boundary context, well-tested domain code and isolate business logic from outside concern.

### What is the Hexagonal Architecture

The hexagonal architecture was invented by Alistair Cockburn in an attempt to avoid known structural pitfalls in object-oriented software design, such as undesired dependencies between layers and contamination of user interface code with business logic, and published in 2005.

> A timeless goal of software engineering has been to separate code that changes frequently from code that is stable.
>
> ~ James Coplien / Lean Architecture

We recommend Hexagonal Architecture for those who want to write clean, maintainable, well-defined boundary context, well-tested domain, and decoupling business logic from technical code.

### Technologies Used

You can use any programming language for implementing Hexagonal Architecture. Here is the list of technologies we used for this application;

* Spring Boot 2
* Java 17
* Gradle 7
* Docker
* Postgres
* Redis
* Kafka