package service;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import org.junit.Test;


public class TriggerBenchmark extends AbstractBenchmark {

    @BenchmarkOptions(benchmarkRounds = 20, warmupRounds = 10)
    @Test
    public void getDataChanges() {
        SQLRequest request = new SQLRequest();
        ServiceTrigger trigger = new ServiceTrigger();
        int count = 100;

        for (int i = 0; i < count; i++) {
            request.insert("Abcdefghijklmnopqrstuvwxyz", count, "Abcdefghijklmnopqrstuvwxyz");
        }

        trigger.getDataChanges("2020-01-27 00:00:00");
        request.close();
    }
}