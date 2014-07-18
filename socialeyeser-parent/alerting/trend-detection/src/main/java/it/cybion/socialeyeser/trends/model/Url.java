package it.cybion.socialeyeser.trends.model;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class Url {
    
    public String expandedUrl;
    public int[] indices;
    public String displayUrl;
    public String url;
    
    public Url() {
    
    }

    public String getExpandedUrl() {

        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {

        this.expandedUrl = expandedUrl;
    }

    public int[] getIndices() {

        return indices;
    }

    public void setIndices(int[] indices) {

        this.indices = indices;
    }

    public String getDisplayUrl() {

        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {

        this.displayUrl = displayUrl;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }
}
