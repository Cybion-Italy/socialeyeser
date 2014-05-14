package it.cybion.socialeyeser.trends;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.testng.Assert.assertTrue;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class TrendTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrendTestCase.class);

    @Test
    public void shouldTestSingletonEventBus() throws Exception {

        final EventBus eventBus = new EventBus();
        final TweetArrivedListener tweetArrivedListener = new TweetArrivedListener();

        eventBus.register(tweetArrivedListener);

        final long tenMsecsPauseBetweenTweets = 10L;
        final TweetStream tweetStream = new TweetStream(tenMsecsPauseBetweenTweets, eventBus);

        final Thread producerThread = new Thread(tweetStream);
        producerThread.start();

        final long oneSecMsecs = 1000L;
        Thread.sleep(oneSecMsecs);

        tweetStream.stop();
        producerThread.join();
        assertTrue(true);

    }

    private static class TweetArrivedListener {

        private final Queue<DateTime> lastRequests;

        private int maxTweetsPerWindow;

        private int windowSizeMsecs;

        private TweetArrivedListener() {

            this.lastRequests = new LinkedList<DateTime>();
            this.maxTweetsPerWindow = 10;
            this.windowSizeMsecs = 100;

        }

        @Subscribe
        public void recordTweet(final TweetArrived event) {

            final DateTime timeStamp = event.getTimeStamp();

            boolean trendDetected = areLastEventsEnoughForTrend(timeStamp);

            if (trendDetected) {
                LOGGER.info("it's trending! - " + timeStamp);
            } else {
                LOGGER.info("nothing relevant " + timeStamp);
            }

        }

        //the whole trend-detection algorithm
        private boolean areLastEventsEnoughForTrend(final DateTime timestamp) {

            //enqueue
            this.lastRequests.add(timestamp);

            dequeueLastElementIfExceeds(maxTweetsPerWindow);

            final DateTime oldest = this.lastRequests.peek();

            if (oldest != null) {

                Interval interval = new Interval(oldest, timestamp);
                if (interval.toDurationMillis() >= windowSizeMsecs) {
                    LOGGER.info("interval " + interval.toDurationMillis() + " msec");
                    return true;
                }
            }

            return false;
        }

        private void dequeueLastElementIfExceeds(int maxTweetsPerWindow) {

            //check
            if (this.lastRequests.size() >= maxTweetsPerWindow) {
                final DateTime removed = this.lastRequests.poll();
            }
        }

    }

    private static class TweetArrived {

        private String tweetId;

        private DateTime timeStamp;

        public TweetArrived(final String tweetId, final DateTime timeStamp) {

            this.tweetId = tweetId;
            this.timeStamp = timeStamp;
        }

        public String getTweetId() {

            return tweetId;
        }

        public DateTime getTimeStamp() {

            return timeStamp;
        }
    }

    private static class TweetStream implements Runnable {

        private final EventBus eventBus;

        private Long pauseBetweenTweets;

        private boolean running = true;

        public TweetStream(Long pauseBetweenTweets, EventBus eventBus) {

            this.pauseBetweenTweets = pauseBetweenTweets;
            this.eventBus = eventBus;
        }

        @Override
        public void run() {

            while (running) {
                final DateTime now = new DateTime();
                final TweetArrived aTweetArrived = new TweetArrived("" + now.getMillis(), now);
                this.eventBus.post(aTweetArrived);
                try {
                    Thread.sleep(pauseBetweenTweets);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stop() {

            this.running = false;
        }
    }
}
