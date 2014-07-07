package it.cybion.socialeyeser.trends.features.count;

import static org.testng.Assert.assertEquals;
import it.cybion.socialeyeser.trends.features.AbstractFeatureTestCase;
import it.cybion.socialeyeser.trends.features.FavoritesFeature;
import it.cybion.socialeyeser.trends.features.windows.FixedSizeWindow;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FavoriteFeatureTestCase extends AbstractFeatureTestCase {
    
    public FavoriteFeatureTestCase() throws IOException, URISyntaxException {
    
        super();
        
    }
    
    @BeforeClass
    public void setup() {
    
        feature = new FavoritesFeature(new FixedSizeWindow(1));
    }
    
    @Test
    public void shouldTestFeature() throws Exception {
    
        double value = feature.extractFrom(sampleTweet);
        assertEquals(value, 92.0);
    }
    
}
