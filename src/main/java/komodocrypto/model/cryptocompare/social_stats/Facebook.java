package komodocrypto.model.cryptocompare.social_stats;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Facebook {

    int id;
    int time;
    String currency;
    int likes;
    @JsonProperty("is_closed")
    String isClosed;
    @JsonProperty("talking_about")
    int talkingAbout;
    String name;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }

    public int getTalkingAbout() {
        return talkingAbout;
    }

    public void setTalkingAbout(int talkingAbout) {
        this.talkingAbout = talkingAbout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
