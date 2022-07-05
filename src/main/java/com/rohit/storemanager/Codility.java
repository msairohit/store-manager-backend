package com.rohit.storemanager;

import java.util.Arrays;

public class Codility {

    public static void main(String[] args) {
//        fibanacci();

        bicycle();

    }

    private static void bicycle() {
        int[] A = {10,0,8,2,-1,12,11,3};
//        int[] A = {5,5};

        Arrays.sort(A);

        int i = 0;
        int j = 1;
        int maxDistance = Integer.MIN_VALUE;
        int res = -1;
        while(j < A.length) {
            int dist = A[j] - A[i];
            if(dist > maxDistance) {
                maxDistance = dist;
                res = (A[j] + A[i]) / 2;
                int temp;
                int rightSum = 0;
                int leftSum = 0;
                if(res % 2 == 0) {
                     leftSum = res - A[i];
                } else {
                    temp = res+1;
                    rightSum = A[j] - temp;
                }
                if(rightSum >= leftSum) {
                    res = rightSum;
                } else {
                    res = leftSum;
                }
            }
            i++;
            j++;
        }

        System.out.println(res);
    }

    private static void fibanacci() {
        int a = 1;
        int b = 35;
        int val;
        int res = 0;
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            val = (i-1) * i;
            if(val >= a && val <= b) {
                res++;
            }
            if(val >= b) {
                break;
            }
        }

        System.out.println(res);
    }
}
