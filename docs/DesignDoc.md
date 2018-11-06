---
geometry: margin=1in
---
# PROJECT Design Documentation

> _The following template provides the headings for your Design
> Documentation.  As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics._

## Team Information
* Team name: 11D
* Team members
  * Nick Sander
  * John Knecht
  * Shannon Quinn
  * Zeke Miller
  * Spencer Fleming

## Executive Summary

This WebCheckers application implements the Java Spark framework to provide the user with a game of checkers againsted other signed in users. To play, a user must sign in with a unique username and select or get selected by another user who is not currently in a game. The application also provides an A.I. player for the user to play against.

### Purpose
> Connect players from across the globe and allow them to play a game of checkers.  WebCheckers is meant to provide an easy to use interface by which players can easily play checkers against anyone else that is signed in at the time.

### Glossary and Acronyms
> _Provide a table of terms and acronyms._

| Term | Definition |
|------|------------|
| VO | Value Object |


## Requirements

This section describes the features of the application.

> _In this section you do not need to be exhaustive and list every
> story.  Focus on top-level features from the Vision document and
> maybe Epics and critical Stories._

### Definition of MVP
	The MVP of Web Checkers allows players to sign in, choose a username, play a game of American
checkers against their chosen opponent, resign from a game and sign out when they are
finished playing.

### MVP Features
> 

### Roadmap of Enhancements
> _Provide a list of top-level features in the order you plan to consider them._


## Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](domain-model.png)

> The domain itself for a game of checkers is fairly simple.  Each game being played is played on a single board by two players.  Each player controls 12 pieces of a given color (at the start), which are placed in a pattern on the 64 Squares on the Board.


## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a
browser.  The client-side of the UI is composed of HTML pages with
some minimal CSS for styling the page.  There is also some JavaScript
that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](web-interface-statechart.png)

> The interface consists of a few main pages, with a few extra states to represent the user's game status and sign in status, for example.  The user starts off disconnected, then is prompted to sign in.  Once the user signs in, they can either begin a game or wait to be challenged.  The red player is prompted to make a move, while the black player waits for his/her opponent to take their turn.  Once the current player makes their move, the two players swap states, with the other player making their turn while the previous player once again waits.


### UI Tier
> _Provide a summary of the Server-side UI tier of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class structure or object diagrams) with some
> details such as critical attributes and methods._

> _You must also provide any dynamic models, such as statechart and
> sequence diagrams, as is relevant to a particular aspect of the design
> that you are describing.  For example, in WebCheckers you might create
> a sequence diagram of the `POST /validateMove` HTTP request processing
> or you might show a statechart diagram if the Game component uses a
> state machine to manage the game._

> _If a dynamic model, such as a statechart describes a feature that is
> not mostly in this tier and cuts across multiple tiers, you can
> consider placing the narrative description of that feature in a
> separate section for describing significant features. Place this after
> you describe the design of the three tiers._

The UI tier starts with the server starting up, which will create all the need HTTP routes.





### Application Tier

The application tier includes the classes GameCenter and PlayerLobby.
When the server is started then we create an instance of GameCenter that holds a PlayerLobby.
PlayerLobby will hold all of the players currently signed into Web Checkers. 


### Model Tier
> _Provide a summary of the Application tier of your architecture. This
> section will follow the same instructions that are given for the UI
> Tier above._

### Design Improvements
>If the project were to continue, we would change the info and error enums to be
capitalized as it was intended to be. We would also nuke all of the javascript 
and start from scratch. 

## Testing
> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing

We have currently 9 stories fully passing their acceptance criteria tests. 
We don't have any conerns about the tests that we have checked. 
Our user story of Multiple Jumps has not yet been tested because we are 
still having some issues with implementing it. 



### Unit Testing and Code Coverage
Our unit testing strategy was to get as many lines covered as possible. We used Mocktio to help us create mock objects. Currently our code is covered at 91%. We were planning on aiming for 80% coverage in the UI class, but we managed to get 90% coverage.  We aimed for 95% coverage in the application tier, which was accomplished 
with ease. The coverage in model tier is close to the 95% coverage goal at 94%. Our model tier is 
still a work in progress, so we should be able to accomplish our goal of 95% at the end of Sprint 3 with no problem.

![Code Coverage](Code-Coverage.png)