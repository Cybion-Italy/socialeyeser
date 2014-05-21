package it.cybion.socialeyeser.trends;

import com.google.common.base.Charsets;
import it.cybion.socialeyeser.trends.functions.Average;
import it.cybion.socialeyeser.trends.functions.FilterInteger;
import it.cybion.socialeyeser.trends.functions.Speedometer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Demo {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);

    private final PublishSubject<String> consoleInputLines;

    public static void main(String[] args) {

        final Demo demo = new Demo();
        demo.run();

    }

    public Demo() {

        this.consoleInputLines = PublishSubject.create();

        //wire observables "topology"
        final Speedometer<String> tSpeedometer = new Speedometer<String>(consoleInputLines,
                Schedulers.io());

        final Observable<Integer> currentSpeed = tSpeedometer.measureEvery(1, TimeUnit.SECONDS);

        final Average averageObservable = new Average(currentSpeed, Schedulers.computation());

        final int windowSize = 5;
        final Observable<Integer> averageSpeed = averageObservable.movingOf(windowSize);

        final FilterInteger filterInteger = new FilterInteger(currentSpeed,
                Schedulers.computation());

        final int limit = 3;
        final Observable<Integer> filterGtEq = filterInteger.filterGtEq(limit);

        final Observable<Boolean> trendHappening = Observable.combineLatest(averageSpeed,
                currentSpeed, new Func2<Integer, Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer average, Integer currentSpeed) {

                        LOGGER.info("avg " + average + " current " + currentSpeed);
                        final boolean bothHigherThanZero = average > 0 && currentSpeed > 0;
                        return currentSpeed >= average && bothHigherThanZero;
                    }
                }
        );

        //subscribe to observables
        filterGtEq.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("gteq " + limit + " mps: " + integer);
            }
        });

        averageSpeed.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("rolling average of last " + windowSize + " speed values: " + integer);
            }
        });

        trendHappening.subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {

                if (aBoolean) {
                    LOGGER.info("trend happening: current speed is higher than rolling average");
                }
            }
        });

    }

    private void run() {

        String inputLine = "";

        Scanner scanIn = new Scanner(System.in, Charsets.UTF_8.name());

        while (!inputLine.equals("stop")) {
            inputLine = scanIn.nextLine();
            consoleInputLines.onNext(inputLine);
        }

        consoleInputLines.onCompleted();
        scanIn.close();

    }
}
