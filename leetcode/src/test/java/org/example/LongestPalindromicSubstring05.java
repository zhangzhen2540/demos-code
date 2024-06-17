package org.example;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Date;

public class LongestPalindromicSubstring05 {

    @Test
    public void timeAdd() {
        Instant now = Instant.now();
        Instant localDateTime = now.plusSeconds(60 * 60);
        System.out.println(now);
        System.out.println(localDateTime);

        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date.from(now)));
    }

    @Test
    public void test() {
//        longestPalindrome("");

        String[] idcardImsg = new String[2];
        int[] nums = new int[211111];
        System.out.println(Arrays.toString(idcardImsg));
        System.out.println(Arrays.toString(nums));
    }



    public void longestPalindrome(String s) {
        int n = s.length();
        int r = 0;

        for (int i = 0; i < s.length(); i++) {




        }
    }

    @Test
    public void print() {
        int n = 7;
        int m = 7;
        int[][] nums = new int[n][m];

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                nums[i][j] = (i + 1) * 10 + (j + 1);
            }
        }
        print(nums);


        /**
         *
         * f(i,j) = f(i + 1, j - 1)
         *
         * for k: 0-n
         *  for i = n - 1; i >= 0; i--:
         *      f(i, i+k) = f(i + 1, i + k - 1)
         *
         *
         *   -- for j = i + k; j >= i; j--:
         *
         *
         * 1-1 2-2 3-3 4-4 5-5 6-6 7-7
         * 6-7 5-6 4-5 3-4 2-3 1-2
         * 5-7 4-6 3-5 2-4 1-3
         * 4-7 3-6
         */

    }

    private void print(int[][] nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.println(Arrays.toString(nums[i]));
        }
        System.out.println("--------------");
    }
}
