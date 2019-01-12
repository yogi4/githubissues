package com.alienvault.core;

import com.alienvault.dto.RepoIssue;
import com.alienvault.dto.TopDayIssue;
import com.alienvault.utilities.IssueComparator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This Class is responsible for Processing Top Day Issues and
 */
public class TopDayIssueProcessor {

    /* THIS Method uses streams and syntactic sugar
    public TopDayIssue processTopdayIssues2(List<RepoIssue> issues)
    {
        //This compares the issue count, when same, looks for date/instant comparison
        Comparator<Map.Entry<Instant, List<RepoIssue>>> comparator = new Comparator<Map.Entry<Instant, List<RepoIssue>>>() {
            public int compare(Map.Entry<Instant, List<RepoIssue>> obj1, Map.Entry<Instant, List<RepoIssue>> obj2) {
                Integer size1 = obj1.getValue().size();
                Integer size2 = obj2.getValue().size();
                return  size1.equals(size2) ?  obj1.getKey().compareTo(obj2.getKey()) : size1.compareTo(size2);
            } };

        Map.Entry<Instant, List<RepoIssue>> maxIssuesByDay =
                issues.stream()
                        .collect(Collectors.groupingBy(w -> w.getCreatedDate().toInstant().truncatedTo(ChronoUnit.DAYS)))
                        .entrySet().stream().max(comparator).get();

        Map<String, Integer> issuesByRepo = maxIssuesByDay.getValue().stream().collect(Collectors.groupingBy(w -> w.getRepository(), Collectors.counting()));
        // TODO add empty zero count for repos that didn't get returned here.
        // could create a static map in Main class that contains a default zero count for every repo name and re-use it.
        TopDayIssue issuesTopDay = new TopDayIssue();
        issuesTopDay.setDay(maxIssuesByDay.getKey().toString());
        issuesTopDay.setOccurences(issuesByRepo);
        return issuesTopDay;
    }

    */
    public TopDayIssue processTopdayIssues(List<RepoIssue> issues)
    {
        TopDayIssue issuesTopDay = new TopDayIssue();
        if(issues.isEmpty())
        {
            issuesTopDay.setOccurences(Collections.emptyMap());
            issuesTopDay.setDay("");
            return issuesTopDay;
        }
        List<RepoIssue> reverseSortedIssues = new ArrayList<>();
        reverseSortedIssues.addAll(issues);
        reverseSortedIssues.sort(new IssueComparator().reversed());

        Map<String, Integer> occurences = new HashMap<>();
        Map<String, ArrayList<String>> occurencesByDayMap = new HashMap<>();
        int maxCount    = 0;
        String topDate  = "";
        for(RepoIssue issue : reverseSortedIssues)
        {
            String dt = issue.getCreated_at().substring(0,10); //or use Truncated capability and convert to string for processing
            ArrayList<String> dayIssueOccurences = occurencesByDayMap.get(dt);
            String currentRepo = issue.getRepository();
            if( dayIssueOccurences == null )
            {
                dayIssueOccurences = new ArrayList<String>();
            }
            dayIssueOccurences.add(currentRepo);
            occurencesByDayMap.put(dt,dayIssueOccurences);
            occurences.put(currentRepo,0);
            if(maxCount <= dayIssueOccurences.size())
            {
                maxCount = dayIssueOccurences.size();
                topDate  = dt;
            }
        }

        issuesTopDay.setDay(topDate);
        for(String str : occurencesByDayMap.get(topDate))
        {
            Integer count = occurences.get(str);
            if (count == null) {
                occurences.put(str, 1);
            }
            else{
                occurences.put(str, count+1);
            }
        }
        issuesTopDay.setOccurences(occurences);
        return issuesTopDay;
    }




}
