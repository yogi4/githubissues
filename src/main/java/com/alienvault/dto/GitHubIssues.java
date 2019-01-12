package com.alienvault.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Helper DTO class for desired json output format
 * This helper class leverages RepoIssue and TopDayIssue
 *
 */

public class GitHubIssues {
    /**
     * Empty constructor for initializing
     */
    public GitHubIssues()
    {

    }
    /**
     * Constructor
     * @param issues
     * @param topDayIssue
     */
    public GitHubIssues(List<RepoIssue> issues , TopDayIssue topDayIssue) {
        this.topDayIssue = topDayIssue;
        this.issues = issues;
    }


    /**
     * List of issues sorted by created date
     */
    private List<RepoIssue> issues;



    /**
     * Top issues day
     * @see TopDayIssue
     */
    @JsonProperty("top_day")
    private  TopDayIssue topDayIssue;


    /**
     * Get issues
     * @return list of issues
     */
    public List<RepoIssue> getIssues() {
        return issues;
    }

    /**
     * set Issues
     * @param issues
     */
    public void setIssues(List<RepoIssue> issues) {
        this.issues = issues;
    }

    /**
     * Get Top day with occurences
     * @return top Day Issue
     * @see TopDayIssue
     */
    public TopDayIssue getTopDayIssue() {
        return topDayIssue;
    }

    /**
     * Set Top Day issue
     * @param topDayIssue
     */
    public void setTopDayIssue(TopDayIssue topDayIssue) {
        this.topDayIssue = topDayIssue;
    }
}
