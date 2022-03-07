package benchmark;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;


import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5)
@Warmup(iterations = 5)
@Fork(1)
public class Benchmark {
    @State(Scope.Benchmark)
    public static class ExecutionPlan {
        //@Param({"https -h google.com", "cat ./input_short", "cat ./input_long", })
        //@Param({"cat ./input_short"})
        //@Param({"cat ./input_long"})
        @Param({"https -h google.com" })
        public String command;

        @Param({">&1", ">&2", "| tee >(1>&2)"})
        //@Param({"| tee >(1>&2)"})
        public String args;

        //@Param({"false", "true"})
        @Param({"false"})
        public String captureOutput;

        @Param({"1", "10", "50"})
        //@Param({"1"})
        public int iterations;

        public DefaultExecutor executor = new DefaultExecutor();
        public ProcessBuilder processBuilder = new ProcessBuilder();
    }


    //@org.openjdk.jmh.annotations.Benchmark
    public void runApache(ExecutionPlan plan, Blackhole blackhole) throws Exception{
        String[] args = {"-c", plan.command, plan.args};
        CommandLine command = CommandLine.parse("/bin/sh");
        command.addArguments(args, false);
        DefaultExecutor executor = plan.executor;

        // redirect output
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
            executor.execute(command);
        }
        blackhole.consume(stdout);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void runProcessBuilder(ExecutionPlan plan, Blackhole blackhole) throws Exception {
        final ProcessBuilder processBuilder = plan.processBuilder.command("/bin/sh", "-c", plan.command, plan.args);
        boolean captureOutput = Boolean.parseBoolean(plan.captureOutput);
        processBuilder.redirectErrorStream(captureOutput);

        // start <iterations> processes to call external programs
        for (int i = 0; i < plan.iterations; i++) {
            startProcess(processBuilder, captureOutput, blackhole);
        }
    }

    private void startProcess(ProcessBuilder processBuilder, boolean captureOutput, Blackhole blackhole) throws  Exception{
        Process process = processBuilder.start();
        if (captureOutput){
            // Capture output
            List<String> results = readOutput(process.getInputStream());
            blackhole.consume(results);
        }
    }

    private List<String> readOutput(InputStream inputStream) throws IOException {

        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {

            List<String> ls = output.lines().collect(Collectors.toList());
            return  ls;
        }
    }
}

