package com.example.m6apps;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

public class DisplayResultActivity extends Activity {
	 
	Button statButton;
	Button quitButton;
	private MyApp myApp; 
	private ImageView buttonPanelView = null;
	 final int[] resIDArray = new int[]{
		 R.drawable.no_01_s,
		 R.drawable.no_02_s,
		 R.drawable.no_03_s,
		 R.drawable.no_04_s,
		 R.drawable.no_05_s,
		 R.drawable.no_06_s,
		 R.drawable.no_07_s,
		 R.drawable.no_08_s,
		 R.drawable.no_09_s,
		 R.drawable.no_10_s,
		 R.drawable.no_11_s,
		 R.drawable.no_12_s,
		 R.drawable.no_13_s,
		 R.drawable.no_14_s,
		 R.drawable.no_15_s,
		 R.drawable.no_16_s,
		 R.drawable.no_17_s,
		 R.drawable.no_18_s,
		 R.drawable.no_19_s,
		 R.drawable.no_20_s,
		 R.drawable.no_21_s,
		 R.drawable.no_22_s,
		 R.drawable.no_23_s,
		 R.drawable.no_24_s,
		 R.drawable.no_25_s,
		 R.drawable.no_26_s,
		 R.drawable.no_27_s,
		 R.drawable.no_28_s,
		 R.drawable.no_29_s,
		 R.drawable.no_30_s,
		 R.drawable.no_31_s,
		 R.drawable.no_32_s,
		 R.drawable.no_33_s,
		 R.drawable.no_34_s,
		 R.drawable.no_35_s,
		 R.drawable.no_36_s,
		 R.drawable.no_37_s,
		 R.drawable.no_38_s,
		 R.drawable.no_39_s,
		 R.drawable.no_40_s,
		 R.drawable.no_41_s,
		 R.drawable.no_42_s,
		 R.drawable.no_43_s,
		 R.drawable.no_44_s,
		 R.drawable.no_45_s,
		 R.drawable.no_46_s,
		 R.drawable.no_47_s,
		 R.drawable.no_48_s,
		 R.drawable.no_49_s
	 };
	
	 TextView tv;
	 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main); 
	    //tv = (TextView)findViewById(R.id.display_result);
	    tv = null;
	    new QueryM6Task().execute();
	    myApp = (MyApp) getApplication();
	    //statButton = (Button)findViewById(R.id.stat_button);
	    //quitButton = (Button)findViewById(R.id.quit_button);
	    //statButton.setOnClickListener(myhandler);
	    //quitButton.setOnClickListener(myhandler);
	    buttonPanelView = (ImageView)findViewById(R.id.bottom_panel);
	    
	    
	    buttonPanelView.setOnTouchListener(new View.OnTouchListener() 
        {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) 
            {
            	switch(event.getAction())
                {
                case MotionEvent.ACTION_DOWN:
                	return true;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    return true;
                case MotionEvent.ACTION_UP:
                	int x = (int)event.getX();
                    int y = (int)event.getY();
                    int totalWidth = buttonPanelView.getWidth();
                    int whichButton = getWhichDivision((float)x , (float)totalWidth);
                    if ( whichButton == 0 ){
                    	//Toast.makeText(getApplicationContext(), "First Button", Toast.LENGTH_SHORT).show();
                    	Intent intent = new Intent(DisplayResultActivity.this, ChooseBallActivity.class); 
                   	    startActivity(intent);
                   	    finish();
                    	
                    } else if ( whichButton == 1 ){
                    	//Toast.makeText(getApplicationContext(), "Second Button", Toast.LENGTH_SHORT).show();
                    	Intent intent = new Intent(DisplayResultActivity.this, M6LuckyNumberActivity.class); 
                   	    startActivity(intent);
                   	    finish();
                    } else if ( whichButton == 2 ) {
                    	//Toast.makeText(getApplicationContext(), "Third Button", Toast.LENGTH_SHORT).show();
                    	Intent intent = new Intent(DisplayResultActivity.this, StatisticsActivity.class); 
                   	    startActivity(intent);
                    	finish();
                    } else if ( whichButton == 3) {
                    	//Toast.makeText(getApplicationContext(), "Forth Button", Toast.LENGTH_SHORT).show();
                    	finish();
                    }
                    return true;
                }
                return false;
            }
        });
	 }
	 
     public int getWhichDivision(float xPos, float fullWidth ){
    	 Log.d("Anderson", "getWhichDivision " + xPos + " " + fullWidth );
    	 if ( xPos/fullWidth <= 0.25 ){
    	     return 0;	 
    	 } else if ( xPos/fullWidth > 0.25 && xPos/fullWidth <= 0.5 ){
    		 return 1;
    	 } else if ( xPos/fullWidth > 0.5 && xPos/fullWidth <= 0.75 ){
    		 return 2;
    	 } else { 
    		 return 3;
    	 } 
    	 
     }
		    	/*
		    	switch (v.getId()) {
		        case R.id.stat_button:
		        	Intent intent = new Intent(); 
		            intent.setClass(DisplayResultActivity.this, StatisticsActivity.class); 
		            startActivity(intent); 
		          break;
		        case R.id.quit_button:
		          finish();
		          break;
		        }
		        */
	
	 
	 @Override
	 protected void onResume() {
	     super.onResume();
	 }
	 
	 private class QueryM6Task extends AsyncTask<Void, Integer, Void>{
	  M6Result m6Result = null;
	  NetworkConnector myConnector;   
	  boolean hasResult = false;
	  ArrayList<Integer> myList;
	  HashMap<Date,List<Integer>> historyResults;
	     @Override
	  protected void onPreExecute() {
	   // update the UI immediately after the task is executed
	   super.onPreExecute();
	  }
	  @Override
	  protected Void doInBackground(Void... params) {

	   myConnector = new NetworkConnector(DisplayResultActivity.this);
	   if ( myConnector.isNetworkAvailable() == false){
	       return null;
	   }
	   M6ResultRetriever retriever = M6ResultFactory.getM6ResultRetriever(M6ResultFactory.ACCESSOR_TYPE_WEB_PARSER);
	   if ( retriever == null ) {
		   return null;
	   }
	   List<Integer> resultList = retriever.getLatestNumbers();
	   
	   if( resultList != null) {
	      myList = new ArrayList<Integer>();
	      int j = 0;
	      for( Integer i : resultList ){
	           myList.add(resultList.get(j));
	           j++;
	      }
	   }
	   
	   //historyResults = retriever.getHistoryNumbers();
	   m6Result = retriever.getLatestDetailedResult();
	   //retriever.getHistoryNumbers2();
	   return null;
	  }
	  
	  @Override
	  protected void onProgressUpdate(Integer... values) {
	   super.onProgressUpdate(values);
	    
	  }
	   
	  @Override
	  protected void onPostExecute(Void result) {
	   super.onPostExecute(result); 
	   String totalString = new String();
	   Log.d("Anderson","myList value is " + myList.toString() );
	   if ( myList.size() != 0){
		   //tv.setText(myList.toString());
		   boolean displayed = displayBallNumbers(myList);   
		   if ( displayed == true ){
		       //TextView loadingTV = (TextView)findViewById(R.id.loading_result);
		       //loadingTV.setVisibility(View.INVISIBLE);
		   }
	   }
	   if ( m6Result != null ){
	       String dateString1 = m6Result.getDrawDateString();
	       String dateString2 = m6Result.getDrawTimesInYearStr();
	       if ( dateString1 != null && dateString2 != null ){
	           TextView dateDrawTv = (TextView)findViewById(R.id.title_text_view3);
	           dateDrawTv.append("\t" + dateString1 + "(" + dateString2 +")");
	       }
	       
	       String firstPrizeStr = m6Result.getFirstPrizeStr();
	       String secondPrizeStr = m6Result.getSecondPrizeStr();
	       TextView firstPrizeStrTv = (TextView)findViewById(R.id.title_text_view4);
	       if ( firstPrizeStr != null ){
	    	   firstPrizeStrTv.append( "\t" + firstPrizeStr);
	       } else {
	    	   firstPrizeStrTv.append( "\t" + " - ");
	       }
	       TextView secondPrizeStrTv = (TextView)findViewById(R.id.title_text_view5);
	       if ( secondPrizeStr != null ){
	    	   secondPrizeStrTv.append( "\t" + secondPrizeStr);   
	       }else {
	    	   secondPrizeStrTv.append( "\t" + " - ");
		       
		   }
	       
	   } 
	   //myApp.setHistoryResults(historyResults);
	   
	   if (true) return;
	   //tv.setText("HIHIHI");
	   totalString += "\n";
	   
	   if ( historyResults == null ) return;
	   
	   //totalString = myList.toString();
	   totalString = " ";
	   for( List<Integer>  list : historyResults.values() ){
		   if ( list.size() == 7 ){
	           totalString +=  list.toString() + "\n";
		   }
	   }
	   
	   tv.setText(totalString);
	  }
	  
	  boolean displayBallNumbers(List<Integer> aDayResultList ) {	
          int counter = 0;
		  boolean result = false;
		  if ( aDayResultList == null || aDayResultList.size() == 0 ) return false;
		  LinearLayout iconLayout = (LinearLayout)findViewById(R.id.icon_layout);
		  if ( iconLayout == null ) return false;
		  for ( Integer ball : aDayResultList) {
		   if ( ball > 0 && ball <= 50  ){
			   result = true;
			  if ( counter < 6 ){
				  // normal numbers 
			     ImageView imageView = new ImageView(DisplayResultActivity.this);
			     imageView.setImageResource(resIDArray[ball-1]);
			     imageView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,5f));
			     iconLayout.addView(imageView);
			     
			      
			  } else {
				  ImageView imageView = new ImageView(DisplayResultActivity.this);
				  imageView.setImageResource(resIDArray[ball-1]);
				  imageView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,5f));
				  LinearLayout specialBallLayout = (LinearLayout)findViewById(R.id.special_layout);
				  specialBallLayout.addView(imageView);
				  
			  }
			  counter++;
		   }
		  }
		  if ( result == true ){ 
			  iconLayout.setVisibility(View.VISIBLE);
		  }
		  return result;
	   }
   }
	 
   
	 
}
	 

