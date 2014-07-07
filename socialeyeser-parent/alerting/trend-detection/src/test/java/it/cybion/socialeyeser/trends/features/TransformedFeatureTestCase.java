package it.cybion.socialeyeser.trends.features;

import static org.testng.Assert.assertTrue;
import it.cybion.socialeyeser.trends.features.base.TransformedFeature;
import it.cybion.socialeyeser.trends.features.windows.FixedTimeWindow;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Function;

public class TransformedFeatureTestCase extends AbstractFeatureTestCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TransformedFeatureTestCase.class);
    
    public TransformedFeatureTestCase() throws IOException, URISyntaxException {
    
        super();
        
    }
    
    @BeforeClass
    public void setup() {
    
        Function<Double, Double> log10Function = new Function<Double, Double>() {
            
            @Override
            public Double apply(Double arg0) {
            
                return Math.log10(arg0);
            }
        };
        
        feature = new TransformedFeature(new TweetFeature(new FixedTimeWindow(1000)), log10Function);
    }
    
    @Test
    public void shouldTestFeature() throws Exception {
    
        double value = feature.extractFrom(sampleTweet);
        LOGGER.info("tweet frequency: " + value);
        
        for (int i = 0; i < 1000; i++) {
            
            sampleTweet.createdAt = new Date();
            value = feature.extractFrom(sampleTweet);
            LOGGER.info("punctual frequency: " + value + " / s");
            
            if (i > 100)
                assertTrue(value > 1.9);
            Thread.sleep(10L);
            
        }
    }
    
}
