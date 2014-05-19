package it.cybion.socialeyeser.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.functions.Action1;
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

        //build observables "topology"
        final Speedometer<String> tSpeedometer = new Speedometer<String>(consoleInputLines, Schedulers.io());

        final Observable<Integer> currentSpeed = tSpeedometer.bufferForSecs(1, TimeUnit.SECONDS);

        final Average averageObservable = new Average(currentSpeed, Schedulers.computation());

        final int windowSize = 5;
        final Observable<Integer> averageSpeed = averageObservable.movingOf(windowSize);

        final FilterInteger filterInteger = new FilterInteger(currentSpeed, Schedulers.computation());

        final int limit = 3;
        final Observable<Integer> filterGtEq = filterInteger.filterGtEq(limit);

        //subscribe loggers
        filterGtEq.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("gteq " + limit + " mps: " + integer);
            }
        });

        //subscribe loggers
        averageSpeed.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("avg mps of last " + windowSize + " speed values: " + integer);
            }
        });

    }

    private void run() {


        String inputLine = "";

        Scanner scanIn = new Scanner(System.in);

        while (!inputLine.equals("stop")) {
            inputLine = scanIn.nextLine();
            consoleInputLines.onNext(inputLine);
        }

        consoleInputLines.onCompleted();
        scanIn.close();

    }
}
