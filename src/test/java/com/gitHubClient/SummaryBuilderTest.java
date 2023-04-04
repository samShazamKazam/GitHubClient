package com.gitHubClient;

import com.gitHubClient.issue.SummaryBuilder;
import com.gitHubClient.issue.Issue;
import com.gitHubClient.issue.Summary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.*;

public class SummaryBuilderTest {

    static List<Issue>  issues2 = new ArrayList<>();
    static List<Issue>  issues3 = new ArrayList<>();
    static List<Issue>  issues4 = new ArrayList<>();
    static Date todaysDate;
    static Date veryOldDate;

    @BeforeClass
    public static void setUp(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1999);
        veryOldDate = cal.getTime();
        todaysDate = new Date();

        //For Test testListOfIssuesWithSameDate
        issues2.add( new Issue(1, "open", "title1", "owner/repo1", todaysDate));
        issues2.add( new Issue(2, "open", "title2", "owner/repo2", todaysDate));
        issues2.add( new Issue(3, "open", "title3", "owner/repo3", todaysDate));
        issues2.add( new Issue(4, "open", "title4", "owner/repo4", todaysDate));

        //For Test testForLatestDate
        issues3.add( new Issue(1, "open", "title1", "owner/repo1", todaysDate));
        issues3.add( new Issue(2, "open", "title2", "owner/repo1", todaysDate));
        issues3.add( new Issue(3, "open", "title3", "owner/repo2", veryOldDate));
        issues3.add( new Issue(4, "open", "title4", "owner/repo2", veryOldDate));

        //For Test testForOneLatestDateAndTwoOldDates
        issues4.add( new Issue(1, "open", "title1", "owner/repo1", veryOldDate));
        issues4.add( new Issue(2, "open", "title2", "owner/repo1", todaysDate));
        issues4.add( new Issue(3, "open", "title3", "owner/repo2", veryOldDate));
        issues4.add( new Issue(4, "open", "title4", "owner/repo2", veryOldDate));
    }

    @Test
    public void testEmptyListOfIssues(){
        SummaryBuilder sb = new SummaryBuilder();
        Summary s = sb.buildSummary(new ArrayList<>());

        Gson gson = (new GsonBuilder())
                .setDateFormat("yyyy-MM-dd'T'hh:mm:ssz")
                .setPrettyPrinting()
                .create();
        String str = gson.toJson(s);
        System.out.println(str);

        assertEquals(s.getIssues().size(), 0);
        assertEquals(s.getTop_day().getDay(), "");
        assertEquals(s.getTop_day().getOccurrences().isEmpty(), true);
    }


    /**
     * Since four issues each from a different repo have the same date, each repo will appear in the top_day
     * section with count = 1
     */
    @Test
    public void testListOfIssuesWithSameDate(){
        SummaryBuilder sb = new SummaryBuilder();
        Summary s = sb.buildSummary(issues2);

        assertEquals(s.getIssues().size(), 4);
        assertEquals(s.getTop_day().getDay(), (new SimpleDateFormat("yyyy-MM-dd")).format(todaysDate));

        Map<String, Integer> occurences = s.getTop_day().getOccurrences();
        assertEquals(occurences.size(), 4);
        for (Issue issue: issues2){
            int count = occurences.get(issue.getRepository());
            assertEquals(1, count);
        }
    }

    /**
     * A test for a list of issues with two issues of one repo having today's date
     * and two issue of another repo having an old date.
     * We expect that the repo with last dates should show up
     */
    @Test
    public void testForLatestDate(){
        SummaryBuilder sb = new SummaryBuilder();
        Summary s = sb.buildSummary(issues3);

        assertEquals(4, s.getIssues().size());

        // Check for Sorting, The list of issues must be sorted in ascending order
        Issue[] temp = s.getIssues().toArray(new Issue[s.getIssues().size()]);
        List<Issue> tempList = new ArrayList<Issue>();
        for (Issue t : temp) tempList.add(t);
        Collections.sort(tempList, Comparator.comparing(Issue::getCreated_at));
        assertArrayEquals(s.getIssues().toArray(), tempList.toArray());

        // Check that the date is the latest one
        assertEquals(s.getTop_day().getDay(), (new SimpleDateFormat("yyyy-MM-dd")).format(todaysDate));

        // We should get the occurrences, we must have one repo and it must have count=2
        Map<String, Integer> occurences = s.getTop_day().getOccurrences();
        assertEquals(1, occurences.size());
        int count = occurences.get("owner/repo1");
        assertEquals(2, count);

    }

    @Test
    public void testForOneLatestDateAndTwoOldDates(){
        SummaryBuilder sb = new SummaryBuilder();
        Summary s = sb.buildSummary(issues4);

        assertEquals(4, s.getIssues().size());

        // Check that the date is the latest one
        assertEquals(s.getTop_day().getDay(), (new SimpleDateFormat("yyyy-MM-dd")).format(veryOldDate));

        // We should get the occurrences, we must have one repo and it must have count=2
        Map<String, Integer> occurences = s.getTop_day().getOccurrences();
        assertEquals(2, occurences.size());
        int count = occurences.get("owner/repo2");
        assertEquals(2, count);

        count = occurences.get("owner/repo1");
        assertEquals(1, count);
    }
}
