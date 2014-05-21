package it.cybion.socialeyeser.trends.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import rx.Observable;
import rx.observables.BlockingObservable;
import rx.schedulers.Schedulers;

import static org.testng.Assert.assertEquals;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class FilterIntegerTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterIntegerTestCase.class);

    @Test
    public void shouldFilter() throws Exception {

        final Observable<Integer> two = Observable.just(2);
        final FilterInteger filterInteger = new FilterInteger(two, Schedulers.computation());
        final BlockingObservable<Integer> gtEqTwo = filterInteger.filterGtEq(2)
                .toBlockingObservable();
        assertEquals(gtEqTwo.first(), Integer.valueOf(2));
    }
}
