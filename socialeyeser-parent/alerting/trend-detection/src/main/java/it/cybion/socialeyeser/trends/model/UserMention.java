package it.cybion.socialeyeser.trends.model;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class UserMention {
    
    public int[] indices;
    public String name;
    public String screenName;
    public long id;
    
    public UserMention() {
    
    }

    public int[] getIndices() {

        return indices;
    }

    public void setIndices(int[] indices) {

        this.indices = indices;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getScreenName() {

        return screenName;
    }

    public void setScreenName(String screenName) {

        this.screenName = screenName;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }
}
