package com.documentmatrix.image.similarity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by root on 29/9/15.
 */
public class Test1 {

    private static String SPACE = "  ";

    public static void main(String args[]) {


        File f = new File("/home/vipin/testd");

        File fs[] = f.listFiles();


        StringBuffer stringBuffer = new StringBuffer();
        List<String> files = new ArrayList<>();

        for (File ft : fs) {
            files.add(ft.getName());
        }


        Collections.sort(files, new Comp());

        int i = 0, j = 0, part = 2, max = files.get(0).length();

        Collections.sort(files);
        while (i < files.size()) {
            j = 0;
            while (j < part) {
                stringBuffer.append(files.get(i));
                for (int k = 0; k < (max - files.get(i).length()) + 2; k++)
                    stringBuffer.append(SPACE);
                j++;
                i++;
            }
            stringBuffer.append("\n");

        }

        System.out.println(stringBuffer.toString());
    }

    static class Comp implements Comparator<String> {
        public int compare(String o1, String o2) {
            if (o1.length() > o2.length()) {
                return -1;
            } else if (o1.length() < o2.length()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
