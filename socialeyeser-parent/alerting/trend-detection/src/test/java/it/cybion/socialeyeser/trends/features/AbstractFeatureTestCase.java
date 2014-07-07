package it.cybion.socialeyeser.trends.features;

import it.cybion.socialeyeser.trends.features.base.Feature;
import it.cybion.socialeyeser.trends.model.Tweet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;

public abstract class AbstractFeatureTestCase {
    
    private static final String SAMPLE_TWEET_FILENAME = "/sample_tweet.json";
    protected Tweet sampleTweet;
    protected Feature feature;
    
    public AbstractFeatureTestCase() throws IOException, URISyntaxException {
    
        final FileInputStream stream = new FileInputStream(new File(this.getClass()
                .getResource(SAMPLE_TWEET_FILENAME).toURI()));
        try {
            final FileChannel fc = stream.getChannel();
            final MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            String json = Charset.defaultCharset().decode(bb).toString();
            
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper
                    .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
            objectMapper.setDateFormat(new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy"));
            
            sampleTweet = objectMapper.readValue(json, Tweet.class);
        } finally {
            stream.close();
        }
        
    }
    
}
