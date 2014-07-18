package it.cybion.socialeyeser.trends.model;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class Coordinates {
    
    public double[] coordinates;
    public double lat;
    public double lon;
    public String type;
    
    public Coordinates() {
    
    }

    public double[] getCoordinates() {

        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {

        this.coordinates = coordinates;
    }

    public double getLat() {

        return lat;
    }

    public void setLat(double lat) {

        this.lat = lat;
    }

    public double getLon() {

        return lon;
    }

    public void setLon(double lon) {

        this.lon = lon;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }
}
