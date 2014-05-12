package it.cybion.socialeyeser.alerting;

import com.google.common.collect.Maps;
import it.cybion.commons.RESTTestUtils;
import it.cybion.commons.web.http.CybionHttpClient;
import it.cybion.commons.web.http.exceptions.CybionHttpException;
import it.cybion.commons.web.responses.ExternalStringResponse;
import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.socialeyeser.alerting.guice.TestServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class AlertsResourceTestCase extends JettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertsResourceTestCase.class);

    public AlertsResourceTestCase() {

        super(RESTTestUtils.PORT);
    }

    @BeforeMethod
    public void setUpLocalJettyServer() throws Exception {

        super.addEventListener(new TestServiceConfig());
        super.start();
    }

    @AfterMethod
    public void tearDownJetty() throws Exception {

        super.stop();
    }

    @Test
    public void shouldTestService() throws CybionHttpException {

        final String url = super.baseUri + "alerts/1234";

        ExternalStringResponse stringResponse = null;

        final Map<String, String> requestHeaderMap = Maps.newHashMap();
        requestHeaderMap.put("Accept", MediaType.APPLICATION_JSON);
        //TODO use a plain http-components client
        stringResponse = CybionHttpClient.performGet(url, requestHeaderMap);

        LOGGER.info("response body: " + stringResponse.getObject());
        assertEquals(ResponseStatus.OK, stringResponse.getStatus(),
                "Unexpected result: " + stringResponse.getMessage());
    }
}
