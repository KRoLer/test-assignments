import com.nikitin.Sum;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

/**
 * @author KRoLer
 */

public class SumTest {

    @Test
    public void testSumIntegersFromFileFor5_Elements() {
        String testPath = "./resources/simple.txt";
        BigInteger sum = Sum.sumIntegersFromFile(testPath);
        assertEquals(sum.longValue(), 15L);
    }

    @Test
    public void testSumIntegersFromFileFor1_000_Elements() {
        String testPath = "./resources/1_000.txt";
        BigInteger sum = Sum.sumIntegersFromFile(testPath);
        assertEquals(sum.longValue(), 499500L);
    }

    @Test
    public void testSumIntegersFromFileFor1_000_000_Elements() {
        String testPath = "./resources/1_000_000.txt";
        BigInteger sum = Sum.sumIntegersFromFile(testPath);
        assertEquals(sum.longValue(), 499999500000L);
    }
}
