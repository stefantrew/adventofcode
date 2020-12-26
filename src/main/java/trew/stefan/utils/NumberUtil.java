package trew.stefan.utils;

import org.ejml.simple.SimpleMatrix;

import java.util.*;

enum NumberFactorType {
    DEFICIENT,
    ABUNDANT,
    PROPER
}

public class NumberUtil {

    private static Map<String, byte[]> burstDigitsCache = new HashMap<>();
    private static Map<String, String> maskCache = new Hashtable<>();
    public static int CacheHit = 0;

    public static boolean IsPandigital(String number, int radix) {
        if (number.length() != radix) {
            return false;
        }
        for (int i = 1; i < radix + 1; i++) {
            if (!number.contains(i + "")) return false;
        }

        return true;
    }

    public static boolean IsSquare(long number) {
        double result = Math.sqrt(number);

        return result == (long) result;
    }
//
//    public static boolean IsLychrel(int input) {
//        int n = 0;
//        BigInteger a = BigInteger.valueOf(input);
//        while (n++ < 50) {
//
//            var b = a.GetReverse();
//
//            var c = a + b;
//            // System.Console.WriteLine ("A:{0} B:{1} C:{2}", a, b, c);
//
//            if (StringUtil.IsPalindromes(c.toString())) return false;
//            a = c;
//        }
//
//        return true;
//    }

    public static byte[] BurstDigits(Long a) {
        return BurstDigits(a.toString());
    }

    public static byte[] BurstDigits(String number) {
        if (burstDigitsCache.containsKey(number)) {
            ;
            return burstDigitsCache.get(number);
        }
        ;
        byte[] result = new byte[number.length()];
        char[] array = number.toCharArray();
        for (int i1 = 0; i1 < array.length; i1++) {
            result[i1] = Byte.parseByte("" + array[i1]);
        }
        burstDigitsCache.put(number, result);

        return result;
    }

    public static double[] BurstDigitsDouble(String number) {
        double[] result = new double[number.length()];
        char[] array = number.toCharArray();
        for (int i1 = 0; i1 < array.length; i1++) {
            result[i1] = Double.parseDouble("" + (long) array[i1]) - 48;
        }

        return result;
    }

    public static int countDigits(String input, int target) {
        byte[] digits = NumberUtil.BurstDigits(input);
        int count = 0;

        for (byte b : digits) {
            if (b == target) count++;
        }

        return count;

    }

    public static String ExtractMask(String number, int length) {
        String key = number + "_" + length;
        if (maskCache.containsKey(key)) {
            CacheHit++;
            return maskCache.get(key);
        }
        ;

        StringBuilder mask = new StringBuilder();
        byte[] digits = NumberUtil.BurstDigits(number);
        for (byte digit : digits) {
            mask.append(digit == 0 ? "0" : "1");
        }
//        mask = new StringBuilder(mask.toString().pa(length, '0'));

        maskCache.put(key, mask.toString());
        return mask.toString();
    }

    public static int ImplodeDigits(int[] a) {
        StringBuilder number = new StringBuilder();

        for (int item : a) {
            number.append(item);
        }
        return Integer.parseInt(number.toString());
    }

    public static String ImplodeDigitsToString(byte[] a) {
        StringBuilder number = new StringBuilder();

        for (int item : a) {
            number.append(item);
        }
        return number.toString();
    }

    public static String ImplodeDigitsToString(double[] a) {
        StringBuilder number = new StringBuilder();

        for (double item : a) {
            number.append(Math.abs((long) item) % 10);
        }
        return number.toString();
    }

    public static String ImplodeDigitsToString(SimpleMatrix m) {
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < m.numCols(); i++) {
            number.append(Math.abs((long) m.get(0, i)) % 10);
        }
        return number.toString();
    }

    public static int GetSumOfNthPower(Integer number, int power) {
        String numberString = number.toString();
        int total = 0;

        for (int i1 = 0; i1 < numberString.length(); i1++) {
            int i = Integer.parseInt(numberString.charAt(i1) + "");
            total += (int) Math.pow(i, power);
        }
        return total;
    }

    public static List<Integer> GetDivisors(int n) {
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 1; i < n; i++) {
            if (n % i == 0)
                list.add(i);
        }

        return list;
    }

    public static List<Long> GetDivisors(long n) {
        List<Long> list = new ArrayList<>();

        for (long i = 1; i < n; i++) {
            if (n % i == 0)
                list.add(i);
        }

        return list;
    }

    public static List<Integer> GetAbundantNumbersBelowLimit(int limit) {
        List<Integer> abundant_numbers = new ArrayList<Integer>();
        int i = 0;
        while (i < 28124) {

            NumberFactorType type = NumberUtil.GetNumberFactorType(i);
            if (type == NumberFactorType.ABUNDANT) abundant_numbers.add(i);
            i++;
        }

        return abundant_numbers;
    }

    public static NumberFactorType GetNumberFactorType(int n) {
        List<Integer> factors = GetDivisors(n);

        int sum = factors.stream().reduce(0, Integer::sum);

        if (sum == n) {
            return NumberFactorType.PROPER;
        }

        return sum < n ? NumberFactorType.DEFICIENT : NumberFactorType.ABUNDANT;

    }

    public static String FormatNumberNoRounding(double d, int decimalPlaces) {
        double factor = Math.pow(10, decimalPlaces);
        double truncated = Math.floor(d * factor) / factor;
        return Double.toString(truncated);
    }

    public static String ComputeReciprocal(String input) {
        String result = "";
        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            String temp = c + result;

            int first = input.indexOf(temp);
            if (first == i) break;
            result = temp;
        }
        String new_result = "";
        for (int i = input.indexOf(result); i < input.length() - 1; i++) {
            char c = input.charAt(i);
            String temp = new_result + c;

            int first = input.indexOf(temp, i + 1);
            if (first == -1) {

                break;
            }
            new_result = temp;
        }
        return new_result;
    }

    public static int SumOfDigits(Double n) {
        int sum = 0;
        String s = n.toString();

        for (int i = 0; i < s.length(); i++) {
            sum += Integer.parseInt(s.charAt(i) + "");
        }

        return sum;
    }

    public static int SumOfDigits(Long n) {
        int sum = 0;
        String s = n.toString();

        for (int i = 0; i < s.length(); i++) {
            sum += Integer.parseInt(s.charAt(i) + "");
        }

        return sum;
    }

    public static int SumOfLetters(String s) {
        int sum = 0;

        for (int i = 0; i < s.length(); i++) {
            sum += (int) s.charAt(i) - 64;
        }

        return sum;
    }

    public static String WriteOut(int number) {

        if (number < 20) {
            switch (number) {
                case 0:
                    return "";
                case 1:
                    return "one";
                case 2:
                    return "two";
                case 3:
                    return "three";
                case 4:
                    return "four";
                case 5:
                    return "five";
                case 6:
                    return "six";
                case 7:
                    return "seven";
                case 8:
                    return "eight";
                case 9:
                    return "nine";
                case 10:
                    return "ten";
                case 11:
                    return "eleven";
                case 12:
                    return "twelve";
                case 13:
                    return "thirteen";
                case 14:
                    return "fourteen";
                case 15:
                    return "fifthteen";
                case 16:
                    return "sixteen";
                case 17:
                    return "seventeen";
                case 18:
                    return "eighteen";
                case 19:
                    return "nineteen";
            }
        }
        if (number < 100) {
            int tens = number / 10;
            int ones = number % 10;
            switch (tens) {
                case 2:
                    return "twenty " + WriteOut(ones);
                case 3:
                    return "thirty " + WriteOut(ones);
                case 4:
                    return "forty " + WriteOut(ones);
                case 5:
                    return "fifty " + WriteOut(ones);
                case 6:
                    return "sixty " + WriteOut(ones);
                case 7:
                    return "seventy " + WriteOut(ones);
                case 8:
                    return "eighty " + WriteOut(ones);
                case 9:
                    return "ninety " + WriteOut(ones);
            }
        }
        if (number < 1000) {
            int hundreds = number / 100;
            int tens = number % 100;

            return (hundreds > 1 ? WriteOut(hundreds) : "") + " hundred" + (tens > 0 ? " and " + WriteOut(tens) : "");

        }
        return "one thousand";
    }

}
