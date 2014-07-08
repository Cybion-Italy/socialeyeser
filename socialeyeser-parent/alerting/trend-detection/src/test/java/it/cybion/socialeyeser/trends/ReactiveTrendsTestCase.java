package it.cybion.socialeyeser.trends;

import it.cybion.socialeyeser.trends.functions.Average;
import it.cybion.socialeyeser.trends.functions.FilterInteger;
import it.cybion.socialeyeser.trends.functions.Speedometer;
import it.cybion.socialeyeser.trends.functions.StringLengthOMeter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ReactiveTrendsTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveTrendsTestCase.class);

    @Test
    public void shouldDetectSpeedChangesAndAvgs() throws Exception {

        //input
        final PublishSubject<String> stringPublishSubject = PublishSubject.create();

        final Speedometer<String> stringSpeedometer = new Speedometer<String>(stringPublishSubject,
                Schedulers.io());

        final StringLengthOMeter stringLengthOMeter = new StringLengthOMeter(stringPublishSubject,
                Schedulers.io());

        final Observable<Integer> stringLengths = stringLengthOMeter.measureEvery(1,
                TimeUnit.SECONDS);

        //build observables
        final Observable<Integer> currentSpeed = stringSpeedometer.measureEvery(1,
                TimeUnit.SECONDS);

        final Average average = new Average(currentSpeed, Schedulers.computation());
        final Observable<Integer> averageSpeed = average.movingOf(5);

        final FilterInteger filterInteger = new FilterInteger(currentSpeed,
                Schedulers.computation());
        final Observable<Integer> filterGtEq = filterInteger.filterGtEq(5);

        //subscribe the same logger
        final SystemOutSubscriber systemOutSubscriber = new SystemOutSubscriber();

//        final Subscription avgPrinter = averageSpeed.subscribe(systemOutSubscriber);
//        final Subscription peakPrinter = filterGtEq.subscribe(systemOutSubscriber);
        final Subscription stringLengthPrinter = stringLengths.subscribe(systemOutSubscriber);

        //        final BurstingWriter writer = new BurstingWriter(stringPublishSubject);
        final InfiniteWriter writer = new InfiniteWriter(stringPublishSubject);

        final Thread thread = new Thread(writer, "the-writing-thread");

        thread.start();

//        final int oneHourMsecs = 1000 * 60 * 60;
        final int oneHourMsecs = 10;
        Thread.sleep(oneHourMsecs);

        writer.stop();
        thread.join();

//        peakPrinter.unsubscribe();
//        avgPrinter.unsubscribe();
        stringLengthPrinter.unsubscribe();

    }

    private static class BurstingWriter implements Runnable {

        private final PublishSubject<String> stringPublishSubject;

        public BurstingWriter(final PublishSubject<String> stringPublishSubject) {

            this.stringPublishSubject = stringPublishSubject;
        }

        @Override
        public void run() {

            //3 slow messages
            for (int i = 0; i < 3; i++) {
                stringPublishSubject.onNext("next");
                LOGGER.info("1 slow message pushed");
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //lots of fast messages
            int amount = 100;

            for (int i = 0; i < amount; i++) {
                stringPublishSubject.onNext("next");

            }

            LOGGER.info(amount + " fast message pushed");

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            stringPublishSubject.onCompleted();

            LOGGER.info("completed");

        }

        public void stop() {

        }
    }

    private static class InfiniteWriter implements Runnable {

        private PublishSubject<String> stringPublishSubject;

        private volatile boolean running;

        public InfiniteWriter(PublishSubject<String> stringPublishSubject) {

            this.stringPublishSubject = stringPublishSubject;
            this.running = true;
        }

        @Override
        public void run() {

            while (running) {
                stringPublishSubject.onNext("message");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            stringPublishSubject.onCompleted();

        }

        public void stop() {

            this.running = false;
        }
    }

    private static class SystemOutSubscriber extends Subscriber<Integer> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Integer o) {
            //log
            LOGGER.info(o + "");

        }
    }
}
