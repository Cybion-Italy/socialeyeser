package it.cybion.socialeyeser.trends.features.count;

import static org.testng.Assert.assertEquals;
import it.cybion.socialeyeser.trends.features.AbstractFeatureTestCase;
import it.cybion.socialeyeser.trends.features.FollowingsFeature;
import it.cybion.socialeyeser.trends.features.windows.FixedSizeFeatureWindow;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FollowingFeatureTestCase extends AbstractFeatureTestCase {
    
    public FollowingFeatureTestCase() throws IOException, URISyntaxException {
    
        super();
        
    }
    
    @BeforeClass
    public void setup() {
    
        feature = new FollowingsFeature(new FixedSizeFeatureWindow(1));
    }
    
    @Test
    public void shouldTestFeature() throws Exception {
    
        double value = feature.extractFrom(sampleTweet);
        assertEquals(value, 538.0);
    }
    
}
