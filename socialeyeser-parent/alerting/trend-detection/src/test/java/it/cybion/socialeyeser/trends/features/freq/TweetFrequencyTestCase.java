package it.cybion.socialeyeser.trends.features.freq;

import static org.testng.Assert.assertTrue;
import it.cybion.socialeyeser.trends.features.AbstractFeatureTestCase;
import it.cybion.socialeyeser.trends.features.TweetFeature;
import it.cybion.socialeyeser.trends.features.windows.FixedTimeWindow;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TweetFrequencyTestCase extends AbstractFeatureTestCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TweetFrequencyTestCase.class);
    
    public TweetFrequencyTestCase() throws IOException, URISyntaxException {
    
        super();
        
    }
    
    @BeforeClass
    public void setup() {
    
        feature = new TweetFeature(new FixedTimeWindow(1000));
    }
    
    @Test
    public void shouldTestFeature() throws Exception {
    
        double value = feature.extractFrom(sampleTweet);
        LOGGER.info("punctual frequency: " + value);
        
        for (int i = 0; i < 1000; i++) {
            
            sampleTweet.createdAt = new Date();
            value = feature.extractFrom(sampleTweet);
            // LOGGER.info("punctual frequency: " + value + " / s");
            
            if (i > 100)
                assertTrue(value > 90);
            Thread.sleep(10L);
            
        }
    }
    
}
