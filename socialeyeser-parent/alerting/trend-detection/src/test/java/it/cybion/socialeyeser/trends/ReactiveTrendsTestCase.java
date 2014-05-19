package it.cybion.socialeyeser.trends;

import it.cybion.socialeyeser.trends.functions.Average;
import it.cybion.socialeyeser.trends.functions.FilterInteger;
import it.cybion.socialeyeser.trends.functions.Speedometer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ReactiveTrendsTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveTrendsTestCase.class);

    @Test(enabled = true)
    public void shouldDetectSpeedChangesAndAvgs() throws Exception {

        //input
        final PublishSubject<String> stringPublishSubject = PublishSubject.create();

        final Speedometer<String> stringSpeedometer = new Speedometer<String>(stringPublishSubject,
                Schedulers.io());

        //build observables
        final Observable<Integer> currentSpeed = stringSpeedometer.measureEvery(1,
                TimeUnit.SECONDS);

        final Average average = new Average(currentSpeed, Schedulers.computation());
        final Observable<Integer> averageSpeed = average.movingOf(5);

        final FilterInteger filterInteger = new FilterInteger(currentSpeed,
                Schedulers.computation());
        final Observable<Integer> filterGtEq = filterInteger.filterGtEq(5);

        //subscribe loggers
        final Subscription avgPrinter = averageSpeed.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("avg speed of last 5 seconds: " + integer);
            }
        });


        final Subscription peakPrinter = filterGtEq.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("gteq 5 eps: " + integer);
            }
        });


        final Thread thread = new Thread(new Runnable() {
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

                //lots speedometer fast messages
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
        }, "the-writing-thread");
        thread.start();
        thread.join();

        peakPrinter.unsubscribe();
        avgPrinter.unsubscribe();

    }

}
