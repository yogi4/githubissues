package com.alienvault.utilities;

import com.alienvault.dto.RepoIssue;

import java.util.Comparator;
import java.util.Date;

/* Comparator class to sort Issues by Date of creation*/
public class IssueComparator implements Comparator<RepoIssue> {

    /*public int compare(RepoIssue o1, RepoIssue o2) {

        if (o1.getCreatedDate().before(o2.getCreatedDate())) {
            return -1;
        } else if (o1.getCreatedDate().after(o2.getCreatedDate())) {
            return 1;
        } else {
            return 0;
        }
    } */


    public int compare(RepoIssue o1, RepoIssue o2) {

        Date d1 = DateUtility.formatCreatedDate(o1.getCreated_at());
        Date d2 = DateUtility.formatCreatedDate(o2.getCreated_at());
        if (d1.before(d2)) {
            return -1;
        } else if (d1.after(d2)) {
            return 1;
        } else {
            return 0;
        }
    }
}
