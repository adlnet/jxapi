package gov.adlnet.xapi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import gov.adlnet.xapi.model.StatementReference;

@RunWith(Suite.class)
@SuiteClasses({ AboutTest.class, AboutClientTest.class, AccountTest.class, ActivityTest.class, 
		ActivityDefinitionTest.class, ActivityProfileTest.class, ActivityStateTest.class, 
		ActivityClientTest.class, ActorTest.class, ActorAdapterTest.class, 
		AgentClientTest.class, AgentTest.class, AgentProfileTest.class,
		AttachmentAndTypeTest.class, AttachmentResultTest.class, AttachmentTest.class,
		ContentActivitiesTest.class, ContextTest.class, GroupTest.class, 
		InteractionComponentTest.class,	PersonTest.class, ResultTest.class, ScoreTest.class, 
		StatementAdapterTest.class, StatementClientTest.class, SubStatementTest.class, 
		StatementTest.class, StatementReferenceTest.class, StatementResultTest.class, VerbsTest.class, 
		VerbTest.class })
public class AllTests {

}
