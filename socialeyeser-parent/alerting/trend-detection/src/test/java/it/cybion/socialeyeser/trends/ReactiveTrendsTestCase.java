package it.cybion.socialeyeser.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ReactiveTrendsTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveTrendsTestCase.class);

    @Test
    public void testName() throws Exception {

        final List<String> tweets = new LinkedList<String>();
        tweets.add("1");
        tweets.add("2");

        final Observable<String> loopOverTweets = Observable
                .from(tweets)
                .repeat(Schedulers.io());

        final Observable<Integer> itemsPerUnitObservable = loopOverTweets
                .buffer(10, TimeUnit.MILLISECONDS).map(new Func1<List<String>, Integer>() {
                                                            @Override
                                                            public Integer call(
                                                                    final List<String> strings) {

                                                                return strings.size();
                                                            }
                                                        }
                );

        final Subscription subscription = itemsPerUnitObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("items per unit " + integer);

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                LOGGER.error(throwable.getMessage());
            }
        });

        Thread.sleep(5000L);

        subscription.unsubscribe();
        assertTrue(subscription.isUnsubscribed());

    }
}
