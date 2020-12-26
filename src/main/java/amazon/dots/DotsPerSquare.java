package amazon.dots;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;

@Slf4j
public class DotsPerSquare {

    public static String DecimalToBinary(Long decimalNumber, int radix, int padding) {
        StringBuilder number = new StringBuilder(Long.toString(decimalNumber, radix));
        while (number.length() < padding) number.insert(0, "0");
        return number.toString();
    }

    public static void main(String[] args) {

        int n = 3;
        int size = (int) Math.pow(n, 4);
        StringBuilder number = new StringBuilder(Long.toString(n));

        while (number.length() < size) number.insert(0, n);
        BigInteger total = new BigInteger(number.toString());
        log.info("{}", total.toString(n + 1));
        for (long i = 0; i < 43046720; i++) {

            String s = DecimalToBinary(i, n + 1, n * n * n * n);
//            log.info(s);
        }

    }

}
