package it.cybion.socialeyeser.trends.model;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class BoundingBox {
    
    public double[][][] coordinates;
    public String type;
    
    public BoundingBox() {
    
    }

    public double[][][] getCoordinates() {

        return coordinates;
    }

    public void setCoordinates(double[][][] coordinates) {

        this.coordinates = coordinates;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }
}
