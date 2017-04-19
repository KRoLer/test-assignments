package com.nikitin;

/**
 * Created by Dmytro Nikitin on 18.04.17.
 */
public class Runner {
    public static void main(String[] args) {
        // String[] path = {"resources/100_000_000.txt"};
        String[] path = { "/Users/kroler/Development/100_000_000.txt" };
        Sum.main(path);
        System.gc();
        SumParallel.main(path);
        //  System.gc();
        //  SumParallelRefactored.main(path);

        String[] arg = { "/Users/kroler/Development/100_000_000.txt", "8192", "10" };
        System.out.println(" buffer: 8192, threads: 10");
        SumParallelLamda.main(arg);

        String[] arg2 = { "/Users/kroler/Development/100_000_000.txt", "4096", "5" };
        System.out.println(" buffer: 4096, threads: 5");
        SumParallelLamda.main(arg2);

        String[] arg3 = { "/Users/kroler/Development/100_000_000.txt", "1024", "10" };
        System.out.println(" buffer: 1024, threads: 10");
        SumParallelLamda.main(arg3);

        String[] arg4 = { "/Users/kroler/Development/100_000_000.txt", "1024", "5" };
        System.out.println(" buffer: 1024, threads: 5");
        SumParallelLamda.main(arg4);

    }
}
