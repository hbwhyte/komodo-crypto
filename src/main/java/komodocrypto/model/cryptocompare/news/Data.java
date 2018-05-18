package komodocrypto.model.cryptocompare.news;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

    @JsonIgnore
    int id;
    @JsonProperty("id")
    int articleId;
    @JsonProperty("published_on")
    int publishedOn;
    String title;
    String url;
    String body;
    String tags;
    String categories;

    public int getId() {
        return id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(int publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
