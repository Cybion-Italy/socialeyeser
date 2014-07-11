package it.cybion.socialeyeser.trends.runners;

import it.cybion.socialeyeser.trends.CrisisDetector;
import it.cybion.socialeyeser.trends.model.Tweet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.TwitterStream;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class OfflineTrendsMiner {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OfflineTrendsMiner.class);
    
    CrisisDetector detector;
    StreamReplayer replayer;
    
    PrintStream stats;
    ObjectMapper mapper;
    TwitterStream stream;
    long totalTweetCounter = 0;
    int tweetCounter = 0;
    
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
    
        // "fiat", "toyota", "prandelli", "unipolsai", "ubibanca", "carige",
        // "mcdonalds"
        String[] crisis = new String[] { "fiat", "toyota", "prandelli", "unipolsai", "ubibanca",
                "carige", "mcdonalds" };
        
        for (String crisisName : crisis) {
            
            LOGGER.info("Replaying " + crisisName);
            
            StreamReplayer replayer = new StreamReplayer(3000000, "/Data/crisis_data/ordered/"
                    + crisisName + ".zip");
            OfflineTrendsMiner miner = new OfflineTrendsMiner(replayer, "/Data/crisis_data/alerts/"
                    + crisisName + ".txt");
            
            miner.fetchItems();
        }
        
    }
    
    public OfflineTrendsMiner(StreamReplayer replayer, String statFilePath)
            throws FileNotFoundException {
    
        this.replayer = replayer;
        this.stats = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(
                statFilePath))));
        
        initDetector();
        
    }
    
    public OfflineTrendsMiner(StreamReplayer replayer) throws FileNotFoundException {
    
        this.replayer = replayer;
        this.stats = System.out;
        initDetector();
        
    }
    
    private void initDetector() {
    
        detector = new CrisisDetector(0.5, 0.5, 3600000);
    }
    
    public void fetchItems() {
    
        while (true) {
            
            try {
                final Tweet tweet = replayer.nextTweet();
                
                totalTweetCounter++;
                detector.detect(tweet);
                List<Double> feats = detector.getLastObserverFeatures();
                
                if (totalTweetCounter % 1000 == 0) {
                    for (double feat : feats)
                        stats.print(feat + " ");
                    stats.print("\n");
                }
                
            } catch (Exception e) {
                
                LOGGER.error(e.getMessage());
                break;
            }
        }
        
        stats.close();
    }
    
}
