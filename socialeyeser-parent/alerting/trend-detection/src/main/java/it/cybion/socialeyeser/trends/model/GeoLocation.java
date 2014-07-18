package it.cybion.socialeyeser.trends.model;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class GeoLocation {
    
    public String type;
    public double[] coordinates;
    
    public GeoLocation() {
    
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public double[] getCoordinates() {

        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {

        this.coordinates = coordinates;
    }
}
