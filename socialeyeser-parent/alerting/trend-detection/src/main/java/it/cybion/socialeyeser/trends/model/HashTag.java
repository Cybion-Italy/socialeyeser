package it.cybion.socialeyeser.trends.model;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class HashTag {
    
    public int[] indices;
    
    public String text;
    
    public HashTag() {
    
    }

    public int[] getIndices() {

        return indices;
    }

    public String getText() {

        return text;
    }

    public void setIndices(int[] indices) {

        this.indices = indices;
    }

    public void setText(String text) {

        this.text = text;
    }
}
