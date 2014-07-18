package it.cybion.socialeyeser.trends.model;

import java.util.Date;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class Tweet {
    
    public Long id;
    public Date createdAt;
    public Coordinates coordinates;
    public Entities entities;
    public String inReplyToScreenName;
    public Long inReplyToStatusId;
    public Long inReplyToUserId;
    // public Place place;
    // public boolean favorited;
    // public boolean retweet;
    // public boolean truncated;
    // public boolean possiblySensitive;
    public long retweetCount;
    public long favoriteCount;
    public Tweet retweetedStatus;
    public String source;
    public String text;
    public User user;
    public String lang;
    // public Object scopes;
    // public long currentUserRetweetId;
    
    public double sentiment;
    public double userInfluence;
    
    public Tweet() {
    
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Date getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {

        this.createdAt = createdAt;
    }

    public Coordinates getCoordinates() {

        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {

        this.coordinates = coordinates;
    }

    public Entities getEntities() {

        return entities;
    }

    public void setEntities(Entities entities) {

        this.entities = entities;
    }

    public String getInReplyToScreenName() {

        return inReplyToScreenName;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {

        this.inReplyToScreenName = inReplyToScreenName;
    }

    public Long getInReplyToStatusId() {

        return inReplyToStatusId;
    }

    public void setInReplyToStatusId(Long inReplyToStatusId) {

        this.inReplyToStatusId = inReplyToStatusId;
    }

    public Long getInReplyToUserId() {

        return inReplyToUserId;
    }

    public void setInReplyToUserId(Long inReplyToUserId) {

        this.inReplyToUserId = inReplyToUserId;
    }

    public long getRetweetCount() {

        return retweetCount;
    }

    public void setRetweetCount(long retweetCount) {

        this.retweetCount = retweetCount;
    }

    public long getFavoriteCount() {

        return favoriteCount;
    }

    public void setFavoriteCount(long favoriteCount) {

        this.favoriteCount = favoriteCount;
    }

    public Tweet getRetweetedStatus() {

        return retweetedStatus;
    }

    public void setRetweetedStatus(Tweet retweetedStatus) {

        this.retweetedStatus = retweetedStatus;
    }

    public String getSource() {

        return source;
    }

    public void setSource(String source) {

        this.source = source;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public String getLang() {

        return lang;
    }

    public void setLang(String lang) {

        this.lang = lang;
    }

    public double getSentiment() {

        return sentiment;
    }

    public void setSentiment(double sentiment) {

        this.sentiment = sentiment;
    }

    public double getUserInfluence() {

        return userInfluence;
    }

    public void setUserInfluence(double userInfluence) {

        this.userInfluence = userInfluence;
    }
}
