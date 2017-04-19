package com.nikitin;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;

/**
 * @author KRoLer
 */
public class Sum {
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
                "Time elapsed (Single thread): " + new SimpleDateFormat("mm:ss:SSS").format(endTime - startTime));
    }

    public static BigInteger sumIntegersFromFile(String path) {
        BigInteger sum = BigInteger.ZERO;

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path))) {
            byte[] ioBuf = new byte[Integer.BYTES];
            int bytesRead;
            while ((bytesRead = in.read(ioBuf)) != -1) {
                if (bytesRead == Integer.BYTES) {
                    int currInt = ByteBuffer.wrap(ioBuf).order(ByteOrder.LITTLE_ENDIAN).getInt();
                    sum = sum.add(BigInteger.valueOf(currInt));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sum;
    }
}
