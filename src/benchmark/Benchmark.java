package benchmark;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;


import java.io.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5)
@Warmup(iterations = 3)
@Fork(2)
@Threads(24)
public class Benchmark {
    @State(Scope.Benchmark)
    public static class ExecutionPlan {
        @Param({"pwd", "date", "du", })
        //@Param({"du", })
        public String command;

        @Param({"false", "true"})
        public String captureOutput;

        @Param({"5", "10", "20"})
        public int iterations;

    }

    //@org.openjdk.jmh.annotations.Benchmark
    public void runProcessBuilder_withoutThreads(ExecutionPlan plan, Blackhole blackhole) throws Exception {
        final ProcessBuilder processBuilder = new ProcessBuilder(plan.command);
        boolean captureOutput = Boolean.parseBoolean(plan.captureOutput);
        processBuilder.redirectErrorStream(captureOutput);

        // start <iterations> processes to call external programs
        for (int i = 0; i < plan.iterations; i++) {
            startProcess(processBuilder, captureOutput, blackhole);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void runApache_withoutThreads(ExecutionPlan plan, Blackhole blackhole) throws Exception{
        CommandLine command = CommandLine.parse(plan.command);
        DefaultExecutor executor = new DefaultExecutor();

        boolean captureOutput = Boolean.parseBoolean(plan.captureOutput);
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        PumpStreamHandler psh = new PumpStreamHandler(stdout);
        if (!captureOutput) {
            // do not capture the stdout
            executor.setStreamHandler(new PumpStreamHandler(null, null, null));
        } else {
            // capture stdout in stream
            executor.setStreamHandler(psh);
        }

        // call external programs
        for (int i = 0; i < plan.iterations; i++) {
            int exitValue = executor.execute(command);
            blackhole.consume(exitValue);
        }
        blackhole.consume(stdout);
    }

    private void startProcess(ProcessBuilder processBuilder, boolean captureOutput, Blackhole blackhole) throws  Exception{
        Process process = processBuilder.start();

        if (captureOutput){
            // Capture output
            List<String> results = readOutput(process.getInputStream());
            blackhole.consume(results);
        }
        int exitCode = process.waitFor();
        blackhole.consume(exitCode);
    }

    private List<String> readOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines().collect(Collectors.toList());
        }
    }
}

