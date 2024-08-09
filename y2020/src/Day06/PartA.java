package y2020.src.Day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartA {
    public static void main(String[] args) {
        int nOfLines = 2093;
        BufferedReader reader;
        String[] array = new String[nOfLines];

        try {
            reader = new BufferedReader(new FileReader("y2020/src/Day06/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int sum = 0;
        String groupString = "";

        for (int i = 0; i < nOfLines; i++) {
            if (array[i].isEmpty()) {
                List<Character> usedChar = new ArrayList<>();
                for (char charater : groupString.toCharArray()) {
                    if (!usedChar.contains(charater)) {
                        usedChar.add(charater);
                    }
                }
                sum += usedChar.size();
                groupString = "";
            } else {
                groupString = groupString + array[i];
            }
        }

        //last few lines of the file
        List<Character> usedChar = new ArrayList<>();
        for (char charater : groupString.toCharArray()) {
            if (!usedChar.contains(charater)) {
                usedChar.add(charater);
            }
        }
        sum += usedChar.size();

        System.out.println(sum);
    }
}
