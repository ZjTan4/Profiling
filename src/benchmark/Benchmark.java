package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@BenchmarkMode(Mode.AverageTime)
@Measurement(iterations = 5)
@Warmup(iterations = 3)
@Fork(2)
@Threads(8)
public class Benchmark {
    @State(Scope.Benchmark)
    public static class ExecutionPlan {
        final ProcessBuilder processBuilder = new ProcessBuilder("java", "-version");
    }

    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @org.openjdk.jmh.annotations.Benchmark
    public Process runProcessBuilder(ExecutionPlan plan) throws Exception {
        ProcessBuilder processBuilder = plan.processBuilder;
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Capture output
        List<String> results = readOutput(process.getInputStream());
        return process;
    }

    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    //@org.openjdk.jmh.annotations.Benchmark
    public void runApache(Blackhole blackhole) {
        // do nothing
        blackhole.consume(new Object());
    }

    private List<String> readOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines().collect(Collectors.toList());
        }
    }
}
