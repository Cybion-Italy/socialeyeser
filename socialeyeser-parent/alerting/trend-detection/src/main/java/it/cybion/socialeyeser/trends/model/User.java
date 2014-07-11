package it.cybion.socialeyeser.trends.model;

import java.util.Date;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class User {
    
    // public Long id;
    public String idStr;
    public Date createdAt;
    public boolean defaultProfile;
    public boolean defaultProfileImage;
    public String description;
    // public Entities entities;
    public int favouritesCount;
    // public User[] followers = new User[0];
    public int followersCount;
    // public User[] friends = new User[0];
    public int friendsCount;
    // public boolean contributorsEnabled;
    // public boolean geoEnabled;
    // public boolean isProtected;
    public String lang;
    // public int listedCount;
    // public String location;
    // public String name;
    // public String screenName;
    public int statusesCount;
    public String timeZone;
    // public String profileImageUrl;
    public String url;
    public int utcOffset;
    public boolean verified;
    
    // public Tweet status;
    
    public User() {
    
    }
}
