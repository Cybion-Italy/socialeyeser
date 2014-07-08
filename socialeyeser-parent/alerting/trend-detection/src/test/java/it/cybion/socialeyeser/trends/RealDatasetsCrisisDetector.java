package it.cybion.socialeyeser.trends;

import it.cybion.socialeyeser.trends.runners.StreamReplayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class RealDatasetsCrisisDetector {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RealDatasetsCrisisDetector.class);
    
    private static final String DATASETS_DIR = "/Data/crisis_data/ordered/";
    private static final String[] DATASET_NAMES = new String[] { "fiat" };
    
    // private static final String[] DATASET_NAMES = new String[] { "fiat",
    // "toyota", "prandelli",
    // "unipolsai", "ubibanca", "carige", "mcdonalds" };
    
    @Test
    public void shouldTestCrisisDetectorOnRealDatasets() throws Exception {
    
        for (String crisisName : DATASET_NAMES) {
            
            LOGGER.info("Testing " + crisisName);
            
            StreamReplayer replayer = new StreamReplayer(3000000, DATASETS_DIR + crisisName
                    + ".zip");
            
            CrisisDetector detector = new CrisisDetector(0.8, 0.3);
            
            LOGGER.debug("******************* BEGIN OF STREAM *****************");
            try {
                while (true) {
                    detector.detect(replayer.nextTweet());
                }
            } catch (Exception e) {
                LOGGER.debug("******************* END OF STREAM *****************");
            }
        }
    }
    
}
