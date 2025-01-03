package ru.nsu.shebanov.z;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Z function class.
 */
public class Zfunction {

    /**
     * Find pattern in file.
     * Uses default bufferSize
     *
     * @param fileName path to the file
     * @param pattern string pattern to look for
     * @return indices of find patterns
     */
    public static List<Long> findInFile(String fileName, String pattern) {
        return findInFile(fileName, pattern, 1000);
    }

    /**
     * Find pattern in file.
     * Buffer is set by user
     *
     * @param fileName path to the file
     * @param pattern string pattern to look for
     * @param bufferSize size of buffer for reading
     * @return indices of find patterns
     */
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
                    res.add(tempRes.get(i) + curPos + 1 - subStringLength);
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


    /**
     * Find patterns in string.
     *
     * @param text string where to look for a pattern
     * @param pattern pattern to look for
     * @return indices of found patterns
     */
    public static List<Integer> find(String text, String pattern) {
        String concat = pattern + "$" + text;

        int l = concat.length();

        int[] z = new int[l];

        getZarr(concat, z);


        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < l; ++i) {
            if (z[i] == pattern.length()) {
                ans.add(i - pattern.length() - 1);
            }
        }

        return ans;
    }

    /**
     * Util function to create array of precalculated z function.
     *
     * @param str string
     * @param z array of long z to set values
     */
    private static void getZarr(String str, int[] z) {

        int n = str.length();
        int l = 0;
        int r = 0;

        for (int i = 1; i < n; ++i) {
            if (i > r) {
                l = r = i;
                while (r < n && str.charAt(r - l) == str.charAt(r)) {
                    r++;
                }
                z[i] = r - l;
                r--;
            } else {
                int k = i - l;

                if (z[k] < r - i + 1) {
                    z[i] = z[k];
                } else {
                    l = i;
                    while (r < n && str.charAt(r - l) == str.charAt(r)) {
                        r++;
                    }
                    z[i] = r - l;
                    r--;
                }
            }
        }
    }

}