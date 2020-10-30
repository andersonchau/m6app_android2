package com.example.m6apps;

public class M6ResultFactory {
     public static final int ACCESSOR_TYPE_WEB_PARSER=1;
         
     public static M6ResultRetriever getM6ResultRetriever(int type){
         switch ( type ){
         case ACCESSOR_TYPE_WEB_PARSER:
        	 return new M6ResultWebPageRetriever();
         default:
        	 return null;
         } 
        
     }
}


