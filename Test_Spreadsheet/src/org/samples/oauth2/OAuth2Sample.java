package org.samples.oauth2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gdata.client.GoogleService;
import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthRsaSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthSigner;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.BaseEntry;
import com.google.gdata.data.BaseFeed;
import com.google.gdata.data.Feed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import java.awt.*;

import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.client.authn.oauth.OAuthParameters.OAuthType;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
/**
 * 
 * @author Hari
 * 
 * Class implemented for accessing google spreadsheet application programming interface (API) features related to authentication
 *
 */

public class OAuth2Sample {
   

   /**
    * Log in to Google, with the OAuth2 authentication
    * 
    * @param clientID client ID received from registering the application with Google
           * @param clientSecret client Secret received from registering the application with Google
 * @throws ServiceException 
 * @throws IOException 
    */
	
	public OAuth2Sample(String msg)
	{
		System.out.println(msg);
	}
	

	

public void loginOAuth2(String clientID, String clientSecret)
       throws IOException, ServiceException {

	System.out.println("inside login auth");
	
      String SCOPES = "https://docs.google.com/feeds https://spreadsheets.google.com/feeds";
      
      // STEP 1: Set up the OAuth objects

      // You first need to initialize a few OAuth-related objects.
      // GoogleOAuthParameters holds all the parameters related to OAuth.
      // OAuthSigner is responsible for signing the OAuth base string.
      GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();

      // Set your OAuth Consumer Key (which you can register at
      // https://www.google.com/accounts/ManageDomains).
      oauthParameters.setOAuthConsumerKey(clientID);

     // System.out.println(clientID);
      
      // Initialize the OAuth Signer.  
      OAuthSigner signer = null;
      oauthParameters.setOAuthConsumerSecret(clientSecret);
      //System.out.println("Clinet Secret");
      signer = new OAuthHmacSha1Signer();
      //System.out.println(signer);

      // Finally create a new GoogleOAuthHelperObject.  This is the object you
      // will use for all OAuth-related interaction.
      GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(signer);


      // STEP 2: Get the Authorization URL

      // Set the scope for this particular service.
      
      System.out.println(SCOPES);
      //oauthParameters.setScope(SCOPES);
      //System.out.println(oauthParameters);
      oauthParameters.setOAuthType(OAuthType.TWO_LEGGED_OAUTH);

      // This method also makes a request to get the unauthorized request token,
      // and adds it to the oauthParameters object, along with the token secret
      // (if it is present).
      try {
    oauthHelper.getUnauthorizedRequestToken(oauthParameters);
    //System.out.println("inside try");
   } catch (OAuthException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
      System.out.println("outside catch");
      // Get the authorization url.  The user of your application must visit
      // this url in order to authorize with Google.  If you are building a
      // browser-based application, you can redirect the user to the authorization
      // url.
      String requestUrl = oauthHelper.createUserAuthorizationUrl(oauthParameters);
      System.out.println(requestUrl);
      System.out.println("Please visit the URL above to authorize your OAuth "
          + "request token.  Once that is complete, press any key to "
          + "continue...");
      try {
    System.in.read();
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }


      // STEP 3: Get the Access Token

      // Once the user authorizes with Google, the request token can be exchanged
      // for a long-lived access token.  If you are building a browser-based
      // application, you should parse the incoming request token from the url and
      // set it in GoogleOAuthParameters before calling getAccessToken().
      String token = null;
   try {
	token = oauthHelper.getAccessToken(oauthParameters);
} catch (OAuthException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
   //token = "ya29.egLAJsRKzauMP6ZCiMJdRClrUAEqjj1UlQmxtMrcCAkzpEp0xcFlqZe9N8SodZq2vaBc";
      System.out.println("OAuth Access Token: " + token);
      System.out.println();


      // STEP 4: Make an OAuth authorized request to Google

      // Initialize the variables needed to make the request
      URL feedUrl = null;
   try {
    feedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
    System.out.println("inside feedurl try");

   } catch (MalformedURLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
      System.out.println("Sending request to " + feedUrl.toString());
      System.out.println();
      SpreadsheetService googleService =
          new SpreadsheetService("oauth-sample-app");

      // Set the OAuth credentials which were obtained from the step above.
      try {
    googleService.setOAuthCredentials(oauthParameters, signer);
    System.out.println("inside setOUthcred try");
   } catch (OAuthException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }

      // Make the request to Google

      
      // Make a request to the API and get all spreadsheets.
      SpreadsheetFeed feed = googleService.getFeed(feedUrl, SpreadsheetFeed.class);
      ListEntry spreadsheets = (ListEntry) feed.getEntries();
      System.out.println("Beofre REsponse DAta");
      System.out.println("Response Data:");
      System.out.println("=====================================================");
      if(spreadsheets != null) {
       
       // Iterate through all of the spreadsheets returned
       for (SpreadsheetEntry spreadsheet : feed.getEntries()) {
   //Print the name of each spreadshet
        System.out.println(spreadsheet.getTitle().getPlainText());
       }
      }
      
      ListFeed lf = googleService.getFeed(feedUrl, ListFeed.class);
      
      for (ListEntry le : lf.getEntries()) {
          CustomElementCollection cec = le.getCustomElements();
          //Pass column name to access it's cell values
          le.getCustomElements().setValueLocal("CustomerId",le.getId());
          //String val3= cec.getValue("CustomerId");
          le.update();
      }
      //ListFeed lf = service.getFeed(feedUrl, ListFeed.class);
      
      

       System.out.println("=====================================================");
      System.out.println();

     


      ////////////////////////////////////////////////////////////////////////////
      // STEP 5: Revoke the OAuth token
      ////////////////////////////////////////////////////////////////////////////

      System.out.println("Revoking OAuth Token...");
      try {
    oauthHelper.revokeToken(oauthParameters);
   } catch (OAuthException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
      System.out.println("OAuth Token revoked...");
   }


}
