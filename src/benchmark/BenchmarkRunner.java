package benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;



@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

//    @Benchmark
    public void runProcessBuilder() throws Exception {
        System.out.println("Process Builder");
        Process process = new ProcessBuilder("java", "-v").start();
    }

    @Benchmark
    public void runApache(Blackhole blackhole) {
        // do nothing
        blackhole.consume(new Object());
    }
}
