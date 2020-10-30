package com.example.m6apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

public class NetworkConnector {
	
	Context myContext;
	NetworkConnector( Context ctx ){
		myContext = ctx;
	}
	
	NetworkConnector() {
	    myContext = null;
	}
    public boolean isNetworkAvailable(){
    	/*
    	ConnectivityManager cm = (ConnectivityManager)myContext.getSystemService(Context.CONNECTIVITY_SERVICE);

    	  State mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

    	  State wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

    	  if (wifi == NetworkInfo.State.CONNECTED )
    	    {
    	        return true;
    	    }
    	  if (mobile == NetworkInfo.State.CONNECTED)
    	    {
    	        return true;
    	    }
        
    	<uses-permission android:name="android.permission.INTERNET" />    return false;
    	   */
    	return true;
    }      
           	
    public String getWebPageContent(String myUrl ){
        if ( !isNetworkAvailable() ){
    	    return null;
        }
        String result = getContents(myUrl); 
        return result;
    }
    public static String getContents(String url) {
        String contents = null;
 
  try {
        URLConnection conn = new URL(url).openConnection();
 
        InputStream in = conn.getInputStream();
        contents = convertStreamToString(in);
   } catch (MalformedURLException e) {
        Log.d("Anderson","MALFORMED URL EXCEPTION");
   } catch (IOException e) {
        Log.d("Anderson" , e.getMessage(), e);
   }
 
  return contents;
}
    private static String convertStreamToString(InputStream is) 
    		throws UnsupportedEncodingException {
    	 
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
          StringBuilder sb = new StringBuilder();
           String line = null;
           try {
                  while ((line = reader.readLine()) != null) {
                          sb.append(line + "\n");
                  }
             } catch (IOException e) {
                  e.printStackTrace();
             } finally {
                  try {
                          is.close();
                  } catch (IOException e) {
                          e.printStackTrace();
                  }
              }
          return sb.toString();
    }
}