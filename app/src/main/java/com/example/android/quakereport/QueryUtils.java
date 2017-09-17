package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link EarthquakeData} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<EarthquakeData> extractEarthquakes(String jsonResponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthquakeData> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject eachJson,propertiesObject;
            JSONArray features = jsonObject.getJSONArray("features");

            for (int i=0;i<features.length();i++) {
                eachJson = features.getJSONObject(i);
                propertiesObject = eachJson.getJSONObject("properties");
                double mag = propertiesObject.getDouble("mag");
                String place = propertiesObject.getString("place");
                Long time = propertiesObject.getLong("time");
                String url = propertiesObject.getString("url");


                earthquakes.add(new EarthquakeData(mag,place,time,url));
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    public static List<EarthquakeData> fetchEarthquakeData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException ioe){
            Log.e("Query Utils: ", " IO Exception at line 90", ioe);
        }
        return extractEarthquakes(jsonResponse);
    }

    public static URL createUrl(String requestUrl){
        URL url = null;
        try{
            url= new URL(requestUrl);
        }catch(MalformedURLException mue){
            mue.printStackTrace();
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws  IOException{
        String jsonResponse="";

        if (url == null)
            return jsonResponse;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse= readFromStream(inputStream);
            }else{
                Log.e("QueryUtils.java","Error code: "+httpURLConnection.getResponseCode());
            }

        }catch(IOException ioe){
            Log.e("QueryUtils.java", "IOException at line 118", ioe);
        }finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }
        return jsonResponse;
    }

    public static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder jsonResponse=new StringBuilder();
        if(inputStream !=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String eachLine = bufferedReader.readLine();
            while (eachLine != null){
                jsonResponse.append(eachLine);
                eachLine=bufferedReader.readLine();
            }
        }
        return jsonResponse.toString();
    }
}