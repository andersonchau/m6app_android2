package com.example.m6apps;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Application; 


public class MyApp extends Application {
	// private List<Date,> ;     
	HashMap<Date,List<Integer>> historyResults;
	boolean bHaveHistResult;
	boolean bHaveHistoryResults(){
	    return bHaveHistResult;
	}
    
	void setHistoryResults(HashMap<Date,List<Integer>> results ){
		historyResults = results;
	}
	
	@Override 
	public void onCreate() { 
        // TODO Auto-generated method stub 
        super.onCreate(); 
        bHaveHistResult = false;        
    }    
}
