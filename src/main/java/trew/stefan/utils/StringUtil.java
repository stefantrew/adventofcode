package trew.stefan.utils;

public class StringUtil {

    public static boolean IsPalindromes(Long number) {
        return IsPalindromes(number.toString());
    }

    public static boolean IsPalindromes(Integer number) {
        return IsPalindromes(number.toString());
    }

    public static boolean IsPalindromes(String str) {

        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(str.length() - i - 1)) return false;
        }

        return true;
    }
}
