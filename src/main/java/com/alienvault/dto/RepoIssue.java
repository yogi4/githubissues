package com.alienvault.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.util.Key;

import java.util.Date;

/**
 * Data Transfer object for organizing issues
 * Using Key annotation to parse JSON directly to this object
 * I could use binding too but wanted to try out Google http Client parse keys
 */

public class RepoIssue {
    /** ID **/
    @Key
    private int id;
    /**
     * Issue State
     * use state for json object
     */
    @Key("state")
    private String issue_state;

    /**
     * Issue created at
     *
     */
    @Key
    private String created_at;


    /**
     * Title
     */
    @Key("title")
    private String title;

    /**
     * repository
     */
    @Key
    private String repository;

    /**
     * get ID
     * @return int id
     * @see setId(int id)
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIssue_state() {
        return issue_state;
    }

    public void setIssue_state(String issue_state) {
        this.issue_state = issue_state;
    }
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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


}
