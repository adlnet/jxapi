jxapi - Experience API Java library
=====
[![Build Status](https://travis-ci.org/adlnet/jxapi.png?branch=master)](https://travis-ci.org/adlnet/jxapi)

## Note: To build jxapi jar with all included dependencies simply run `mvn package`

# StatementClient

To instantiate a new StatementClient you need the URL to an LRS, and a username/password combo that works for HTTP BasicAuth

```java
StatementClient client = new StatementClient(url, username, password);
```

This has the possiblitiy of throwing a ```MalformedURLException``` if an invalid URL is passed it.

## Creating a Statement

This library has some overloaded model constructors to simplify building common parts of a statement.  

```java
// create an activity with just an id
Activity activity = new Activity("http://activity.com");
System.out.println("Activity");
System.out.println( "\t" +  activity  );

// prints...
// Activity
//	http://activity.com

// create an agent with name and mbox
Agent agent = new Agent("tommy", "mailto:tommy@example.com");
System.out.println("Agent - name & mbox");
System.out.println("\t" + agent);

// prints...
// Agent - name & mbox
//	tommy

// create an agent with just an mbox
Agent agent = new Agent(null, "mailto:tommy@example.com")
System.out.println("Agent - mbox");
System.out.println("\t" + agent);

// prints...
// Agent - mbox
//	mailto:tommy@example.com

// create an agent with openid
Agent agent = new Agent(null, new URI("http://tom-is-me")));
System.out.println("Agent - openid");
System.out.println("\t" + agent);

// prints...
// Agent - openid
//	http://tom-is-me

// create an agent with an account
Agent agent = new Agent("", new Account("joe", "http://joe.com"));
System.out.println("Agent - account");
System.out.println("\t" + agent);

// prints...
// Agent - account
//	joe (http://joe.com)


// use an ADL verb
System.out.println("ADL verb");
System.out.println("\t" + Verbs.answered());

// prints...
// ADL verb
//	answered

// create a verb
Verb verb = new Verb("http://my.verb/didsomething");
System.out.println("my verb");
System.out.println("\t" + verb);

// prints...
// my verb
//	http://my.verb/didsomething


// create a verb with display 
HashMap<String, String> disp = new HashMap<String, String>();
disp.put("fr", "le ran");
disp.put("en-US", "ran");
Verb verb = new Verb("http://my.verb/ran", disp);
System.out.println("my ran verb");
System.out.println("\t" + verb);

// prints...
// my ran verb
//	ran

// create a basic statement
System.out.println("Statement ");
System.out.println("\t" + new Statement(new Agent("tom", "mailto:tom@example.com"), 
                                        new Verb("http://verb.com/did", getVerbDisp()),
                                        new Activity("act:id")));

// prints...
// Statement 
//	7fdbc0cc-aef8-47d6-97ad-b1929afc34b5: tom did act:id
```

## Publishing a Statement

To publish a statement you'll need a Verb (There is a predefined list of ADL verbs available in ```gov.adlnet.xapi.model.Verbs``` class), an Actor who compled the activity the statment describes, and the Statement object, which can be an Activity, a SubStatement, or a StatementRef

```java
StatementClient client = new StatementClient(LRS_URI, USERNAME,
PASSWORD);
Statement statement = new Statement();
Agent agent = new Agent();
Verb verb = Verbs.experienced();
agent.setMbox("mailto:test@example.com");
agent.setName("Tester McTesterson");
statement.setActor(agent);
statement.setId(UUID.randomUUID().toString());
statement.setVerb(verb);
Activity a = new Activity();
a.setId("http://example.com");
statement.setObject(a);
ActivityDefinition ad = new ActivityDefinition();
ad.setChoices(new ArrayList<InteractionComponent>());
InteractionComponent ic = new InteractionComponent();
ic.setId("http://example.com");
ic.setDescription(new HashMap<String, String>());
ic.getDescription().put("en-US", "test");
ad.getChoices().add(ic);
ad.setInteractionType("choice");
ad.setMoreInfo("http://example.com");
a.setDefinition(ad);
String publishedId = client.publishStatement(statement);
```

## Querying an LRS

You can get all the statements from an LRS by calling the ```getStatements``` method on the client

This will return a ```StatementResult``` object, which will have a list of all returned statements along with a string needed to get the next page of results, the code below demonstrates how to get the first page of results.

```java
StatementResult results = client.getStatements()
```

To get the next page of results call ```getStatements``` with the value returned from ```getMore```

```java
StatementResult nextPage = client.getStatements(previousPage.getMore());
```

To query an LRS by verb you add a call to ```filterByVerb``` to the above queries

```java
StatementResult results = client.filterByVerb(Verbs.experienced()).getStatements();
```

Subsequent pages can then be queried as the above example.

There are a number of filters available that function similiarly to ```filterByVerb```, they are

```java
filterByVerb
filterByActor
filterByActivity
filterByRegistration
filterBySince
filterByUntil
```
These filters can be chained together to created more complex queries, such as

```java
StatementResult results = client.filterByVerb(verb).filterByActivity(activity).getStatements();
```

You can also specify what data is brought back from the LRS, by calling the ```include*``` methods, the available methods are

```java
includeRelatedActivities
includeRelatedAgents
includeAttachments
```

To bring back only statement ids from the LRS, include the ```ids``` method call when chaining filters/include methods

