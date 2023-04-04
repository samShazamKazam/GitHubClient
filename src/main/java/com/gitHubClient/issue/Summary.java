package com.gitHubClient.issue;

import java.util.List;

public class Summary {
    private List<Issue> issues;
    private TopDay top_day;

    public Summary(List<Issue> issues, TopDay topday) {
        this.issues = issues;
        this.top_day = topday;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public TopDay getTop_day() {
        return top_day;
    }

    public void setTop_day(TopDay top_day) {
        this.top_day = top_day;
    }

}
