package trew.stefan;

public class BaseConvertUtil {

    public static String DecimalToBinary(Long decimalNumber, int padding) {
        StringBuilder number = new StringBuilder(DecimalToBinary(decimalNumber));
        while (number.length() < padding) number.insert(0, "0");
        return number.toString();
    }

    public static String DecimalToBinary(Long decimalNumber) {

        Long remainder;
        StringBuilder result = new StringBuilder();
        while (decimalNumber > 0) {
            remainder = decimalNumber % 2;
            decimalNumber /= 2;
            result.insert(0, remainder);
        }
        return result.toString();
    }
}
