package it.cybion.socialeyeser.trends.model;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class Attribute {
    
    public String streetAddress;
    public String locality;
    public String region;
    private String iso3;
    public String postalCode;
    public String phone;
    public String twitter;
    public String url;
    
    public Attribute() {
    
    }

    public String getStreetAddress() {

        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {

        this.streetAddress = streetAddress;
    }

    public String getLocality() {

        return locality;
    }

    public void setLocality(String locality) {

        this.locality = locality;
    }

    public String getRegion() {

        return region;
    }

    public void setRegion(String region) {

        this.region = region;
    }

    public String getIso3() {

        return iso3;
    }

    public void setIso3(String iso3) {

        this.iso3 = iso3;
    }

    public String getPostalCode() {

        return postalCode;
    }

    public void setPostalCode(String postalCode) {

        this.postalCode = postalCode;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public String getTwitter() {

        return twitter;
    }

    public void setTwitter(String twitter) {

        this.twitter = twitter;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }
}
