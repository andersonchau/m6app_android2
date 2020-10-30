package com.example.m6apps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class M6ResultWebPageRetriever extends M6ResultRetriever {
    //boolean bHaveReuslt = false;
	private static final String TAG = "M6ResultWebPageRetriever";
	private static final String urlString = "http://www.checkmark6.com/";
	private static final String urlHistoryString = "http://bet.hkjc.com/marksix/Results.aspx";
	private int globalUniqueCounter=0;
        private static final String urlHistoryStringLongList="http://hk.myfreepost.com/en/hkjc/marksix/resultlist/?dMonth=%s&dYear=%s&button=Go";
	private List<Integer> latestResultList; 
    
    
	private HashMap<Date,List<Integer>> historyResults;
	private ArrayList<M6Result> historyResultsObj;
	@Override
	List<Integer> getLatestNumbers() {
		// TODO Auto-generated method stub
	    
		
		NetworkConnector myConnector = new NetworkConnector();
		String result =  myConnector.getWebPageContent(urlString);
		if ( result == null ) return null;
		   // parse the webpage content here ..
		   ArrayList<String> myList = new ArrayList<String>();
		   for (String token : result.split("\n")) {
		       if( token.indexOf("result1") != -1 ||
				  token.indexOf("result2") != -1 ||
				token.indexOf("result3") != -1 ||
				token.indexOf("result4") != -1 ||
				token.indexOf("result5") != -1 ||
				token.indexOf("result6") != -1 ||
				token.indexOf("resultspecial") != -1 )  {
				   Pattern p = Pattern.compile("images/[0-9]+.gif");
				    Matcher m = p.matcher(token);
				   // System.out.println(m.find());
				   // System.out.println(m.matches());
				     if ( m.find()) {
				         String rematchString = m.group();
				         Pattern p2 = Pattern.compile("[0-9]+");
						 Matcher m2 = p2.matcher(rematchString);
						 if ( m2.find() ){
						     myList.add(m2.group());
						 }
				     }
				    	
			   }
		   }
		latestResultList = new ArrayList<Integer>();
		for ( String a : myList ){
			latestResultList.add(Integer.valueOf(a));
		}
        return latestResultList;
	}
        

       String getCurrentMonthString(){
            return null;
       }
       String getCurrentYearString(){
            return null;
       }
       
       int parseOneWebPageM6Obj(NetworkConnector myNetConnector , String urlString ){
    	   
    	   int totalNumResult = 0;
    	   String webPageStr =  myNetConnector.getWebPageContent(urlString);
    	   if ( webPageStr == null ) {
			    Log.d("Anderson", "web page string is null!");
			    return -1;
		    }
    	   historyResultsObj = new ArrayList<M6Result>();
    	   //Log.d("HIHI", " " + webPageStr);
           int position = webPageStr.indexOf("Winning Numbers");
           Log.d("HIHIHI", " Winning number position is" + position);
           String trimmedWebPageStr = webPageStr.substring(position);
           //Log.d("AAAAA", " " + trimmedWebPageStr);
           Pattern myPattern = Pattern.compile("[0-9]+-[0-9]+-[0-9]+-[0-9]+-[0-9]+-[0-9]+-\\([0-9]+\\)");
           Matcher webPageMatcher = myPattern.matcher(trimmedWebPageStr);
           List<Integer> aResultList =new ArrayList<Integer>();
           
           while( webPageMatcher.find() ){
        	   totalNumResult++;
               String matcherPatternStr = webPageMatcher.group();
               StringTokenizer str2= new StringTokenizer(matcherPatternStr,"-");
               int i = 0;
               while (str2.hasMoreTokens()){ // 
                   i++;
                   if( i > 6){
                            String tmpStr = str2.nextToken();
                            String resultStr = tmpStr.substring(1,tmpStr.length()-1);
                            int resultVal = Integer.parseInt(resultStr);
                            aResultList.add(resultVal);
                            Date tmpDate = new Date();
                            tmpDate.setTime(globalUniqueCounter++);
                            Log.d("YYYY","Adding result " + aResultList.toString() );
                            historyResults.put(tmpDate,aResultList);
                            historyResultsObj.add(new M6Result(aResultList) );
                            aResultList = new ArrayList<Integer>();
                            
                            break;
                        } else {
                            int resultVal = Integer.parseInt(str2.nextToken());
                            aResultList.add(resultVal);
                        }
               	 }
                }
               
                
    	        return totalNumResult;
    	   
       }
       
       
       int parseOneWebPage(NetworkConnector myNetConnector , String urlString ){
    	   int totalNumResult = 0;
    	   String webPageStr =  myNetConnector.getWebPageContent(urlString);
    	   if ( webPageStr == null ) {
			    Log.d("Anderson", "web page string is null!");
			    return -1;
		    }
		   //Log.d("HIHI", " " + webPageStr);
           int position = webPageStr.indexOf("Winning Numbers");
           Log.d("HIHIHI", " Winning number position is" + position);
           String trimmedWebPageStr = webPageStr.substring(position);
           //Log.d("AAAAA", " " + trimmedWebPageStr);
           Pattern myPattern = Pattern.compile("[0-9]+-[0-9]+-[0-9]+-[0-9]+-[0-9]+-[0-9]+-\\([0-9]+\\)");
           Matcher webPageMatcher = myPattern.matcher(trimmedWebPageStr);
           List<Integer> aResultList =new ArrayList<Integer>();
           
           while( webPageMatcher.find() ){
        	   totalNumResult++;
               String matcherPatternStr = webPageMatcher.group();
               StringTokenizer str2= new StringTokenizer(matcherPatternStr,"-");
               int i = 0;
               while (str2.hasMoreTokens()){ // 
                   i++;
                   if( i > 6){
                            String tmpStr = str2.nextToken();
                            //Log.d("AAAAAAA", "tmpStr is " + tmpStr );
                            String resultStr = tmpStr.substring(1,tmpStr.length()-1);
                            int resultVal = Integer.parseInt(resultStr);
                            aResultList.add(resultVal);
                            //historyResults.put()
                            Date tmpDate = new Date();
                            tmpDate.setTime(globalUniqueCounter++);
                            Log.d("YYYY","Adding result " + aResultList.toString() );
                            historyResults.put(tmpDate,aResultList);
                            aResultList = new ArrayList<Integer>();
                            break;
                        } else {
                            int resultVal = Integer.parseInt(str2.nextToken());
                            aResultList.add(resultVal);
                        }
               	 }
                }
               
                
    	        return totalNumResult;
    	   
       }
       
       @Override 
        HashMap<Date, List<Integer>> getHistoryNumbers() {
        	int totalPastHistResultNum =0;
        	int MAX_NUM_HISTORY_DATA = 50;
        	globalUniqueCounter=0;
        	NetworkConnector myConnector = new NetworkConnector();
        	historyResults = new HashMap<Date,List<Integer>>();
        	
        	DateInfo myDateInfo = new DateInfo();
            myDateInfo.setupDateInfoFromCurDate();
            int getM6ResultsCode = -1;
            int loopCount = 0; 
            do {
            	// construct the link address string 
            	
            	String urlString = null;
            	// the current month is not enough 
            	if ( loopCount == 0 ){
            		myDateInfo.setupDateInfoFromCurDate();
            	} else {
            		myDateInfo.setPrevMonth();
            	}
            	urlString = String.format( urlHistoryStringLongList , myDateInfo.getMonthStr(),myDateInfo.getYearStr() );
            	Log.d("Andrson" , "urlString is " + urlString);
            	getM6ResultsCode = parseOneWebPage(myConnector, urlString);
                if ( getM6ResultsCode <= 0 ){
                	Log.d("Anderson", "getM6ResultsCode return " + getM6ResultsCode);
                   break; // web page access error 
                } else {
                	totalPastHistResultNum += getM6ResultsCode;
                }
            	loopCount++;
            	if ( loopCount > 4 ){
            		break;
                }
            	
            } while ( totalPastHistResultNum < MAX_NUM_HISTORY_DATA ); 
            Log.d("Anderson", "There are totally " + totalPastHistResultNum + " results" );   
            return historyResults;
        } 
       
    M6Result getLatestDetailedResult(){
        String hkjcM6Url = "http://bet.hkjc.com/marksix/index.aspx?lang=en";
        int resultContentStartIdx = -1; 
        M6Result m6DrawResult = new M6Result();
        String lastResultContentStr = null;
        NetworkConnector myConnector = new NetworkConnector();
        
        String webPageStr =  myConnector.getWebPageContent(hkjcM6Url);
        if ( webPageStr == null){
            return null;
        }
        resultContentStartIdx = webPageStr.indexOf("Last Draw");
        if ( resultContentStartIdx == -1 ) {
            return null;
        }
        lastResultContentStr = webPageStr.substring(resultContentStartIdx);
        
        // Step I : get the six ball number 
        Pattern ballImagePattern = Pattern.compile("no_[0-9]+.gif");
        Matcher webPageMatcher = ballImagePattern.matcher(lastResultContentStr);
        ArrayList<Integer> tmpList = new ArrayList<Integer>();
        while( webPageMatcher.find() ){
       	    String matcherPatternStr = webPageMatcher.group();
       	    String digitStr = matcherPatternStr.substring(3,5);
       	    int s = Integer.parseInt(digitStr);
       	    tmpList.add(s); 
        }
        m6DrawResult.setBallResults(tmpList);
        
        Log.d(TAG,"hihi " + tmpList.toString() );
        
        
       // Step II : get the drawing times in a year 
       int dataStrPos1 =  lastResultContentStr.indexOf("Draw Number");
       int dataStrPos2 =  lastResultContentStr.indexOf("Draw Date");
       //String step2String = 
       if ( dataStrPos1 != -1 && dataStrPos2 != -1 && dataStrPos2 > dataStrPos1 ){ 
           String drawTimeInYearStr =  lastResultContentStr.substring(dataStrPos1, dataStrPos2);
           Pattern timeInYearPattern = Pattern.compile("[0-9][0-9]/[0-9][0-9][0-9]");
           Matcher drawTimeInYearStrMatcher = timeInYearPattern.matcher(lastResultContentStr);
           while( drawTimeInYearStrMatcher.find() ){
          	   String matcherPatternStr = drawTimeInYearStrMatcher.group();
          	   Log.d(TAG,"Time in year string is " + matcherPatternStr);
          	   m6DrawResult.setDrawTimesInYearStr(matcherPatternStr);
               break;
           }  
       }
       
       // Step III : get the drawing date 
       if (dataStrPos2 != -1 ) {
    	   String drawDateStr =  lastResultContentStr.substring(dataStrPos2);
    	   Pattern drawDatePattern = Pattern.compile("[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]");
           Matcher drawDateMatcher = drawDatePattern.matcher(lastResultContentStr);
           while( drawDateMatcher.find() ){
          	   String matcherPatternStr = drawDateMatcher.group();
          	   Log.d(TAG,"draw date String is " + matcherPatternStr);
          	   m6DrawResult.setDrawDateString(matcherPatternStr);
               break;
           }
       }
       
       
       // Step IV : get the 1st / 2nd / 3rd prize money
       int dataStrPos3 =  lastResultContentStr.indexOf("1st");
       int dataStrPos4 =  lastResultContentStr.indexOf("2nd");
       int dataStrPos5 =  lastResultContentStr.indexOf("3rd");
       
       if ( dataStrPos3 != -1 && dataStrPos4 != -1 && dataStrPos5 != -1 ){
    	   String firstPrizeStr =  lastResultContentStr.substring(dataStrPos3+1,dataStrPos4);
    	   String secondPrizeStr =  lastResultContentStr.substring(dataStrPos4+1,dataStrPos5);
    	   String thirdPrizeStr =  lastResultContentStr.substring(dataStrPos5+1);
    	   //Log.d(TAG,"firstPrizeStr is " + firstPrizeStr );
    	   //Log.d(TAG,"secondPrizeStr is " + secondPrizeStr );
    	   //Log.d(TAG,"thirdPrizeStr is " + thirdPrizeStr );
    	   Pattern firstPrizePattern = Pattern.compile("\\$[,0-9]+");
           Matcher firstPrizeMatcher = firstPrizePattern.matcher(firstPrizeStr);
           while( firstPrizeMatcher.find() ){
          	   String firstPrizeMacthedStr = firstPrizeMatcher.group();
          	   Log.d(TAG,"First Prize String is " + firstPrizeMacthedStr);
          	   m6DrawResult.setFirstPrizeStr(firstPrizeMacthedStr);
               break;
           }
           
           Pattern secondPrizePattern = Pattern.compile("\\$[,0-9]+");
           Matcher secondPrizeMatcher = secondPrizePattern.matcher(secondPrizeStr);
           while( secondPrizeMatcher.find() ){
          	   String secondPrizeMatchedStr = secondPrizeMatcher.group();
          	   Log.d(TAG,"Second Prize String is " + secondPrizeMatchedStr);
          	   m6DrawResult.setSecondPrizeStr(secondPrizeMatchedStr);
               break;
           }
           
           
           Pattern thirdPrizePattern = Pattern.compile("\\$[,0-9]+");
           Matcher thirdPrizeMatcher = thirdPrizePattern.matcher(thirdPrizeStr);
           while( thirdPrizeMatcher.find() ){
          	   String thirdPrizeMatchedStr = thirdPrizeMatcher.group();
          	   Log.d(TAG,"Thrid Prize String is " + thirdPrizeMatchedStr);
          	   m6DrawResult.setThirdPrizeStr(thirdPrizeMatchedStr);
               break;
           }
    	   
       }
       
       return m6DrawResult;
    }
	//@Override
	HashMap<Date, List<Integer>> getHistoryNumbers2() {
		// TODO Auto-generated method stub
		historyResults = new HashMap<Date,List<Integer>>();
		NetworkConnector myConnector = new NetworkConnector();
		String webPageStr =  myConnector.getWebPageContent(urlHistoryString);
		if ( webPageStr == null ) {
			Log.d("Anderson", "web page string is null!");
			return null;
		} 
		Log.d("Anderson", " " + webPageStr);
		StringTokenizer st = new StringTokenizer(webPageStr,"Results_Detail");
		int i=0; 
		while (st.hasMoreTokens()){
            Log.d("Anderson","Enter here "  ); 
			List<Integer> tmpList = new ArrayList<Integer>();
			if( i>0){ 
                 Pattern ballImagePattern = Pattern.compile("no_[0-9]+_s.gif");
                 Matcher webPageMatcher = ballImagePattern.matcher(st.nextToken());
                 while( webPageMatcher.find() ){
                	 String matcherPatternStr = webPageMatcher.group();
                	 String digitStr = matcherPatternStr.substring(3,5);
                	 int s = Integer.parseInt(digitStr);
                	 tmpList.add(s);
                 }
                 Date tmpDate = new Date();
                 tmpDate.setTime(i);
                 historyResults.put(tmpDate,tmpList);
             } 
             i++;
		}
        return historyResults;
	}
	
	//@Override
	List<M6Result> getHistoryNumbers3() {
		
		
		int totalPastHistResultNum =0;
    	int MAX_NUM_HISTORY_DATA = 50;
    	globalUniqueCounter=0;
    	NetworkConnector myConnector = new NetworkConnector();
    	historyResults = new HashMap<Date,List<Integer>>();
    	
    	DateInfo myDateInfo = new DateInfo();
        myDateInfo.setupDateInfoFromCurDate();
        int getM6ResultsCode = -1;
        int loopCount = 0; 
        do {
        	// construct the link address string 
        	
        	String urlString = null;
        	// the current month is not enough 
        	if ( loopCount == 0 ){
        		myDateInfo.setupDateInfoFromCurDate();
        	} else {
        		myDateInfo.setPrevMonth();
        	}
        	urlString = String.format( urlHistoryStringLongList , myDateInfo.getMonthStr(),myDateInfo.getYearStr() );
        	Log.d("Andrson" , "urlString is " + urlString);
        	getM6ResultsCode = parseOneWebPageM6Obj(myConnector, urlString);
            if ( getM6ResultsCode <= 0 ){
            	Log.d("Anderson", "getM6ResultsCode return " + getM6ResultsCode);
               break; // web page access error 
            } else {
            	totalPastHistResultNum += getM6ResultsCode;
            }
        	loopCount++;
        	if ( loopCount > 4 ){
        		break;
            }
        	
        } while ( totalPastHistResultNum < MAX_NUM_HISTORY_DATA ); 
        Log.d("Anderson", "There are totally " + totalPastHistResultNum + " results" );   
        return historyResultsObj;
        
		/*
		// TODO Auto-generated method stub
		List<M6Result> historyResults = new ArrayList<M6Result>();
		NetworkConnector myConnector = new NetworkConnector();
		String webPageStr =  myConnector.getWebPageContent(urlHistoryString);
		if ( webPageStr == null ) {
			Log.d("Anderson", "web page string is null!");
			return null;
		} 
		Log.d("Anderson", " " + webPageStr);
		StringTokenizer st = new StringTokenizer(webPageStr,"Results_Detail");
		int i=0; 
		while (st.hasMoreTokens()){
            Log.d("Anderson","Enter here "  ); 
			List<Integer> tmpList = new ArrayList<Integer>();
			if( i>0){ 
                 Pattern ballImagePattern = Pattern.compile("no_[0-9]+_s.gif");
                 Matcher webPageMatcher = ballImagePattern.matcher(st.nextToken());
                 while( webPageMatcher.find() ){
                	 String matcherPatternStr = webPageMatcher.group();
                	 String digitStr = matcherPatternStr.substring(3,5);
                	 int s = Integer.parseInt(digitStr);
                	 tmpList.add(s);
                 }
                 Date tmpDate = new Date();
                 tmpDate.setTime(i);
                 historyResults.add(new M6Result(tmpList));  
             } 
             i++;
		}
	    
        return historyResults;
        */
	}
        
        private class DateInfo {
            private int year;
            private int month;
            String mFormattedDate = " ";
            void setupDateInfoFromCurDate(){
                // todo : init variable 
            	
            	Date date = new Date();
            	SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            	mFormattedDate = format.format(date);
            	month = Integer.parseInt(mFormattedDate.substring(0,2));
            	year  = Integer.parseInt(mFormattedDate.substring(6,10));
            }
            void setPrevMonth(){
                month--;
                if ( month == 0 ){
                     month=12;
                     year--;
                } 
            }
            String getMonthStr() {
            	 return Integer.toString(month); 
                 
            } 
            String getYearStr() {
            	return Integer.toString(year); 
            } 
             
        }

}

