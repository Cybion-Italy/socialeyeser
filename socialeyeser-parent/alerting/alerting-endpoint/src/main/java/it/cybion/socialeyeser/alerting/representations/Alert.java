package it.cybion.socialeyeser.alerting.representations;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
@XmlRootElement
public class Alert {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "level")
    private int level;

    public Alert() {

    }

    public Alert(String alertId, int level) {

        this.id = alertId;
        this.level = level;
    }

}
