package com.example.m6apps;

import java.util.List;

public class M6Result {
	
	public final int NUMBER_OF_BALLS = 7;
	public final int SPECIAL_NUMBER_IDX_POS = 6;
	private int[] theResultArray = null;
	private String mDrawDateStr;
	private String mFirstPrizeStr;
	private String mSecondPrizeStr;
	private String mThirdPrizeStr;
	private String mDrawTimeInYearStr;
	
	M6Result() {
		theResultArray = new int[NUMBER_OF_BALLS];
    }
	
	M6Result(List<Integer> myList ) {
		theResultArray = new int[NUMBER_OF_BALLS];
	    int s = 0;
		for ( int i : myList ){
            theResultArray[s] = i;
	        s++;
	        if ( s >= NUMBER_OF_BALLS ) break;
	    }
	}
	
	void setBallResults(List<Integer> ballList ){
	    int i = 0;
		for ( Integer val : ballList ){
	        if ( i < NUMBER_OF_BALLS ){
	        	theResultArray[i] = ballList.get(i);
	        }
			i++;
	    }
	}
    
	int[] getBallResult(){
	    return theResultArray;
    } 
	
	int getSpecialNumber(){
	    return theResultArray[SPECIAL_NUMBER_IDX_POS];
	}
	
	void setDrawDateString(String str){
		mDrawDateStr = str;
	}
    
	String getDrawDateString(){
	    return mDrawDateStr;
	}
	
	void setFirstPrizeStr(String str){
		mFirstPrizeStr = str;
	}
	
	String getFirstPrizeStr(){
		return mFirstPrizeStr;
	}
	
    void setSecondPrizeStr(String str){
    	mSecondPrizeStr = str;
	}
	
	String getSecondPrizeStr(){
		return mSecondPrizeStr;
	}
	
    void setThirdPrizeStr(String str){
    	mThirdPrizeStr = str;
	}
	
	String getThirdPrizeStr(){
		return mThirdPrizeStr;
	}
	 
	void setDrawTimesInYearStr(String drawTimesinYear ){
		mDrawTimeInYearStr = drawTimesinYear;
	}
	
	String getDrawTimesInYearStr(){
	    return mDrawTimeInYearStr;
	}
}  
