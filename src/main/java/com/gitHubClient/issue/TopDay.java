package com.gitHubClient.issue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TopDay {

    String day;
    Map<String, Integer> occurrences = new HashMap<>();


    public TopDay(Date day, Map<String, Integer> occurrences) {
        if (occurrences.isEmpty()){
            this.day = "";
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.day = sdf.format(day);
        }
        this.occurrences = occurrences;

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Map<String, Integer> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Map<String, Integer> occurrences) {
        this.occurrences = occurrences;
    }
}
