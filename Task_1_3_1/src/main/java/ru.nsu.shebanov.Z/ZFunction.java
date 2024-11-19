package ru.nsu.shebanov.Z;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ZFunction {

    public static List<Long> findInFile(String fileName, String pattern){
        int BUFFER_SIZE = 10;

        return findInFile(fileName, pattern, BUFFER_SIZE);
    }

    public static List<Long> findInFile(String fileName, String pattern, int bufferSize) {
        List<Long> res = new ArrayList<>();
        int subStringLength = pattern.length();
        try (Reader br = new BufferedReader(new FileReader(fileName))) {
            char[] buffer = new char[bufferSize];
            StringBuilder tempBuffer;
            int readChars;
            long curPos = 0L;
            List<Integer> tempRes;
            tempBuffer = new StringBuilder(pattern);

            while ((readChars = br.read(buffer, 0, bufferSize)) != -1) {
                String curString = new String(buffer, 0, readChars);
                tempRes = (find(curString, pattern));
                for (Integer tempRe : tempRes) {
                    res.add(tempRe + curPos);
                }


                for (int j = 0; j < subStringLength - 1; j++) {
                    tempBuffer.append(buffer[j]);
                }
                if (curPos != 0) {
                    tempRes = find(String.valueOf(tempBuffer), pattern);
                }

                for (int i = 0; i < tempRes.size() && curPos != 0; i++) {
                    res.add(tempRes.get(i) + curPos - subStringLength);
                }

                tempBuffer = new StringBuilder();


                for (int j = bufferSize - subStringLength + 1; j < bufferSize; j++) {
                    tempBuffer.append(buffer[j]);
                }

                curPos += bufferSize;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return res;
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
