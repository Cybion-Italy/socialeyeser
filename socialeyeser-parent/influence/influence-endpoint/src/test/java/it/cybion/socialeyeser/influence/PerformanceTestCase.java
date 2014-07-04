package it.cybion.socialeyeser.influence;

import static org.testng.Assert.assertEquals;
import it.cybion.commons.web.http.CybionHttpClient;
import it.cybion.commons.web.http.exceptions.CybionHttpException;
import it.cybion.commons.web.responses.ExternalStringResponse;
import it.cybion.commons.web.responses.ResponseStatus;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.google.common.collect.Maps;

public class PerformanceTestCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceTestCase.class);
    private static final int MAX_REQUESTS = 100;
    
    @Test
    public void shouldTestRESTPerformance() throws CybionHttpException {
    
        final Map<String, String> requestHeaderMap = Maps.newHashMap();
        requestHeaderMap.put("Accept", MediaType.APPLICATION_JSON);
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_REQUESTS; i++) {
            String url = "http://gaia.cybion.eu/socialeyeser/influence/twitter?userId=83342324626633&followers="
                    + System.nanoTime() % 100000000;
            ExternalStringResponse stringResponse = CybionHttpClient.performGet(url,
                    requestHeaderMap);
            assertEquals(ResponseStatus.OK, stringResponse.getStatus(), "Unexpected result: "
                    + stringResponse.getMessage());
        }
        
        LOGGER.info(MAX_REQUESTS + " performed in "
                + ((System.currentTimeMillis() - start) / 1000.0));
        
    }
}
