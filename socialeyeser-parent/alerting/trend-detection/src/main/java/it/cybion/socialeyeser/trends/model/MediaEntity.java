package it.cybion.socialeyeser.trends.model;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class MediaEntity {
    
    public String display_url;
    public String expanded_url;
    public long id;
    public String id_str;
    public int[] indices;
    public String media_url;
    public String media_url_https;
    // public Object sizes;
    public long source_status_id;
    public String source_status_id_str;
    public String type;
    public String url;
    
    public MediaEntity() {
    
    }

    public String getDisplay_url() {

        return display_url;
    }

    public void setDisplay_url(String display_url) {

        this.display_url = display_url;
    }

    public String getExpanded_url() {

        return expanded_url;
    }

    public void setExpanded_url(String expanded_url) {

        this.expanded_url = expanded_url;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getId_str() {

        return id_str;
    }

    public void setId_str(String id_str) {

        this.id_str = id_str;
    }

    public int[] getIndices() {

        return indices;
    }

    public void setIndices(int[] indices) {

        this.indices = indices;
    }

    public String getMedia_url() {

        return media_url;
    }

    public void setMedia_url(String media_url) {

        this.media_url = media_url;
    }

    public String getMedia_url_https() {

        return media_url_https;
    }

    public void setMedia_url_https(String media_url_https) {

        this.media_url_https = media_url_https;
    }

    public long getSource_status_id() {

        return source_status_id;
    }

    public void setSource_status_id(long source_status_id) {

        this.source_status_id = source_status_id;
    }

    public String getSource_status_id_str() {

        return source_status_id_str;
    }

    public void setSource_status_id_str(String source_status_id_str) {

        this.source_status_id_str = source_status_id_str;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }
}
