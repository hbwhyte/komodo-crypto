package komodocrypto.model.cryptocompare.social_stats;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Twitter {

    int id;
    int time;
    String currency;
    int following;
    int accountCreation;
    String name;
    int lists;
    int statuses;
    int favourites;
    int followers;
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

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getAccountCreation() {
        return accountCreation;
    }

    public void setAccountCreation(int accountCreation) {
        this.accountCreation = accountCreation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLists() {
        return lists;
    }

    public void setLists(int lists) {
        this.lists = lists;
    }

    public int getStatuses() {
        return statuses;
    }

    public void setStatuses(int statuses) {
        this.statuses = statuses;
    }

    public int getFavourites() {
        return favourites;
    }

    public void setFavourites(int favourites) {
        this.favourites = favourites;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
