package com.alienvault

import com.alienvault.core.IssueProcessor
import com.alienvault.core.TopDayIssueProcessor
import com.alienvault.dto.RepoIssue
import com.alienvault.dto.TopDayIssue
import spock.lang.Shared
import spock.lang.Specification

class TopDayIssueProcessorTest extends Specification {
    @Shared
    def repoIssues     = new ArrayList<RepoIssue>();

    @Shared
    def topDayIssue     = new TopDayIssue();

    def setup() {
    }

    def setupSpec() {

        def issue1         =  new RepoIssue();
        issue1.created_at = "2017-04-18T01:35:00Z"
        issue1.repository = "att/AAF"
        repoIssues.add(issue1)

        def issue2         = new RepoIssue();
        issue2.created_at = "2017-04-18T01:35:00Z"
        issue1.repository = "att/AJC"
        repoIssues.add(issue2)

        def issue3         = new RepoIssue();
        issue3.created_at = "2018-10-31T05:29:49Z"
        issue3.repository = "att/AAF"
        repoIssues.add(issue3)

        def issue4         = new RepoIssue();
        issue4.repository = "att/ICF"
        issue4.setCreated_at("2018-10-31T05:29:49Z")
        repoIssues.add(issue4)

        def issue5         = new RepoIssue();
        issue5.setRepository("att/USP")
        issue5.setCreated_at("22018-10-31T05:29:49Z")
        repoIssues.add(issue5)

        def issue6         = new RepoIssue();
        issue6.setRepository("att/IRC")
        issue6.setCreated_at("2019-01-09T05:29:49Z")
        repoIssues.add(issue6)

        def issue7         = new RepoIssue();
        issue7.setRepository("att/IRC")
        issue7.setCreated_at("2019-01-09T05:29:49Z")
        repoIssues.add(issue7)

        def issue8         = new RepoIssue();
        issue8.setCreated_at("2019-01-09T05:29:49Z")
        issue8.setRepository("att/USP")
        repoIssues.add(issue8)


    }

    def cleanup() {
    }


    def "test processTopdayIssues max day"() {
        given:
        repoIssues
        when:
        def topDayIssueProcessor = new TopDayIssueProcessor();
        topDayIssue = topDayIssueProcessor.processTopdayIssues(repoIssues)
        then:
        topDayIssue.day == "2019-01-09"
        }
    def "test processTopdayIssues occurences"() {
         given:
         repoIssues
         when:
         def topDayIssueProcessor = new TopDayIssueProcessor();
         topDayIssue = topDayIssueProcessor.processTopdayIssues(repoIssues)
         then:
         topDayIssue.occurences.containsKey("att/IRC")
         topDayIssue.occurences.get("att/IRC") == 2
    }

    def "test processTopdayIssues occurences with added repo issues  "() {
        given:
        repoIssues
        def issue9         = new RepoIssue();
        issue9.setRepository("att/USP")
        issue9.setCreated_at("2018-10-31T05:29:49Z")
        when:
        repoIssues.add(issue9)
        def topDayIssueProcessor = new TopDayIssueProcessor();
        topDayIssue = topDayIssueProcessor.processTopdayIssues(repoIssues)
        then:
        topDayIssue.day == "2018-10-31"
    }

    def "test processTopdayIssues with empty repolist"()
    {
        given:
        def emptyRepos = new ArrayList<RepoIssue>();

        when:
        def topDayIssueProcessor = new TopDayIssueProcessor();
        topDayIssue = topDayIssueProcessor.processTopdayIssues(emptyRepos)
        then:
        topDayIssue.day ==""
        topDayIssue.occurences.isEmpty()

    }

    def "test processTopdayIssues with API data"(){
        given:
        def repos = new String[2];
        repos[0] = "att/AAF";
        repos[1] = "octocat/Hello-World";
        def issueProcessor = new IssueProcessor();
        repoIssues = issueProcessor.processReposIssues(repos);

        when:
        def topDayIssueProcessor = new TopDayIssueProcessor();
        topDayIssue = topDayIssueProcessor.processTopdayIssues(repoIssues)

        then:
        topDayIssue.occurences.containsKey("octocat/Hello-World")
        topDayIssue.occurences.containsValue(0)


    }


}
