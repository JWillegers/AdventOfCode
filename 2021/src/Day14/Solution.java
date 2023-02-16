package Day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    private int nOfLines = 100;
    private String[] array = new String[nOfLines];
    private final String polymer = "HHKONSOSONSVOFCSCNBC";
    private Map<String, Long> occurence = new HashMap<>();
    //private final String polymer = "NNCB";

    public static void main(String[] args) {
        Solution part = new Solution();
        part.setup();
        part.solution(part.occurence,1);
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2021/src/Day15/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
            for (int i = 0; i < polymer.length() - 1; i++) {
                String toAdd = polymer.charAt(i) + String.valueOf(polymer.charAt(i + 1));
                if (occurence.containsKey(toAdd)) {
                    occurence.put(toAdd,occurence.get(toAdd) + 1);
                } else {
                    occurence.put(toAdd, (long) 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution(Map<String, Long> frequency, int counter) {
        Map<String, Long> newFrequency = new HashMap<>();
        for (String couple : frequency.keySet()) {
            String letter = "";
            for (int i = 0; i < nOfLines; i++) {
                if (array[i].contains(couple)) {
                    letter = array[i].split(" ")[2];
                }
            }
            String newKey = couple.charAt(0) + letter;
            if (newFrequency.containsKey(newKey)) {
                long number = frequency.get(couple) + newFrequency.get(newKey);
                newFrequency.put(newKey, number);
            } else {
                newFrequency.put(newKey, frequency.get(couple));
            }
            newKey = letter + couple.charAt(1);
            if (newFrequency.containsKey(newKey)) {
                long number = frequency.get(couple) + newFrequency.get(newKey);
                newFrequency.put(newKey, number);
            } else {
                newFrequency.put(newKey, frequency.get(couple));
            }
        }
        if (counter == 10 || counter == 40) {
            Map<Character, Long> letterFrequency = new HashMap<>();
            for (String letters : newFrequency.keySet()) {
                char letter = letters.charAt(0);
                if (letterFrequency.containsKey(letter)) {
                    long number = newFrequency.get(letters) + letterFrequency.get(letter);
                    letterFrequency.put(letter, number);
                } else {
                    letterFrequency.put(letter, newFrequency.get(letters));

                }
            }

            long mostCommon = -1;
            long leastCommon = -1;
            for (long number : letterFrequency.values()) {
                if (mostCommon == -1) {
                    mostCommon = number;
                    leastCommon = number;
                } else if (number > mostCommon) {
                    mostCommon = number;
                } else if (number < leastCommon) {
                    leastCommon = number;
                }
            }

            System.out.println(mostCommon - leastCommon - 1);
            if (counter == 10) {
                solution(newFrequency, counter + 1);
            }

        } else {
            solution(newFrequency, counter + 1);
        }
    }
}
