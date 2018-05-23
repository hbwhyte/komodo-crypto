package komodocrypto.model.cryptocompare.social_stats;

import com.fasterxml.jackson.annotation.JsonProperty;

public class List {

    @JsonProperty("created_at")
    int createdAt;
    @JsonProperty("open_total_issues")
    int openTotalIssues;
    @JsonProperty("closed_total_issues")
    int closedTotalIssues;
    @JsonProperty("last_update")
    int lastUpdate;
    String url;
    @JsonProperty("closed_issues")
    String closedIssues;
    @JsonProperty("closed_pull_issues")
    String closedPullIssues;
    String fork;
    @JsonProperty("last_push")
    String lastPush;
    @JsonProperty("open_pull_issues")
    String openPullIssues;
    String language;
    int subscribers;
    @JsonProperty("open_issues")
    int openIssues;
}
