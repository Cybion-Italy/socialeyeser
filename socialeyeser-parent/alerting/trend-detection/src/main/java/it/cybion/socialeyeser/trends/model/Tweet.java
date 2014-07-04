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
    public Place place;
    public boolean favorited;
    public boolean retweet;
    public boolean truncated;
    public boolean possiblySensitive;
    public long retweetCount;
    public long favoriteCount;
    public Tweet retweetedStatus;
    public String source;
    public String text;
    public User user;
    public String lang;
    public Object scopes;
    public long currentUserRetweetId;
    
    public Tweet() {
    
    }
}
