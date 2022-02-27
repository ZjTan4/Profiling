package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.File;

import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@Measurement(iterations = 5)
@Warmup(iterations = 3)
@Fork(2)
@Threads(8)
public class Benchmark {
    @OutputTimeUnit(TimeUnit.SECONDS)
    @org.openjdk.jmh.annotations.Benchmark
    public Process runProcessBuilder() throws Exception {
        final ProcessBuilder processBuilder = new ProcessBuilder("java", "-version");

        Process process = processBuilder.start();

        return process;
    }

    //@org.openjdk.jmh.annotations.Benchmark
    public void runApache(Blackhole blackhole) {
        // do nothing
        blackhole.consume(new Object());
    }
}
