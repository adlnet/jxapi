package gov.adlnet.xapi.model;

import java.util.UUID;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.google.gson.*;
/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	Gson g = new Gson();
    	Statement statement = new Statement();
    	statement.setId(UUID.randomUUID());
    	String s = g.toJson(statement);    	
        assertTrue( s, false );
    }
}
