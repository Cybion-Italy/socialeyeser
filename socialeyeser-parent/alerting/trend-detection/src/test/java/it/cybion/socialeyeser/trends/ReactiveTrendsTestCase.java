package it.cybion.socialeyeser.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ReactiveTrendsTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveTrendsTestCase.class);

    @Test(enabled = true)
    public void shouldDetectSpeedChangesAndAvgsFromConsoleInput() throws Exception {

        //input
        final PublishSubject<String> consoleInputLines = PublishSubject.create();

        //build observables
        final Observable<Integer> currentSpeed = it.cybion.socialeyeser.trends.Speed.speedometer(
                consoleInputLines, 1);

        final Observable<Integer> averageSpeed = it.cybion.socialeyeser.trends.Speed.movingAverageOf(
                5, currentSpeed);

        final Observable<Integer> filterGtEq = it.cybion.socialeyeser.trends.Filter.filterGtEq(5,
                currentSpeed);

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
                    consoleInputLines.onNext("next");
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
                    consoleInputLines.onNext("next");
                }

                LOGGER.info(amount + " fast message pushed");

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                consoleInputLines.onCompleted();

                LOGGER.info("completed");

            }
        }, "the-writing-thread");
        thread.start();
        thread.join();

    }

}
