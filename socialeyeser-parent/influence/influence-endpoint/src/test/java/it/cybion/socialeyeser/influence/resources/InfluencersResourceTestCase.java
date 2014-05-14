package it.cybion.socialeyeser.influence.resources;

import com.google.common.collect.Maps;
import it.cybion.commons.RESTTestUtils;
import it.cybion.commons.web.http.CybionHttpClient;
import it.cybion.commons.web.http.exceptions.CybionHttpException;
import it.cybion.commons.web.responses.ExternalStringResponse;
import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.socialeyeser.influence.guice.TestServiceConfig;
import it.cybion.socialeyeser.influence.utils.JettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class InfluencersResourceTestCase extends JettyServer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(InfluencersResourceTestCase.class);
    
    public InfluencersResourceTestCase() {
    
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
    
        String url = super.baseUri + "twitter?userId=1234&followers=100";
        
        ExternalStringResponse stringResponse = null;
        
        final Map<String, String> requestHeaderMap = Maps.newHashMap();
        requestHeaderMap.put("Accept", MediaType.APPLICATION_JSON);
        stringResponse = CybionHttpClient.performGet(url, requestHeaderMap);
        
        LOGGER.info("response body: " + stringResponse.getObject());
        assertTrue(ResponseStatus.OK == stringResponse.getStatus(),
                "Unexpected result: " + stringResponse.getMessage());
        
        url = super.baseUri + "twitter?userId=1234&followers=-100";
        stringResponse = CybionHttpClient.performGet(url, requestHeaderMap);
        
        LOGGER.info("response body: " + stringResponse.getObject());
        assertTrue(ResponseStatus.NOK == stringResponse.getStatus(),
                "Unexpected result: " + stringResponse.getMessage());
        
        url = super.baseUri + "twitter?userId=1234";
        stringResponse = CybionHttpClient.performGet(url, requestHeaderMap);
        
        LOGGER.info("response body: " + stringResponse.getObject());
        assertTrue(ResponseStatus.NOK == stringResponse.getStatus(),
                "Unexpected result: " + stringResponse.getMessage());
        
        url = super.baseUri + "twitter/";
        stringResponse = CybionHttpClient.performGet(url, requestHeaderMap);
        
        LOGGER.info("response body: " + stringResponse.getObject());
        assertTrue(ResponseStatus.NOK == stringResponse.getStatus(),
                "Unexpected result: " + stringResponse.getMessage());
    }
}
