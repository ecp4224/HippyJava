package com.ep.hippyjava.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class WebUtils {
    
    /**
     * Returns the response from the given URL as a single line of text.
     * At the end of each line, there will be a \n appened to it.
     * @param url
     *           The url to connect to.
     * @return
     *        A response string from the server.
     * @throws Exception
     *                  This exception is thrown when there was a problem connecting to the URL
     */
    public static String getTextAsString(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            response.append("\n" + inputLine);

        in.close();

        return response.toString();
    }
    
    /**
     * Returns the response from the given URL as an array of lines.
     * @param url
     *           The url to connect to.
     * @return
     *        A response string from the server.
     * @throws Exception
     *                  This exception is thrown when there was a problem connecting to the URL
     */
    public static String[] getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    connection.getInputStream()));

        ArrayList<String> lines = new ArrayList<String>();
        String inputLine;
        
        while ((inputLine = in.readLine()) != null) 
            lines.add(inputLine);

        in.close();

        return lines.toArray(new String[lines.size()]);
    }

}
