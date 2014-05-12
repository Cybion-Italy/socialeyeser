package it.cybion.socialeyeser.alerting.representations;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
@XmlRootElement
public class Paging {

    @XmlAttribute(name = "previous")
    private final URI previous;

    @XmlAttribute(name = "next")
    private final URI next;

    public Paging(URI previous, URI next) {

        this.previous = previous;
        this.next = next;
    }

    public URI getPrevious() {

        return previous;
    }

    public URI getNext() {

        return next;
    }
}
