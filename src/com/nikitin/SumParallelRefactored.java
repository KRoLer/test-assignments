package com.nikitin;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author KRoLer
 */
public class SumParallelRefactored {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        if (args != null && args.length > 0) {
            String path = args[0];
            int bufferSize = (args.length > 2) ? Integer.parseInt(args[1]) : 4096;
            int poolSize = (args.length > 3) ? Integer.parseInt(args[2]) : 10;

            try {
                BigInteger sum = sumIntegersFromFile(path, bufferSize, poolSize);
                System.out.println(sum.longValueExact());
            } catch (ArithmeticException e) {
                System.err.println("Resulted sum is out of range for the 64 bit long type");
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Time elapsed (Multi thread refactored): " + new SimpleDateFormat("mm:ss:SSS")
                .format(endTime - startTime));
    }

    public static BigInteger sumIntegersFromFile(String path, int bufferSize, int poolSize) {
        BigInteger sum = BigInteger.ZERO;
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        Set<Future<BigInteger>> set = new HashSet<>();

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path))) {
            byte[] ioBuf = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = in.read(ioBuf)) != -1) {
                final byte[] bytesReadOnly = Arrays.copyOf(ioBuf, bytesRead);
                Future<BigInteger> future = pool.submit(new Callable<BigInteger>() {
                    @Override
                    public BigInteger call() throws Exception {
                        BigInteger result = BigInteger.ZERO;
                        ByteBuffer bb = ByteBuffer.wrap(bytesReadOnly).asReadOnlyBuffer();
                        bb.order(ByteOrder.LITTLE_ENDIAN);
                        while (bb.hasRemaining()) {
                            result = result.add(BigInteger.valueOf(bb.getInt()));
                        }
                        return result;
                    }
                });
                set.add(future);
            }

            for (Future<BigInteger> future : set) {
                sum = sum.add(future.get());
            }

            pool.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return sum;
    }
}
