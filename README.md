This fork is an OSGi-adapted version of Aura-not project. It should be used
as a submodule of aurframework parent project from:

https://github.com/igor-sfdc/aura

cloned under the root aura folder. More detailed instructions are to be posted.



## What is Aura-Note?

Aura-Note is a simple note taking sample application that demonstrates many of the features and patterns used when building an Aura based application.

To find out more about Aura please see the [Aura Documentation](http://documentation.auraframework.org/auradocs) site or see the instructions at the end of this README for 
accessing the documentation on your localhost after you build the project.

### Prerequisites

You need:

* Java Development Kit (JDK) 1.6
* Apache Maven 3

### Getting Started

1. Clone the repo:

	`git clone https://github.com/forcedotcom/aura-note.git`
	
2. Change to the newly created aura-note folder:

	`cd aura-note`
		
	If you want a pre-populated sample not database just copy the file auranote.db.h2.db (in the same folder as this README) to your home folder
		
3. To start jetty on port 8080 run this:

    `mvn jetty:run -Pdev`

4. To see the app go to:

    `http://localhost:8080/auranote/notes.app`

5. To edit the app open:

    `src/main/webapp/WEB-INF/components/auranote/notes/notes.app`
