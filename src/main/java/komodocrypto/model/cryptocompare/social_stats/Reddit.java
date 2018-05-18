package komodocrypto.model.cryptocompare.social_stats;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Reddit {

    int id;
    int time;
    String currency;
    @JsonProperty("posts_per_hour")
    double postsPerHour;
    @JsonProperty("comments_per_hour")
    double commentsPerHour;
    @JsonProperty("posts_per_day")
    double postsPerDay;
    @JsonProperty("comments_per_day")
    double commentsPerDay;
    String name;
    @JsonProperty("active_users")
    int activeUsers;
    @JsonProperty("community_creation")
    int communityCreation;
    int subscribers;
    @JsonProperty("Points")
    int points;

    public int getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getPostsPerHour() {
        return postsPerHour;
    }

    public void setPostsPerHour(double postsPerHour) {
        this.postsPerHour = postsPerHour;
    }

    public double getCommentsPerHour() {
        return commentsPerHour;
    }

    public void setCommentsPerHour(double commentsPerHour) {
        this.commentsPerHour = commentsPerHour;
    }

    public double getPostsPerDay() {
        return postsPerDay;
    }

    public void setPostsPerDay(double postsPerDay) {
        this.postsPerDay = postsPerDay;
    }

    public double getCommentsPerDay() {
        return commentsPerDay;
    }

    public void setCommentsPerDay(double commentsPerDay) {
        this.commentsPerDay = commentsPerDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(int activeUsers) {
        this.activeUsers = activeUsers;
    }

    public int getCommunityCreation() {
        return communityCreation;
    }

    public void setCommunityCreation(int communityCreation) {
        this.communityCreation = communityCreation;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
