package com.alienvault.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Map;

/**
 * Helper DTO for tracking Top Day Issues and organizing data for Desired JSON Output
 */
@JsonPropertyOrder({ "day", "occurences" })
public class TopDayIssue {

    /**
     * Represents the day top issues have occured.
     * Referencing this as string to minimize object conversions.
     *
     */
    @JsonProperty("day")
    private String day;

    /**
     * Represents number occurences of occurences of issues for a particular repository
     * String - Repository name in the format of owner/repository
     * Integer - number of occurences
     */
    @JsonProperty("occurences")
    private Map<String, Integer> occurences;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Map<String, Integer> getOccurences() {
        return occurences;
    }

    public void setOccurences(Map<String, Integer> occurences) {
        this.occurences = occurences;
    }
}
