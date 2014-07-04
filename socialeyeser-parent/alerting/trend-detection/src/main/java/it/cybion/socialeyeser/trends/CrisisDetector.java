package it.cybion.socialeyeser.trends;

import it.cybion.socialeyeser.trends.adwin.AdWin;
import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.features.sma.FavoritesContainerFeature;
import it.cybion.socialeyeser.trends.features.sma.FollowersContainerFeature;
import it.cybion.socialeyeser.trends.features.sma.FollowingsContainerFeature;
import it.cybion.socialeyeser.trends.features.sma.HashtagsCountContainerFeature;
import it.cybion.socialeyeser.trends.features.sma.LinksContainerFeature;
import it.cybion.socialeyeser.trends.features.sma.MentionsContainerFeature;
import it.cybion.socialeyeser.trends.features.sma.RetweetContainerFeature;
import it.cybion.socialeyeser.trends.features.sma.RetweetCountContainerFeature;
import it.cybion.socialeyeser.trends.features.sma.TweetContainerFeature;
import it.cybion.socialeyeser.trends.features.sma.containers.FixedTimeFeatureContainer;
import it.cybion.socialeyeser.trends.model.Tweet;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class CrisisDetector {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CrisisDetector.class);
    
    private final double delta = 0.0000001;// 0.002;
    Map<Feature, AdWin> featureObservers;
    Feature[] featureObserverList;
    PrintStream ps;
    
    public CrisisDetector(PrintStream ps) {
    
        this.ps = ps;
        
        featureObservers = new HashMap<Feature, AdWin>();
        // featureObserverList = new Feature[] { new FavoritesFeature(), new
        // FollowersFeature(),
        // new FollowingFeature(), new HashtagsFeature(), new LinksFeature(),
        // new MentionsFeature(), new RetweetsFeature(), new RetweetFrequency(),
        // new TweetFrequency() };
        // ps.println("timeMillis favorite favorite_alert follower follower_alert following following_alert hashtag hashtag_alert link link_alert mention mention_alert retweet retweet_alert retweet_freq retweet_freq_alert tweet_freq tweet_freq_alert total_alerts");
        
        long oneHourMillis = 3600 * 1000L;
        long sixHoursMillis = 6 * oneHourMillis;
        long oneDayMillis = 24 * oneHourMillis;
        featureObserverList = new Feature[] {
                new FavoritesContainerFeature(new FixedTimeFeatureContainer(oneHourMillis)),
                new FollowersContainerFeature(new FixedTimeFeatureContainer(oneHourMillis)),
                new FollowingsContainerFeature(new FixedTimeFeatureContainer(oneHourMillis)),
                new HashtagsCountContainerFeature(new FixedTimeFeatureContainer(oneHourMillis)),
                new LinksContainerFeature(new FixedTimeFeatureContainer(oneHourMillis)),
                new MentionsContainerFeature(new FixedTimeFeatureContainer(oneHourMillis)),
                new TweetContainerFeature(new FixedTimeFeatureContainer(oneHourMillis)),
                new RetweetContainerFeature(new FixedTimeFeatureContainer(oneHourMillis)),
                new RetweetCountContainerFeature(new FixedTimeFeatureContainer(oneHourMillis)),
                
                new FavoritesContainerFeature(new FixedTimeFeatureContainer(sixHoursMillis)),
                new FollowersContainerFeature(new FixedTimeFeatureContainer(sixHoursMillis)),
                new FollowingsContainerFeature(new FixedTimeFeatureContainer(sixHoursMillis)),
                new HashtagsCountContainerFeature(new FixedTimeFeatureContainer(sixHoursMillis)),
                new LinksContainerFeature(new FixedTimeFeatureContainer(sixHoursMillis)),
                new MentionsContainerFeature(new FixedTimeFeatureContainer(sixHoursMillis)),
                new TweetContainerFeature(new FixedTimeFeatureContainer(sixHoursMillis)),
                new RetweetContainerFeature(new FixedTimeFeatureContainer(sixHoursMillis)),
                new RetweetCountContainerFeature(new FixedTimeFeatureContainer(sixHoursMillis)),
                
                new FavoritesContainerFeature(new FixedTimeFeatureContainer(oneDayMillis)),
                new FollowersContainerFeature(new FixedTimeFeatureContainer(oneDayMillis)),
                new FollowingsContainerFeature(new FixedTimeFeatureContainer(oneDayMillis)),
                new HashtagsCountContainerFeature(new FixedTimeFeatureContainer(oneDayMillis)),
                new LinksContainerFeature(new FixedTimeFeatureContainer(oneDayMillis)),
                new MentionsContainerFeature(new FixedTimeFeatureContainer(oneDayMillis)),
                new TweetContainerFeature(new FixedTimeFeatureContainer(oneDayMillis)),
                new RetweetContainerFeature(new FixedTimeFeatureContainer(oneDayMillis)),
                new RetweetCountContainerFeature(new FixedTimeFeatureContainer(oneDayMillis))
        
        };
        ps.println("timeMillis favorite1h follower1h following1h hashtag1h link1h mention1h tweet1h retweet1h retweet_count1h favorite6h follower6h following6h hashtag6h link6h mention6h tweet6h retweet6h retweet_count6h favorite24h follower24h following24h hashtag24h link24h mention24h tweet24h retweet24h retweet_count24h total_alerts");
        
        for (Feature feat : featureObserverList)
            featureObservers.put(feat, new AdWin(delta));
        
    }
    
    public double detect(Tweet tweet) {
    
        double activatedObservers = 0.00;
        double observerValue = 0.0;
        
        ps.print(tweet.createdAt.getTime() + " ");
        
        for (Feature observer : featureObserverList) {
            
            observerValue = observer.extractFrom(tweet);
            ps.print(observerValue + " ");
            
            if (featureObservers.get(observer).setInput(observerValue)) {
                activatedObservers++;
            }
            
        }
        ps.print(activatedObservers + "\n");
        
        return activatedObservers / featureObservers.size();
    }
}
