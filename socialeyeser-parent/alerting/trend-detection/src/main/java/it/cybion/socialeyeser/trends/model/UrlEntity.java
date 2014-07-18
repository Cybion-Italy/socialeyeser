package it.cybion.socialeyeser.trends.model;

import java.net.URL;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class UrlEntity {
    
    public int start;
    public int end;
    public URL url;
    public URL expandedURL;
    public String displayURL;
    
    public UrlEntity() {
    
    }

    public int getStart() {

        return start;
    }

    public void setStart(int start) {

        this.start = start;
    }

    public int getEnd() {

        return end;
    }

    public void setEnd(int end) {

        this.end = end;
    }

    public URL getUrl() {

        return url;
    }

    public void setUrl(URL url) {

        this.url = url;
    }

    public URL getExpandedURL() {

        return expandedURL;
    }

    public void setExpandedURL(URL expandedURL) {

        this.expandedURL = expandedURL;
    }

    public String getDisplayURL() {

        return displayURL;
    }

    public void setDisplayURL(String displayURL) {

        this.displayURL = displayURL;
    }
}
