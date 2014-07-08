package it.cybion.socialeyeser.trends.features.windows;

import static org.testng.Assert.assertTrue;
import it.cybion.socialeyeser.trends.features.AbstractFeatureTestCase;
import it.cybion.socialeyeser.trends.features.FavoritesFeature;

import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class FavoritesWindowFeatureTestCase extends AbstractFeatureTestCase {
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(FavoritesWindowFeatureTestCase.class);
    
    public FavoritesWindowFeatureTestCase() throws IOException, URISyntaxException {
    
        super();
        
    }
    
    @Test
    public void shouldTestFixedTimeWindowFeature() throws Exception {
    
        feature = new FavoritesFeature(new FixedTimeWindow(10000L));
        
        double value = feature.extractFrom(sampleTweet);
        LOGGER.info("time average favorites: " + value);
        
        for (int i = 0; i < 100; i++) {
            
            value = feature.extractFrom(sampleTweet);
            LOGGER.info("time average favorites: " + value);
            
            assertTrue(value > 10);
            Thread.sleep(10L);
            
        }
        
    }
    
    @Test
    public void shouldTestFixedSizeWindowFeature() throws Exception {
    
        feature = new FavoritesFeature(new FixedSizeWindow(10));
        
        double value = feature.extractFrom(sampleTweet);
        LOGGER.info("size average favorites: " + value);
        
        for (int i = 0; i < 100; i++) {
            
            value = feature.extractFrom(sampleTweet);
            LOGGER.info("size average favorites: " + value);
            
            assertTrue(value > 1);
            Thread.sleep(10L);
            
        }
        
    }
    
}
