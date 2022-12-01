package trew.stefan;

import java.util.Arrays;
import java.util.Collections;

public class Challenge {

    public static void main(String[] args) {


        var arr = new int[]{2, 1, 1, 2, 4, 2, 1, 3, 1, 0, 2, 3, 1, 1,-20, 0, 0, 0, 3, 0, 2, 4, 0, 4, 0, 0, 3, 1, 3, 2, 4, 0, 0, 3, 3, 4, 0, 1, 1, 0, 0, 1, 1, 3, 4, 3, 2, 4, 3, 1, 4, 3, 0, 2, 3, 3, 4, 0, 2, 4, 2, 2, 0, 1, 4, 0, 1, 0, 2, 0, 2, 1, 1, 2, 1, 1, 4, 2, -4, 1, 1, 3, 0, 4, 1, 0, 2, 2, 3, -4, 4, 2, 4, 3, 1, 2, 3, 3, 0, 1, 1, 1, 4, 2, 0, 0, 2, 4, 1, 3, 1, 0, 4, -10, 1, 3, 3, 0, 3, 3, 2, 2, 2, 2, 1, 4, 3, 0, 4, 3, 2, 3, 2, 4, 4, 2, 4, 2, 4, 0, 4, 1, 0, 0, -3, 1, 4, 3, 0, 0, 2, 3, 1, 2, 3, 4, 4, 3, 1, 2, 4, 4, 2, 2, 3, 1, 4, 3, 3, 2, 2, 1, 0, 3, 1, 0, -5, 3, 0, 4, 4, 1, 4, 2, 2, 2, 3, 3, 0, 2, 1, 1, 3, 1, 1, 1, 4, 2, 1, 2, 2, 1};

        System.out.println(Arrays.stream(arr).sum());

        int leftSum = 0;
        int rightSum = 0;
        int leftIndex = 0;
        int rightIndex = arr.length - 1;

        while (leftIndex != rightIndex) {
            if (leftSum < rightSum) {
                leftSum += arr[leftIndex];
                leftIndex++;
            } else {
                rightSum += arr[rightIndex];
                rightIndex--;
            }

        }

        System.out.println(rightSum);
        System.out.println(leftSum);
        System.out.println(leftIndex - 1);



    }
}
