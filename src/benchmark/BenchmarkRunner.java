package benchmark;

import org.openjdk.jmh.profile.HotspotThreadProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.profile.HotspotMemoryProfiler;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .addProfiler(HotspotMemoryProfiler.class)
                .addProfiler(HotspotThreadProfiler.class)
                .build();
        new Runner(opt).run();
//        org.openjdk.jmh.Main.main(args);
    }

}
