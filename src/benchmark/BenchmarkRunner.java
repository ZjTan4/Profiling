package benchmark;

import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.HotspotThreadProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.profile.HotspotMemoryProfiler;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BenchmarkRunner {
//    public static void runProcessBuilder() throws Exception {
//        final ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c","https -h google.com >&1");
//        boolean captureOutput = true;
//        processBuilder.redirectErrorStream(captureOutput);
//
//        // start <iterations> processes to call external programs
//        for (int i = 0; i < 5; i++) {
//            startProcess(processBuilder, captureOutput);
//        }
//    }
//
//    private static void startProcess(ProcessBuilder processBuilder, boolean captureOutput) throws  Exception{
//        Process process = processBuilder.start();
//        if (captureOutput){
//            // Capture output
//            List<String> results = readOutput(process.getInputStream());
//            System.out.println(results);
//        }
//    }
//
//    private static List<String> readOutput(InputStream inputStream) throws IOException {
//        List<String> ls = new ArrayList<>();
//        try(BufferedReader output = new BufferedReader(new InputStreamReader(inputStream));) {
//            String line;
//            while ((line = output.readLine()) != null) {
//                ls.add(line);
//            }
//        }
//        return  ls;
//    }
    public static void main(String[] args) throws Exception {
//        PrintStream log = new PrintStream("./out.log");
//        System.setOut(log);
//        runProcessBuilder();
        Options opt = new OptionsBuilder()
                .build();
        new Runner(opt).run();
        org.openjdk.jmh.Main.main(args);
    }
}
