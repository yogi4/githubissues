package com.alienvault;

import com.alienvault.core.IssueProcessor;
import com.alienvault.core.TopDayIssueProcessor;
import com.alienvault.dto.GitHubIssues;
import com.alienvault.dto.RepoIssue;
import com.alienvault.dto.TopDayIssue;
import com.alienvault.utilities.GitIssuesProperties;
import com.alienvault.utilities.ReposInputStream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * GitHub Issues -------------
 *
 * Create a program that generates a report about the the Issues belonging to a
 * list of github repositories ordered by creation time, and information about
 * the day when most Issues were created.
 *
 * Input: ----- List of 1 to n Strings with Github repositories references with
 * the format "owner/repository"
 *
 *
 * Output: ------ String representation of a Json dictionary with the following
 * content:
 *
 * - "issues": List containing all the Issues related to all the repositories
 * provided. The list should be ordered by the Issue "created_at" field (From
 * oldest to newest) Each entry of the list will be a dictionary with basic
 * Issue information: "id", "state", "title", "repository" and "created_at"
 * fields. Issue entry example: { "id": 1, "state": "open", "title": "Found a
 * bug", "repository": "owner1/repository1", "created_at":
 * "2011-04-22T13:33:48Z" }
 *
 * - "top_day": Dictionary with the information of the day when most Issues were
 * created. It will contain the day and the number of Issues that were created
 * on each repository this day If there are more than one "top_day", the latest
 * one should be used. example: { "day": "2011-04-22", "occurrences": {
 * "owner1/repository1": 8, "owner2/repository2": 0, "owner3/repository3": 2 } }
 *
 *
 * Output example: --------------
 *
 * {
 * "issues": [ { "id": 38, "state": "open", "title": "Found a bug",
 * "repository": "owner1/repository1", "created_at": "2011-04-22T13:33:48Z" }, {
 * "id": 23, "state": "open", "title": "Found a bug 2", "repository":
 * "owner1/repository1", "created_at": "2011-04-22T18:24:32Z" }, { "id": 24,
 * "state": "closed", "title": "Feature request", "repository":
 * "owner2/repository2", "created_at": "2011-05-08T09:15:20Z" } ], "top_day": {
 * "day": "2011-04-22", "occurrences": { "owner1/repository1": 2,
 * "owner2/repository2": 0 } } }
 *
 * --------------------------------------------------------
 *
 * You can create the classes and methods you consider. You can use any library
 * you need. Good modularization, error control and code style will be taken
 * into account. Memory usage and execution time will be taken into account.
 *
 * Good Luck!
 */
public class Main {

    /**
     * Couple of constructors if no parameters
     */

    /**
     * @param args String array with Github repositories with the format
     * "owner/repository"
     *
     */

     public static void main(String[] args) {


        //TODO : if args then read from args , if not use a repo file.
        Options options = new Options();

        Option input = new Option("i", "input", true, "input String Array example: Main -i att/AAF octocat/Hello-World ");
        input.setRequired(false);
        input.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(input);

        Option fileinput = new Option("f", "file-input", true, "input file path ");
        fileinput.setRequired(false);
        options.addOption(fileinput);

        Option exampleFile = new Option("e", "example-file-input", false, " Empty  use example from code ");
        exampleFile.setRequired(false);
        options.addOption(exampleFile);

        Option output = new Option("o", "output", true, "output file");
        output.setRequired(false);
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine cmd;

        List<String> inputRepos = new ArrayList<String>();
        String outputfile = "";


        try {
            cmd = parser.parse(options, args);

            if((!cmd.hasOption("i")&&!cmd.hasOption("f")&&!cmd.hasOption("e")))
            {
                formatter.printHelp(" Please use one of the options i , f , or e ( empty) one of options i or f is required Main", options);
                System.exit(1);
            }

            else if(cmd.hasOption("i"))
            {
                inputRepos = Arrays.asList(cmd.getOptionValues("input"));

            }
            else if(cmd.hasOption("f"))
            {
                String filePath = cmd.getOptionValue("file-input");
                ReposInputStream ris = new ReposInputStream();
                inputRepos = ris.repoList(filePath);
            }
            else if(cmd.hasOption("e"))
            {
                InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("repos.txt");

                ReposInputStream ris = new ReposInputStream();
                inputRepos = ris.repoList(inputStream);

            }

            if(cmd.hasOption("o"))
            {
                outputfile = cmd.getOptionValue("output");
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Main", options);
            System.exit(1);
        }

        GitHubIssues gitHubIssues = new GitHubIssues();
        IssueProcessor issueProcessor = new IssueProcessor();
        TopDayIssueProcessor topDayIssueProcessor = new TopDayIssueProcessor();

        List<RepoIssue> repoIssues = issueProcessor.processReposIssues(inputRepos);
        gitHubIssues.setIssues(repoIssues);
        TopDayIssue issuesTopDay = topDayIssueProcessor.processTopdayIssues(repoIssues);
        gitHubIssues.setTopDayIssue(issuesTopDay);
        ObjectMapper objectMapper = new ObjectMapper();
        String reposjson = null;
        try {
            reposjson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(gitHubIssues);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(reposjson);
        if(!outputfile.isEmpty())
        {
            try (PrintWriter out = new PrintWriter(new FileOutputStream(outputfile))) {
                out.println(reposjson);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
