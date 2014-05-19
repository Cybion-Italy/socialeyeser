package it.cybion.socialeyeser.trends.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.TestScheduler;

import static org.testng.Assert.assertEquals;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class FilterIntegerTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterIntegerTestCase.class);

    @Test
    public void shouldFilter() throws Exception {

        final TestScheduler testScheduler = new TestScheduler();
        final Observable<Integer> two = Observable.just(2);
        final FilterInteger filterInteger = new FilterInteger(two, testScheduler);
        final Observable<Integer> gtEqTwo = filterInteger.filterGtEq(2);

        gtEqTwo.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                assertEquals(integer, Integer.valueOf(2));
            }
        });
        testScheduler.triggerActions();
    }
}
