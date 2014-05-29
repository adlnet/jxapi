jxapi - Experience API Java library
=====
[![Build Status](https://travis-ci.org/adlnet/jxapi.png?branch=master)](https://travis-ci.org/adlnet/jxapi)

# StatementClient

To instantiate a new StatementClient you need the URL to an LRS, and a username/password combo that works for HTTP BasicAuth

```
StatementClient client = new StatementClient(url, username, password);
```

This has the possiblitiy of throwing a ```MalformedURLException``` if an invalid URL is passed it.

## Publishing a Statement

To publish a statement you'll need a Verb (There is a predefined list of ADL verbs available in ```gov.adlnet.xapi.model.Verbs``` class), an Actor who compled the activity the statment describes, and the Statement object, which can be an Activity, a SubStatement, or a StatementRef

```
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
