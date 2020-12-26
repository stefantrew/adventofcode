package trew.stefan.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermutationUtil {

    private static List<String> result = new ArrayList<>();
    private static List<int[]> result2 = new ArrayList<>();

    public static int[] GetNthPermutation(int[] a, int n) {
        // PermutationUtil
        return a;
    }

    //Prints the array
    public static void printArr(int[] a, int n) {
        String str = "";
        for (int i = 0; i < n; i++) {
            // Console.Write (a[i] + " ");
            str += a[i];

        }
        result.add(str);
        result2.add(a.clone());
        // Console.WriteLine ();
    }

    public static String sortLetters(String input) {
        char[] chars = input.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static boolean isPermutation(Long val1, Long val2) {
        return isPermutation(val1.toString(), val2.toString());
    }

    public static boolean isPermutation(String val1, String val2) {
        return sortLetters(val1).equals(sortLetters(val2));
    }


    public static List<String> getPermutations(int[] input) {
        result = new ArrayList<>();
        heapPermutation(input, input.length, input.length);

        return result;
    }

    public static List<int[]> getPermutationsVectors(int[] input) {
        result2 = new ArrayList<>();
        heapPermutation(input, input.length, input.length);

        return result2;
    }

    //Generating permutation using Heap Algorithm
    private static void heapPermutation(int[] a, int size, int n) {
        // if size becomes 1 then prints the obtained
        // permutation
        if (size == 1)
            printArr(a, n);

        for (int i = 0; i < size; i++) {
            heapPermutation(a, size - 1, n);

            // if size is odd, swap first and last
            // element
            if (size % 2 == 1) {
                int temp = a[0];
                a[0] = a[size - 1];
                a[size - 1] = temp;
            }

            // If size is even, swap ith and last
            // element
            else {
                int temp = a[i];
                a[i] = a[size - 1];
                a[size - 1] = temp;
            }
        }
    }
}
