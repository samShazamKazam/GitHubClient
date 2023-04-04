package com.gitHubClient.issue;

import java.util.*;

public class SummaryBuilder {

    int max_count = 0;      // initialize the count to keep track of the max number of issues
    Date max_date = new Date(Long.MIN_VALUE);  //The minimum date as our initial value

    public SummaryBuilder() {
    }

    public Summary buildSummary(List<Issue> issues){
        Collections.sort(issues, Comparator.comparing(Issue::getCreated_at));

        List<Issue> list =categorizeByDate(issues);

        //Find the latest day with the maximum number of issues
        Map<String, Integer> occurences = new HashMap<>();
        for (Issue i : list){
            int count = occurences.getOrDefault(i.getRepository(), 0);
            occurences.put(i.getRepository(), count+1);
        }

        TopDay topday = new TopDay(max_date, occurences);

       return new Summary(issues, topday);
    }

    private Date zeroOutTime(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.AM_PM, Calendar.AM);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR, 0);
        return c.getTime();
    }

    /**
     *
     * @param issues A list of issues sorted by created_at date
     * @return A list of issues for that date the had the most issues created
     */
    private List<Issue> categorizeByDate(List<Issue> issues){

        Map<Date, List<Issue>> map = new HashMap<>(issues.size());
        for (Issue issue: issues){
            Date date = zeroOutTime(issue.getCreated_at());

            List<Issue> list = map.get(date);
            if (list == null){
                list= new ArrayList<Issue>();
                map.put(date, list);
            }
            list.add(issue);

            //Since the issues are sorted by date, a later date that
            // has the same number of issues as prior dates will always
            // be picked as max_date. Of course, that means
            if (max_count <= list.size()){
                max_count = list.size();
                max_date = date;
            }
        }
        return map.getOrDefault(max_date, new ArrayList<>());
    }

}
