package com.hackerman.plugin.updater;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hackerman.plugin.configManager.configManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import static io.netty.handler.codec.http.HttpHeaders.Names.USER_AGENT;

public class checkUpdate {
    public static String version = "1.1.3";
    public static int productId = 2;

    public static void getLatestVersion() throws IOException {

        if (configManager.getConfigValue ( "config","check_updates", "true").equalsIgnoreCase ( "false" )) {
            return;
        }

        System.out.println ( "[~] Checking for an update..."  );
        String url = "https://hackerman.tech/api/downloads/files/" + productId + "?key=9de71381cf5ebf7df50190d11a87074f";

        try {
            URL obj = new URL ( url );
            HttpURLConnection con = (HttpURLConnection) obj.openConnection ( );
            con.setRequestMethod ( "GET" );
            con.setRequestProperty ( "User-Agent" , USER_AGENT );

            int responseCode = con.getResponseCode ( );
            BufferedReader in = new BufferedReader (
                    new InputStreamReader ( con.getInputStream ( ) ) );
            String inputLine;
            StringBuffer response = new StringBuffer ( );

            while ((inputLine = in.readLine ( )) != null) {
                response.append ( inputLine );
            }
            in.close ( );

            if (responseCode == 200) {
                JsonObject jobj = new Gson ( ).fromJson ( response.toString ( ) , JsonObject.class );
                String lastVersion = jobj.get ( "version" ).getAsString ( );
                String lastVersionTime = jobj.get ( "date" ).getAsString ( );
                String url2 = jobj.get ( "url" ).getAsString ( );
                if (version.equalsIgnoreCase ( lastVersion )) {
                    System.out.println ( "[~] This version is the latest available version." );
                } else {
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
                    TemporalAccessor accessor = timeFormatter.parse ( lastVersionTime );

                    Date date = Date.from ( Instant.from ( accessor ) );

                    System.out.println ( "\n[~] A NEW VERSION IS AVAILABLE FOR `Better About`:" );
                    System.out.println ( "[~] New version `" + lastVersion + "` was released on " + date );
                    System.out.println ( "[~] Download @ " + url2 );
                    System.out.println ( "[~] Support is NOT given on this version anymore!\n" );
                }
            } else {
                System.out.println ( "[~]  Failed to check for an update, error response " + responseCode + "" );
            }
        } catch (Exception e) {
            System.out.println ( "[~] An error occured whilst checking for an update" );

        }
    }

}
