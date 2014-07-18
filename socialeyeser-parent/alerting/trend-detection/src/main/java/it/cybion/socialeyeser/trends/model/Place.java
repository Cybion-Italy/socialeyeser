package it.cybion.socialeyeser.trends.model;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class Place {
    
    public String name;
    public String streetAddress;
    public String countryCode;
    public String id;
    public String country;
    public String placeType;
    public String url;
    public String fullName;
    public BoundingBox boundingBox;
    
    public Place() {
    
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getStreetAddress() {

        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {

        this.streetAddress = streetAddress;
    }

    public String getCountryCode() {

        return countryCode;
    }

    public void setCountryCode(String countryCode) {

        this.countryCode = countryCode;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public String getPlaceType() {

        return placeType;
    }

    public void setPlaceType(String placeType) {

        this.placeType = placeType;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public String getFullName() {

        return fullName;
    }

    public void setFullName(String fullName) {

        this.fullName = fullName;
    }

    public BoundingBox getBoundingBox() {

        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {

        this.boundingBox = boundingBox;
    }
}
