package it.cybion.socialeyeser.alerting.representations;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlertPage {

    //TODO add data instead of page
    @XmlAttribute(name = "page")
    private int page;

    @XmlAttribute(name = "paging")
    private Paging paging;

    public AlertPage(int page, Paging paging) {

        this.page = page;
        this.paging = paging;
    }

    public int getPage() {

        return page;
    }

    public Paging getPaging() {

        return paging;
    }
}
