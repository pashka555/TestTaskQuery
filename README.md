# University Querier

##Short description
A simple Hibernate-based project designed for one to simply type in a question or a request to, for example, show statistics or average salary of a university department

## How to use

You first need a postgres database called universitydb. Launching this app, for now, **wipes it and fills anew with data**.

Find example queries done in the MainProcess.java and add your own! using a piece of code like

```
new RequestProcessor("Search by li")
			.executeIntent();
```

It will create a new instance of request processor and process the command, then execute said query. 

## Available commands
* Search (globally) ``Search by %word%``

* Show average salary ``Show salary %departmentname%``

* Show statistics ``Show statistics %departmentname`` (or even ``Show statistics for %departmentname%``, app is insensitive to extra words so one can form entire sentences) 

* Show all heads ``Show heads``

* Show a head of a department ``Show head of %departmentname%``

You can find out department names simply by searching for "Department"

```
Search by Department
```

## TODO

* Event loop to read commands (currently, it executes some commands formatted as string and one may add their own easily)
* Format the answers, they are simple one-word sentences yet, except for statistics.
* Change the main logic, change the behaviour of hibernate to update instead of creating the database schemas. Like this, it works as a great demonstration.

##Technologies used

* Hibernate
* Javax
* Gradle