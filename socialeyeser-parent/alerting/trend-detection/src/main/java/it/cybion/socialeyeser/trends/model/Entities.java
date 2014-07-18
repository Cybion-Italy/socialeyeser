package it.cybion.socialeyeser.trends.model;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class Entities {
    
    public Url[] urls = new Url[0];
    public HashTag[] hashtags = new HashTag[0];
    public UserMention[] userMentions = new UserMention[0];
    public MediaEntity[] mediaEntities = new MediaEntity[0];
    
    public Entities() {
    
    }

    public Url[] getUrls() {

        return urls;
    }

    public void setUrls(Url[] urls) {

        this.urls = urls;
    }

    public HashTag[] getHashtags() {

        return hashtags;
    }

    public void setHashtags(HashTag[] hashtags) {

        this.hashtags = hashtags;
    }

    public UserMention[] getUserMentions() {

        return userMentions;
    }

    public void setUserMentions(UserMention[] userMentions) {

        this.userMentions = userMentions;
    }

    public MediaEntity[] getMediaEntities() {

        return mediaEntities;
    }

    public void setMediaEntities(MediaEntity[] mediaEntities) {

        this.mediaEntities = mediaEntities;
    }
}
