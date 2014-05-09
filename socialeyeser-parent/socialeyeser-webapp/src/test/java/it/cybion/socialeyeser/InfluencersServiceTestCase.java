package it.cybion.socialeyeser;

import it.cybion.commons.RESTTestUtils;
import it.cybion.commons.web.http.CybionHttpClient;
import it.cybion.commons.web.http.exceptions.CybionHttpException;
import it.cybion.commons.web.responses.ExternalStringResponse;
import it.cybion.commons.web.responses.ResponseStatus;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Maps;

public class InfluencersServiceTestCase extends JettyServer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(InfluencersServiceTestCase.class);
    
    public InfluencersServiceTestCase() {
    
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
    
        final String url = super.baseUri + "twitter/1234";
        
        ExternalStringResponse stringResponse = null;
        
        final Map<String, String> requestHeaderMap = Maps.newHashMap();
        requestHeaderMap.put("Accept", MediaType.APPLICATION_JSON);
        stringResponse = CybionHttpClient.performGet(url, requestHeaderMap);
        
        LOGGER.info("response body: " + stringResponse.getObject());
        Assert.assertTrue(ResponseStatus.OK == stringResponse.getStatus(), "Unexpected result: "
                + stringResponse.getMessage());
    }
}
