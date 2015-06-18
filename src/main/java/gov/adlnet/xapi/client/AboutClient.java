package gov.adlnet.xapi.client;

import gov.adlnet.xapi.model.About;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lou on 6/18/15.
 */
public class AboutClient extends BaseClient {

    public AboutClient(String uri, String username, String password)
            throws MalformedURLException {
        super(uri, username, password);
    }

    public AboutClient(URL uri, String username, String password)
            throws MalformedURLException {
        super(uri, username, password);
    }

    public About getAbout()
            throws IOException {
        String path = "/xAPI/about";
        String result = issueGet(path);
        return getDecoder().fromJson(result, About.class);
    }
}
