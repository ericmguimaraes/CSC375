package com.eguimaraes;

import org.openjdk.jmh.Main;
 
public class RunTest {
 
    public static void main(String[] args) throws Exception {
        Main.main(getArguments("MyBenchmark", 10, 5, 100));
    }
 
    private static String[] getArguments(String className, int nRuns, int runForMilliseconds, int nThreads) {
        return new String[]{className,
            "-i", "" + nRuns,
            "-r", runForMilliseconds + "ms",
            "-t", "" + nThreads,
            "-w", "5ms",
            "-wi", "5"
        };
    }
}