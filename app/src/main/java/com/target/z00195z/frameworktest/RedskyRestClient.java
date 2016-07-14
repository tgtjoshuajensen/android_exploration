package com.target.z00195z.frameworktest;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




public class RedskyRestClient {

    private static final String REDSKY_SEARCH_API =
            "https://www.tgtappdata.com/v1/products/list?searchTerm=%s";

    public static JSONObject getJSON(String searchTerm) {
        try {

            URL url = new URL(String.format(REDSKY_SEARCH_API, searchTerm));

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuilder json = new StringBuilder(1024);
            String tmp;
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            return new JSONObject(json.toString());
        } catch (Exception e) {
            Log.e("EXCEPTION", String.valueOf(e));
            return null;
        }
    }
}
