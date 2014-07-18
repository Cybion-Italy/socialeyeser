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

    public String getIdStr() {

        return idStr;
    }

    public void setIdStr(String idStr) {

        this.idStr = idStr;
    }

    public Date getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {

        this.createdAt = createdAt;
    }

    public boolean isDefaultProfile() {

        return defaultProfile;
    }

    public void setDefaultProfile(boolean defaultProfile) {

        this.defaultProfile = defaultProfile;
    }

    public boolean isDefaultProfileImage() {

        return defaultProfileImage;
    }

    public void setDefaultProfileImage(boolean defaultProfileImage) {

        this.defaultProfileImage = defaultProfileImage;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public int getFavouritesCount() {

        return favouritesCount;
    }

    public void setFavouritesCount(int favouritesCount) {

        this.favouritesCount = favouritesCount;
    }

    public int getFollowersCount() {

        return followersCount;
    }

    public void setFollowersCount(int followersCount) {

        this.followersCount = followersCount;
    }

    public int getFriendsCount() {

        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {

        this.friendsCount = friendsCount;
    }

    public String getLang() {

        return lang;
    }

    public void setLang(String lang) {

        this.lang = lang;
    }

    public int getStatusesCount() {

        return statusesCount;
    }

    public void setStatusesCount(int statusesCount) {

        this.statusesCount = statusesCount;
    }

    public String getTimeZone() {

        return timeZone;
    }

    public void setTimeZone(String timeZone) {

        this.timeZone = timeZone;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public int getUtcOffset() {

        return utcOffset;
    }

    public void setUtcOffset(int utcOffset) {

        this.utcOffset = utcOffset;
    }

    public boolean isVerified() {

        return verified;
    }

    public void setVerified(boolean verified) {

        this.verified = verified;
    }
}
