package it.cybion.socialeyeser.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import rx.Observable;
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
    public void shouldDetectSpeedChangesAndAvgsFromConsoleInput() throws Exception {

        //input
        final PublishSubject<String> stringPublishSubject = PublishSubject.create();

        final Speedometer<String> stringSpeedometer = new Speedometer<String>(stringPublishSubject,
                Schedulers.io());

        //build observables
        final Observable<Integer> currentSpeed = stringSpeedometer.bufferForSecs(1, TimeUnit.SECONDS);

        final Average average = new Average(currentSpeed, Schedulers.computation());
        final Observable<Integer> averageSpeed = average.movingAverageOf(5);

        final Filter filter = new Filter(currentSpeed, Schedulers.computation());
        final Observable<Integer> filterGtEq = filter.filterGtEq(5);

        //TODO groupJoin to detect if current speed is higher than the average speed

        //subscribe loggers
        filterGtEq.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("gteq 5 eps: " + integer);
            }
        });

        averageSpeed.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("avg speed of last 5 seconds: " + integer);
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

    }

}
