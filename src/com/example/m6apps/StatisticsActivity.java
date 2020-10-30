package com.example.m6apps;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class StatisticsActivity extends Activity {
    private MyApp myApp; 
    
    ExpandableHeightGridView gridView;
    
    ImageView buttonPanelView = null;
	ImageView renewButtonView = null;
    ImageAdapter myImgAdapter = null;
	boolean isProcessingHistoryQuery = false;
    @Override 
    protected void onCreate(Bundle savedInstanceState) { 

        super.onCreate(savedInstanceState); 
        //setContentView(R.layout.main);
        myApp = (MyApp) getApplication();   
        //Log.i("guoll", "OhterActivity receive the Label:"+myApp.getLabel()); //查看变量值是否修改了 
        super.onCreate(savedInstanceState);
        displayM6StatPage(true);
        isProcessingHistoryQuery = false;
        renewButtonView = (ImageView)findViewById(R.id.renew_button);
        
        renewButtonView.setOnTouchListener(new View.OnTouchListener() 
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
	                int totalWidth = renewButtonView.getWidth();
	                boolean isButtonPressed = isWithinButtonRange((float)x , (float)totalWidth);
	                if ( isButtonPressed == true && 
	                		isProcessingHistoryQuery == false ){
	                    new QueryM6HistoryTask().execute();
	                    Log.d("TAG", "Anderson Quitting!! ");
	                }
	                return true;
	            }
	            return false;
	        }
	    });
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
                    	Intent intent = new Intent(StatisticsActivity.this, ChooseBallActivity.class); 
                   	    startActivity(intent);
                   	    finish();
                    	
                    } else if ( whichButton == 1 ){
                    	//Toast.makeText(getApplicationContext(), "Second Button", Toast.LENGTH_SHORT).show();
                    	Intent intent = new Intent(StatisticsActivity.this, M6LuckyNumberActivity.class); 
                   	    startActivity(intent);
                   	    finish();
                    } else if ( whichButton == 2 ) {
                    	//Toast.makeText(getApplicationContext(), "Third Button", Toast.LENGTH_SHORT).show();
                    	//Intent intent = new Intent(StatisticsActivity.this, StatisticsActivity.class); 
                   	   // startActivity(intent);
                    	
                    } else if ( whichButton == 3) {
                    	//Toast.makeText(getApplicationContext(), "Forth Button", Toast.LENGTH_SHORT).show();
                    	finish();
                    }
                    return true;
                }
                return false;
            }
        });
	 } //onCreate
        
        
    
    void displayM6StatPage(boolean bInitial){
    	if ( bInitial == true ) { 
    	    setContentView(R.layout.statis_main);
		    
    	    gridView = (ExpandableHeightGridView) findViewById(R.id.myId);
    	    gridView.setExpanded(true);
    	    //gridView = (GridView) findViewById(R.id.gridView1);
		    M6ResultDatabaseHandler dbh = new M6ResultDatabaseHandler(StatisticsActivity.this, "M6HistDB", null, 0);
		    //int [] ballFreqArry =  dbh.getBallFrequencyArr();
		    int[] ballFreqArry= dbh.getBallFrequencyArr();
		    if ( ballFreqArry == null ){
		        ballFreqArry = new int[49];
		        for ( int i = 0 ; i < 49 ; i++ ){
		    	    ballFreqArry[i] = 0;
		        }
		    } 
		    
		    myImgAdapter = new ImageAdapter(this, ballFreqArry);
		    gridView.setAdapter(myImgAdapter);
		    
    	} else {
    		Log.d("Anderson", "HIHi herehere" );
    		M6ResultDatabaseHandler dbh = new M6ResultDatabaseHandler(StatisticsActivity.this, "M6HistDB", null, 0);
		    int [] ballFreqArry =  dbh.getBallFrequencyArr();
    		myImgAdapter.resetData(ballFreqArry);
    		myImgAdapter.notifyDataSetChanged();
    	}
    }
    
    // background task 
    private class QueryM6HistoryTask extends AsyncTask<Void, Integer, Void>{
  	  
  	  NetworkConnector myConnector;   
  	  boolean hasResult = false;
  	  
  	  @Override
  	  protected void onPreExecute() {
  	   // update the UI immediately after the task is executed
  	   super.onPreExecute();
  	 isProcessingHistoryQuery = true;
  	   hasResult = false; 
  	  }
  	      
  	  @Override
  	  protected Void doInBackground(Void... params) {

  	   myConnector = new NetworkConnector(StatisticsActivity.this);
  	   if ( myConnector.isNetworkAvailable() == false){
  	       return null;
  	   }
  	   M6ResultRetriever retriever = M6ResultFactory.getM6ResultRetriever(M6ResultFactory.ACCESSOR_TYPE_WEB_PARSER);
  	   if ( retriever == null ) {
  		   return null;
  	   }
  	   // parse the webpage to retrieve the data. 
  	   List<M6Result> m6HistoryResults = retriever.getHistoryNumbers3();
       // update the database 
  	   if( m6HistoryResults != null) {
  	      // TODO : insert it into database
  		 M6ResultDatabaseHandler dbh = new M6ResultDatabaseHandler(StatisticsActivity.this, "M6HistDB", null, 0);
  		 dbh.removeAllM6Records();
  		 for ( M6Result m6r : m6HistoryResults ){
  		    dbh.addM6Result(m6r);
  		 }
  		 hasResult = true;
  	   }
  	   
  	   return null;
  	  }
  	  
  	  @Override
  	  protected void onProgressUpdate(Integer... values) {
  	   super.onProgressUpdate(values);
  	  }
  	   
  	  @Override
  	  protected void onPostExecute(Void result) {
  	   super.onPostExecute(result);
  	   if ( hasResult == true ){
  	      displayM6StatPage(false);
  	   }
  	isProcessingHistoryQuery = false;
  	   
  	  }
   }
    
    public boolean isWithinButtonRange(float xPos, float fullWidth ){
		 Log.d("Anderson", "isWithinButtonRange " + xPos + " " + fullWidth );
		  if ( xPos/fullWidth > 0.33 && xPos/fullWidth <= 0.66 ){
			 return true;
		 } 
		  return false;
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




}
