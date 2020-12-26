package trew.stefan.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Slf4j
public class PrimeUtil {

    private static final Logger logger = LoggerFactory.getLogger(PrimeUtil.class);


    private static List<Long> Primes;
    private static long cache_upper_bound;

    private static List<Long> CircularPrimes = new ArrayList<>();
    private static List<Long> NotCircularPrimes = new ArrayList<>();

    public static long GetGCDByModulus(long value1, long value2) {
        while (value1 != 0 && value2 != 0) {
            if (value1 > value2)
                value1 %= value2;
            else
                value2 %= value1;
        }
        return Math.max(value1, value2);
    }

    private static Boolean Coprime(long value1, long value2) {
        if (value1 % 2 == 0 || value2 % 2 == 0) {
            return false;
        }
        return GetGCDByModulus(value1, value2) == 1;
    }

    public static List<Long> GetCoprimes(int number) {
        List<Long> result = new ArrayList<>();
        result.add(1L);
        for (long i = 2; i < number; i++) {
            if (Coprime(i, number)) result.add(i);
        }

        return result;
    }

//    public static Boolean IsCircularPrime(Long number) {
//
//        if (CircularPrimes.contains(number)) return true;
//        if (NotCircularPrimes.contains(number)) return false;
//
//        var digits = NumberUtil.BurstDigits(number);
//        int size = digits.Length;
//        long[] ints = new long[size];
//        boolean found = true;
//        for (int i = 0; i < size; i++) {
//            String str = "";
//            for (int j = 0; j < size; j++) {
//                str += digits[(i + j) % size];
//            }
//            var x = Int32.Parse(str);
//            ints[i] = x;
//
//            if (!IsPrime(x)) {
//                found = false;
//            }
//        }
//        for (long l : ints) {
//            if (found) {
//                CircularPrimes.add(l);
//            } else {
//                NotCircularPrimes.add(l);
//            }
//        }
//
//        return found;
//    }

    public static BitSet sieveOfEratosthenes(int n) {
        BitSet isPrime = new BitSet(n);

        // Iniially all numbers are prime.
        for (int i = 2; i <= n; i++) {
            isPrime.set(i);
        }

        // mark non-primes <= N using Sieve of Eratosthenes
        for (int i = 2; i * i <= n; i++) {

            // if i is prime, then mark multiples of i as nonprime
            // suffices to consider mutiples i, i+1, ..., N/i
            if (isPrime.get(i)) {
                for (int j = i; i * j <= n; j++) {
                    isPrime.clear(i * j);
                }
            }

        }

        //System.out.println("Found " + isPrime.cardinality() + " primes");
        return isPrime;
    }

    public long GetSumOfPrimesBelowLimit(int limit) {
        return getPrimesBelow(limit).stream().reduce(0L, Long::sum);
    }

    public long GetSumOfPrimesBelowLimit2(int limit) {
        return getPrimesBelowEratosthenes(limit).stream().reduce(0L, Long::sum);
    }

    public long GetSumOfPrimesBelowLimit3(int limit) {
        return getPrimesBelowEratosthenes2(limit).stream().reduce(0L, Long::sum);
    }

    public static List<Long> getPrimeFactors(Long number) {

        List<Long> result = new ArrayList<>();
        int index = 0;
        List<Long> primes = getPrimesBelowEratosthenes2(10000000);
        while (number > 1 && index < 100) {
            long divisor = primes.get(index);
            log.info("Divisor {}", divisor);
            while (number % divisor == 0) {
                result.add(divisor);
                log.info("Set {}", result);
                number /= divisor;
            }
            index++;
        }

        return result;
    }

    private static List<Long> getPrimesBelow(long limit) {
        List<Long> result = new ArrayList<>();

        List<Long> not_primes = new ArrayList<>();

        int i = 1;
        while (i++ < limit) {

            int factor = 1;
            while (factor++ * i < limit) {
                not_primes.add((long) factor * i);
            }
        }

        for (long j = 2; j < limit; j++) {
            if (!not_primes.contains(j)) {
                result.add(j);
            }
        }
        Primes = result;

        return result;
    }

    public long getNumberOfConsecutivePrimes(int a, int b) {

        long n = 0;
        while (true) {

            long p = n * n + a * n + b;
            if (!IsPrime(p)) {
                return n;
            }
            n++;
        }

    }


    public static List<Long> getPrimesBelowEratosthenes2(long limit) {

        long lower_bound = 10000; //Math.max(10000, Primes.Max());
        if (limit <= lower_bound) {
            return getPrimesBelow(limit);
        }

        List<Long> primes = getPrimesBelow(lower_bound);
        // var primes = lower_bound == 10000 ? getPrimesBelow (lower_bound) : Primes;
        Long start = Collections.max(primes);
        logger.info("{}", start);
        logger.info("{}", --limit);

        logger.info("Filter init");
        for (long j = start; j < limit; j += 2) {
            if (j % (limit / 100) == 1) logger.info("{}", j / (limit / 100));
            boolean found = false;
            for (long filter_number : primes) {
                if (j % filter_number == 0) {
                    found = true;
                    break;
                }
                if (!(j > 2 * filter_number)) break;

            }
            if (found) continue;
            primes.add(j);
        }
        logger.info("done with init: {}", primes.size());

        long i = start;
        while (i * i < limit) {

            int factor = 1;
            while (factor++ * i < limit) {
                primes.remove(factor * i);
            }
            i += 2;

        }
        logger.info("done with not primes");
        Primes = primes;
        cache_upper_bound = limit;
        return primes;
    }

    private List<Long> getPrimesBelowEratosthenes(int limit) {
        List<Long> result = new ArrayList<>();

        List<Long> not_primes = new ArrayList<>();
        int i = 3;
        while (i * i < limit) {

            int factor = 1;
            while (factor++ * i < limit) {
                not_primes.add((long) factor * i);
            }
            i += 2;

        }

        result.add((long) 2);
        int c = 0;
        for (long j = 3; j < limit; j += 2) {
            if (!not_primes.contains(j)) {
                result.add(j);
            }
            if (c++ % 1000 == 0) {
                logger.info("{} {}", c, j);
            }
        }

        return result;
    }

    public static Boolean IsPrime(String n) {
        return IsPrime(Long.parseLong(n));
    }

    public static Boolean IsPrime(Long n) {
        if (n <= 1) {
            return false;
        }

        if (n < cache_upper_bound) {
            return Primes.contains(n);
        }

        for (long i = 2; i < n; i++) {
            if (n % i == 0) {
                log.info("Found {}", i);
                return false;
            }
        }

        return true;
    }
}
