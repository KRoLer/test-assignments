package com.nikitin;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author KRoLer
 */
public class SumParallel {
    public static class Producer implements Callable {

        private byte[] bytes;

        public Producer(byte[] bytes) {
            this.bytes = bytes;
        }

        @Override
        public BigInteger call() throws Exception {
            BigInteger result = BigInteger.ZERO;
            ByteBuffer bb = ByteBuffer.wrap(bytes).asReadOnlyBuffer();
            bb.order(ByteOrder.LITTLE_ENDIAN);
            while (bb.hasRemaining()) {
                result = result.add(BigInteger.valueOf(bb.getInt()));
            }
            return result;
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        if (args != null && args.length > 0) {
            String path = args[0];
            try {
                BigInteger sum = sumIntegersFromFile(path);
                System.out.println(sum.longValueExact());
            } catch (ArithmeticException e) {
                System.err.println("Resulted sum is out of range for the 64 bit long type");
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println(
                "Time elapsed (Multi thread): " + new SimpleDateFormat("mm:ss:SSS").format(endTime - startTime));
    }


    public static BigInteger sumIntegersFromFile(String path) {
        BigInteger sum = BigInteger.ZERO;
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Set<Future<BigInteger>> set = new HashSet<>();

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path))) {
            byte[] ioBuf = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(ioBuf)) != -1) {
                Callable<BigInteger> callable = new Producer(Arrays.copyOf(ioBuf, bytesRead));
                Future<BigInteger> future = pool.submit(callable);
                set.add(future);
            }

            for(Future<BigInteger> future : set){
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

    private static void getIntsFromBuffer(byte[] ioBuf, int bytesRead) {

        for(int i = 0; i < bytesRead; i=i+4) {

            byte[] bytes = Arrays.copyOfRange(ioBuf,i,i+4);
            //  System.out.println(bytes.length);
            System.out.println(ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt());
            int currInt = ((bytes[3] & 0xFF) << 24) | ((bytes[2] & 0xFF) << 16)
                    | ((bytes[1] & 0xFF) << 8) | (bytes[0] & 0xFF);
            System.out.println("Manual converting: " + currInt);
            // System.out.println(bytes[0] + bytes[1] + bytes[2] + bytes[3]);
        }

    }


}
