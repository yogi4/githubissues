package com.alienvault.core;

import com.alienvault.dto.RepoIssue;
import com.alienvault.utilities.CustomUrl;
import com.alienvault.utilities.GitIssuesProperties;
import com.alienvault.utilities.IssueComparator;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Core Class Responsible for making Http calls and processing JSON into Array list of issues
 * Used Google Http Client
 * There are several Rest Clients out there , but decided to go with this Client for sake of simplicity
 */
public class IssueProcessor {
    Properties properties = GitIssuesProperties.loadProperties();

    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static JsonFactory JSON_FACTORY = new JacksonFactory();

     /**
     * OverRide method for string Arrays
     * @param userRepos String[] of user repos in format of owner/repository
     * @return ist of Repo Issues sorted by creation date
     */
    public List<RepoIssue> processReposIssues(String[] userRepos) {

        ArrayList<String> stringList = new ArrayList<>(Arrays.asList(userRepos));
        return processReposIssues(stringList);
    }

    /**
     * Method to process user repos
     * This methods makes rest api call to Github and parses the result set into
     * List of Repo Issues
     * @param userRepos
     * @return List of Repo Issues sorted by creation date
     */
    @SuppressWarnings("JavaDoc")
    public List<RepoIssue> processReposIssues(List<String> userRepos) {
        HttpRequestFactory requestFactory
                = HTTP_TRANSPORT.createRequestFactory(
                (HttpRequest request) -> request.setParser(new JsonObjectParser(JSON_FACTORY)));

        Type type = new TypeToken<List<RepoIssue>>() {}.getType();
        List<RepoIssue> issues =new ArrayList<RepoIssue>();
        for(String userRepo : userRepos )
        {
            //This forms the URL with token and github link'

            String gitUrl = properties.getProperty("url");
            String token = properties.getProperty("token");
            String tempUrl = CustomUrl.urlBuilder(userRepo,gitUrl,token);
            //String tempUrl = CustomUrl.urlBuilder(userRepo);
            CustomUrl  url = new CustomUrl(tempUrl);
            url.per_page = 10;
            List<RepoIssue> singleRepoIssues = Collections.EMPTY_LIST;
            try
            {
                HttpRequest request = requestFactory.buildGetRequest(url);
                // This is where the fun Happens
                // This line of code could be split into making an api call
                // And Parsing json content into Object
                //But it's simpler and efficient leveraging Google HTTP Client API and parser streaming library

                (singleRepoIssues = (List<RepoIssue>) request
                        .execute()
                        .parseAs(type)).forEach(repoIssue -> {
                    repoIssue.setRepository(userRepo);
                });

            } catch (IOException ioe)
            {
              System.out.println(ioe.getMessage());
              //TODO: Logging
            }

            issues.addAll(singleRepoIssues);
        }
        issues.sort(new IssueComparator());
        //Collections.sort(issues, new IssueComparator());
        return issues;

    }
}
