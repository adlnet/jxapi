package gov.adlnet.xapi;

import java.io.IOException;
import java.net.MalformedURLException;

import gov.adlnet.xapi.client.StatementClient;
import gov.adlnet.xapi.model.Statement;

public class App {
	private static final String LRS_URI = "https://lrs.adlnet.gov/xAPI/";
	private static final String USERNAME = "Walt Grata";
	private static final String PASSWORD = "password";	
	public static void main(String[] args) throws IOException {
		StatementClient client = new StatementClient(LRS_URI, USERNAME,
				PASSWORD);
	}
}
