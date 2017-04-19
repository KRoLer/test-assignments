package com.nikitin;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Created by kroler on 14.11.16.
 */
public class GenerateTestFile {
    public static void main(String[] args) {
        Path p = Paths.get("/Users/kroler/Development/100_000_000.txt");
        //   Path p = Paths.get("D:\\1_000_000_000.txt");

        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(p, CREATE))) {
            Random rand = new Random();
            for (int i = 0; i <= 100_000_000; i++) {
                int ra = rand.nextInt(Integer.MAX_VALUE);
                // System.out.println(ra);
                byte[] two = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ra).array();
                out.write(two, 0, two.length);
            }

        } catch (IOException x) {
            System.err.println(x);
        }
    }
}
