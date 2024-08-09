package y2020.src.Day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartB {
    private static int sum;

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

        sum = 0;
        String groupString = "";
        int groupSize = 0;

        for (int i = 0; i < nOfLines; i++) {
            if (array[i].isEmpty() && groupSize != 0) {
                if (groupSize == 1) {
                    onePerson(groupString);
                } else {
                    multiplePeople(groupString, groupSize);
                }
                groupString = "";
                groupSize = 0;
            } else {
                groupString = groupString + array[i];
                groupSize++;
            }
        }

        //last line(s)
        if (groupSize == 1) {
            onePerson(groupString);
        } else {
            multiplePeople(groupString, groupSize);
        }

        System.out.println(sum);
    }

    public static void multiplePeople(String groupString, int groupSize) {
        List<Character> usedChar = new ArrayList<>();
        for (char charater : groupString.toCharArray()) {
            usedChar.add(charater);
        }

        Collections.sort(usedChar);

        for (int j = 0; j <= usedChar.size() - groupSize; j++) {
            if (usedChar.get(j) == usedChar.get(j + groupSize - 1)) {
                sum++;
            }

        }
    }

    public static void onePerson(String groupString) {
        List<Character> usedChar = new ArrayList<>();
        for (char charater : groupString.toCharArray()) {
            if (!usedChar.contains(charater)) {
                usedChar.add(charater);
            }
        }
        sum += usedChar.size();
    }
}
