package it.cybion.socialeyeser.trends.runners;

import it.cybion.socialeyeser.trends.model.Tweet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

/*
 * replays a twitter stream using a zipped list of tweets
 */
public class StreamReplayer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamReplayer.class);
    
    private double speedFactor = 1.0;
    
    private ZipInputStream zip;
    private Tweet currentTweet;
    private Tweet nextTweet;
    private ObjectMapper mapper;
    
    public StreamReplayer(double speedFactor, String jsonZipPath) {
    
        this.speedFactor = speedFactor;
        
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper
                .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        this.mapper.setDateFormat(new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy"));
        
        try {
            initStream(jsonZipPath);
        } catch (IOException e) {
            LOGGER.error("Could not init stream: " + e.getMessage());
        }
        
    }
    
    private void initStream(String jsonZipPath) throws IOException {
    
        zip = new ZipInputStream(new FileInputStream(new File(jsonZipPath)));
        
        // bootstrap first two tweets
        zip.getNextEntry();
        
        StringBuilder sb = new StringBuilder();
        for (int c = zip.read(); c != -1; c = zip.read()) {
            sb.append((char) c);
        }
        nextTweet = mapper.readValue(sb.toString(), Tweet.class);
        
    }
    
    public Tweet nextTweet() throws Exception {
    
        try {
            ZipEntry entry = zip.getNextEntry();
            if (entry == null) {
                
                zip.close();
                throw new Exception("End of stream reached!");
                
            }
            
            StringBuilder sb = new StringBuilder();
            for (int c = zip.read(); c != -1; c = zip.read()) {
                sb.append((char) c);
            }
            currentTweet = nextTweet;
            nextTweet = mapper.readValue(sb.toString(), Tweet.class);
            
            long interTweetTimemillis = nextTweet.createdAt.getTime()
                    - currentTweet.createdAt.getTime();
            interTweetTimemillis /= speedFactor;
            
            if (interTweetTimemillis < 0) {
                return nextTweet();
            } else
                Thread.sleep(interTweetTimemillis);
            
            return currentTweet;
            
        } catch (Exception e) {
            throw new Exception("Exception while reading stream: " + e.getMessage());
        }
    }
}
