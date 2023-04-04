package com.gitHubClient.issue;

import java.util.Date;

public class Issue {
    long id;
    String state;
    String title;
    String repository;
    Date created_at;

    public Issue(long id, String state, String title, String repository, Date created_at) {
        this.id = id;
        this.state = state;
        this.title = title;
        this.repository = repository;
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
