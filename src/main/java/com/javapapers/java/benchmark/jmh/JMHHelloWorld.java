package com.javapapers.java.benchmark.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class JMHHelloWorld {
    private List<Integer> list;
    private int[] array;
    private Random random;

    @Setup(Level.Trial)
    public void init() {
        random = new Random();
        array = new int[100000];
        list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            int randomNumber = random.nextInt();
            array[i] = randomNumber;
            list.add(randomNumber);
        }
    }

    @Benchmark @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void collectionsSort(Blackhole blackhole) {
        Collections.sort(list);
        blackhole.consume(list);
    }

    @Benchmark @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void arraySort(Blackhole blackhole) {
        Arrays.sort(array);
        blackhole.consume(array);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHHelloWorld.class.getSimpleName())
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server")
                .build();
        new Runner(options).run();
    }
}
