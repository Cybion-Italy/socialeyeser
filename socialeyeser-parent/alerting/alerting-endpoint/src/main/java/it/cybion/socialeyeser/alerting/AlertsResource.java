package it.cybion.socialeyeser.alerting;

import com.google.inject.Inject;
import it.cybion.socialeyeser.alerting.representations.Alert;
import it.cybion.socialeyeser.alerting.representations.AlertPage;
import it.cybion.socialeyeser.alerting.representations.Paging;
import it.cybion.socialeyeser.alerting.services.AlertsService;
import org.apache.log4j.Logger;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/alerts")
public class AlertsResource {

    private static final Logger LOGGER = Logger.getLogger(AlertsResource.class);

    private AlertsService alertsService;

    @Inject
    public AlertsResource() {

        LOGGER.info("Starting influence service");
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Alert byId(@Context UriInfo uriInfo, @PathParam("userId") String alertId) {

        LOGGER.info("alert id parameter '" + alertId + "'");

        //TODO validate params

        URI nextPageUri = null;

        boolean hasNextPage = true;

        try {
            return new Alert(alertId, 100);
        } catch (final Exception e) {
            throw new RuntimeException("Error", e);
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AlertPage listAlerts(@Context UriInfo uriInfo,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("per_page") @DefaultValue("10") int perPage) {

        //TODO validate params
        LOGGER.info("alerts parameter '" + page + "' '" + perPage + "'");

        this.alertsService.list(page, perPage);

        //TODO map dto to representation

        URI nextPageUri = null;

        boolean hasNextPage = true;

        //TODO manage pagination
        if (hasNextPage) {
            final int nextPage = page + 1;
            nextPageUri = uriInfo.getBaseUriBuilder().path(AlertsResource.class).queryParam("page",
                    nextPage).queryParam("per_page", perPage).build();
        }

        try {
            return new AlertPage(page, new Paging(nextPageUri, nextPageUri));
        } catch (final Exception e) {
            throw new RuntimeException("Error", e);
        }

    }

}