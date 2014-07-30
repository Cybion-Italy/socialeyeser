package it.cybion.socialeyeser.trends;

import org.testng.annotations.Test;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class AvgTestCase {

    /* test exponentially weighted moving average */
    @Test
    public void shouldCalculateExponentiallyWeightedMovingAverage() throws Exception {

        //input
        final PublishSubject<Long> timestamps = PublishSubject.create();

        final SystemOutSubscriber<Long> systemOutSubscriber = new SystemOutSubscriber<Long>();

        final Max max = new Max(0L);
        final Observable<Long> maxObs = max.observe(timestamps);
        maxObs.subscribe(systemOutSubscriber);
        timestamps.onNext(1L);
        timestamps.onNext(2L);
        timestamps.onNext(3L);
        timestamps.onNext(3L);
        timestamps.onNext(2L);
        timestamps.onNext(1L);
        timestamps.onNext(1L);
        timestamps.onCompleted();

    }

    private class Max {

        private final AtomicLong currentMax;

        public Max(final Long currentMax) {

            this.currentMax = new AtomicLong(currentMax);
        }

        public Observable<Long> observe(final Observable<Long> timestamps) {

            return timestamps.filter(new Func1<Long, Boolean>() {
                @Override
                public Boolean call(final Long currentValue) {

                    if (currentValue > currentMax.get()) {
                        currentMax.set(currentValue);
                        return Boolean.TRUE;
                    } else {
                        return Boolean.FALSE;
                    }
                }
            });
        }

    }

    public static class SystemOutSubscriber<T> extends Subscriber<T> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(T o) {

            System.out.println(o.toString() + "");

        }
    }
}
