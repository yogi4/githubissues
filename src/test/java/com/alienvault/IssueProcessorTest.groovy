package com.alienvault

import com.alienvault.core.IssueProcessor
import com.alienvault.dto.RepoIssue
import com.alienvault.utilities.DateUtility
import spock.lang.Shared
import spock.lang.Specification

class IssueProcessorTest extends Specification {
    @Shared
    def repoIssues     = new ArrayList<RepoIssue>();

    def issue1         = Mock(RepoIssue);
    def issue2         = Mock(RepoIssue);

    def setup() {


    }
    def setupSpec() {
        def repos = new String[2];
        repos[0] = "att/AAF";
        repos[1] = "octocat/Hello-World";
        def issueProcessor = new IssueProcessor();
        repoIssues = issueProcessor.processReposIssues(repos);
    }
    def cleanup() {
    }

    def "test ProcessReposIssues API Test with valid data"() {
        when:
        repoIssues
        then:
        assert repoIssues.isEmpty() == Boolean.FALSE
        assert repoIssues.get(0).repository == "att/AAF"
    }

    def "test ProcessReposIssues Data with multi repos data"() {
        when:
        repoIssues
        then:
        assert repoIssues.isEmpty() == Boolean.FALSE
        assert repoIssues.get(0).repository == "att/AAF"
        assert repoIssues.get(1).repository == "octocat/Hello-World"
    }

    def "test ProcessReposIssues Sorting by Date with multi repos data"() {
        when:
        repoIssues
        def dt = DateUtility.formatCreatedDate(repoIssues.get(0).created_at)
        def dt2= DateUtility.formatCreatedDate(repoIssues.get(1).created_at)
        then:
        assert dt.before(dt2)
    }


    def "test ProcessReposIssues API Test with invalid repo"() {
        given:
        def repos = new String[1];
        repos[0] = "yogi";

        when:
        def issueProcessor = new IssueProcessor();
        repoIssues = issueProcessor.processReposIssues(repos);

        then:
        assert repoIssues.isEmpty() == Boolean.TRUE
    }
    def "test ProcessReposIssues API Test with empty String array"() {
        given:
        def repos = new String[0];
        when:
        def issueProcessor = new IssueProcessor();
        repoIssues = issueProcessor.processReposIssues(repos);

        then:
        assert repoIssues.isEmpty() == Boolean.TRUE
    }

}
