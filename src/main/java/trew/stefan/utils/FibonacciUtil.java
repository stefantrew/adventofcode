package trew.stefan.utils;


import java.math.BigInteger;
import java.util.HashMap;

public class FibonacciUtil {

    private HashMap<Integer, Long> fibonacciList = new HashMap<>();

    public FibonacciUtil() {
        buildSet();
    }

    public static BigInteger fibOffset(BigInteger n) {

        BigInteger a = new BigInteger(FibFixtures.get_500000());
        BigInteger b = new BigInteger(FibFixtures.get_500001());

        return getBigInteger(n, a, b, 500000);
    }

    public static BigInteger fib(BigInteger n) {
        int num = n.intValue();
        if (num < 500000)
            return getBigInteger(n, new BigInteger("0"), new BigInteger("1"), 0);
        else if (num < 750000)
            return getBigInteger(n, new BigInteger(FibFixtures.get_750000()), new BigInteger(FibFixtures.get_750001()), 750000);
        else if (num < 900000)
            return getBigInteger(n, new BigInteger(FibFixtures.get_900000()), new BigInteger(FibFixtures.get_900001()), 900000);
        else if (num < 1000000)
            return getBigInteger(n, new BigInteger(FibFixtures.get_500000()), new BigInteger(FibFixtures.get_500001()), 500000);
        else if (num < 1100000)
            return getBigInteger(n, new BigInteger(FibFixtures.get_1000000()), new BigInteger(FibFixtures.get_1000001()), 1000000);
        else if (num < 1500000)
            return getBigInteger(n, new BigInteger(FibFixtures.get_1100000()), new BigInteger(FibFixtures.get_1100001()), 1100000);
        else
            return getBigInteger(n, new BigInteger(FibFixtures.get_1500000()), new BigInteger(FibFixtures.get_1500001()), 1500000);
    }

    private static BigInteger getBigInteger(BigInteger n, BigInteger a, BigInteger b, int offset) {
        System.out.println(offset);
        int m = n.intValue();
        int m_pos = Math.abs(m);
        if (m == offset) return a;
        if (m_pos == offset + 1) return b;

        BigInteger c = a;
        for (int i = offset + 2; i <= m_pos; i++) {
            c = a.add(b);

            a = b;
            b = c;
        }

        if (m < 0 && m % 2 == 0) {
            c = c.negate();
        }

        return c;
    }

    private void buildSet() {
        long a = 0;
        long b = 1;

        fibonacciList.put(0, 0L);
        fibonacciList.put(1, 1L);
        for (int i = 2; i < 1000; i++) {
            long c = a + b;
            fibonacciList.put(i, c);

            a = b;
            b = c;
        }
    }

    public long[] productFib(long prod) {

        int i = 0;

        while (i < fibonacciList.size()) {
            Long a = fibonacciList.get(i);
            Long b = fibonacciList.get(i + 1);

            if (a * b == prod) {
                return new long[]{a, b, 1L};
            }
            if (a * b > prod) {
                return new long[]{a, b, 0L};
            }
            i++;
        }

        return null;
    }


}
