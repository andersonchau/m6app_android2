package com.example.m6apps;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public abstract class M6ResultRetriever {
    abstract List<Integer> getLatestNumbers();
    
    abstract HashMap<Date, List<Integer>> getHistoryNumbers2();
    
    abstract HashMap<Date,List<Integer> > getHistoryNumbers(); 
    
    abstract M6Result getLatestDetailedResult();    
    
    abstract List<M6Result> getHistoryNumbers3();
}
