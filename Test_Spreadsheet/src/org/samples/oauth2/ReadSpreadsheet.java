package org.samples.oauth2;

import java.io.IOException;
import java.net.URL;
 
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;
 
public class ReadSpreadsheet {
 
    public static final String Client_ID = "748106676844-4ijv682q74cblhru0cb6ir8i0nc8df7u.apps.googleusercontent.com";
      public static final String Client_secret = "8woCtKt6cSny_7s3jMP395GR"; 
     // public static final String SPREADSHEET_URL = "https://spreadsheets.google.com/feeds/spreadsheets/1BuEN5wHRrQJFgTj9bXN6lXEAcb1lcJ-QYg4Lya-3lCo"; //Fill in google spreadsheet URI
 
      public static void main(String[] args) throws IOException, ServiceException
      {
        /** Our view of Google Spreadsheets as an authenticated Google user. */
        OAuth2Sample ser = new OAuth2Sample("Print Google Spreadsheet Demo");
 
        // Login and prompt the user to pick a sheet to use.
        ser.loginOAuth2(Client_ID,Client_secret);
        
        //System.out.println("outer class");
      }
}