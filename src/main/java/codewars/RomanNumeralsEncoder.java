package codewars;

public class RomanNumeralsEncoder {

    String repeater(String c, int n) {
        StringBuilder s = new StringBuilder();
        while (n-- > 0) s.append(c);
        return s.toString();
    }

    public String solution(int n) {

        StringBuilder s = new StringBuilder();

        if (n <= 3) {
            s.append(build123(n));
        } else if (n == 4) {
            s.append(build4());
        } else if (n <= 8) {
            s.append("V");
            s.append(repeater("I", n - 5));
        } else if (n == 9) {
            s.append("I");
            s.append("X");
        } else if (n <= 13) {
            s.append("X");
            s.append(build123(n - 10));
        } else if (n == 14) {
            s.append("X");
            s.append(build4());
        } else if (n <= 18) {
            s.append("X");

        }

        return s.toString();
    }

    private String build123(int n) {
        return repeater("I", n);
    }

    private String build4() {
        return "IV";
    }
}
/*
Symbol    Value
I          1
V          5
X          10
L          50
C          100
D          500
M          1,000

 Repeatable I, X, C, and M
 I from V X
 X L C
 */
