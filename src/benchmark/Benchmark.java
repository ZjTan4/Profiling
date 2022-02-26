package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 5)
@Warmup(iterations = 3)
public class Benchmark {
    @org.openjdk.jmh.annotations.Benchmark
    public Process runProcessBuilder() throws Exception {
        final ProcessBuilder processBuilder = new ProcessBuilder("java", "-version");
        processBuilder.redirectErrorStream(true);
        File log = new File("./java-version.log");
        processBuilder.redirectOutput(log);

        Process process = processBuilder.start();

        return process;
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void runApache(Blackhole blackhole) {
        // do nothing
        blackhole.consume(new Object());
    }
}
