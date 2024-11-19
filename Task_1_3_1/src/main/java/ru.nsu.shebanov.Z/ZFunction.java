package ru.nsu.shebanov.Z;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ZFunction {

    public static List<Long> findInFile(String fileName, String pattern){
        int BUFFER_SIZE = 10;

        return findInFile(fileName, pattern, BUFFER_SIZE);
    }

    public static List<Long> findInFile(String fileName, String pattern, int bufferSize) {
        List<Long> matches = new ArrayList<>();
        long shift = 0; // Initialize shift outside the loop
        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[bufferSize];
            int bytesRead;
            while ((bytesRead = br.read(buffer)) != -1) {
                builder.append(buffer, 0, bytesRead);

                if (builder.length() > 10000) {
                    List<Integer> res = find(builder.toString(), pattern);
                    for (int val : res) {
                        matches.add((long) (val + shift));
                    }

                    int firstCharIndex = res.isEmpty() ? 0 :
                            Math.max(0, builder.length() - pattern.length() + 1);
                    shift += firstCharIndex;
                    builder.delete(0, firstCharIndex);
                }
            }

            if (builder.length() != 0 && builder.length() > pattern.length()) {
                List<Integer> res = find(builder.toString(), pattern);
                for (int val : res) {
                    matches.add((long) (val + shift));
                }
            }

            return matches;

        } catch (IOException e) {
            System.out.print("Error reading file: " + fileName);
            return new ArrayList<>();
        }
    }


    public static List<Integer> find(String text, String pattern)
    {
        String concat = pattern + "$" + text;

        int l = concat.length();

        int[] Z = new int[l];

        getZarr(concat, Z);


        ArrayList<Integer> ans = new ArrayList<>();
        for(int i = 0; i < l; ++i){
            if(Z[i] == pattern.length()){
                ans.add(i - pattern.length() - 1);
            }
        }

        return ans;
    }

    private static void getZarr(String str, int[] Z) {

        int n = str.length();
        int L = 0, R = 0;

        for(int i = 1; i < n; ++i) {
            if(i > R){
                L = R = i;
                while(R < n && str.charAt(R - L) == str.charAt(R)) {
                    R++;
                }
                Z[i] = R - L;
                R--;
            }
            else{
                int k = i - L;

                if(Z[k] < R - i + 1){
                    Z[i] = Z[k];
                }
                else{
                    L = i;
                    while(R < n && str.charAt(R - L) == str.charAt(R)) {
                        R++;
                    }
                    Z[i] = R - L;
                    R--;
                }
            }
        }
    }

}
