package Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    private final int nOfLines = 94; //94
    private String[] array = new String[nOfLines];
    private int syntaxErrorScore = 0;

    public static void main(String[] args) {
        Solution part = new Solution();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2021/src/Day10/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution() {
        List<Long> partBlist = new ArrayList<>();
        for (int i = 0; i < nOfLines; i++) {
            int oldSES = syntaxErrorScore;
            List<Character> characters = new ArrayList<>();
            characters = partA(characters, i);
            if (oldSES == syntaxErrorScore) {
                partBlist.add(partB(characters));
            }

        }

        Collections.sort(partBlist);
        for (long test : partBlist) {
            System.out.println(test);
        }
        System.out.println("Part1: " + syntaxErrorScore);
        System.out.println("Part2: " + partBlist.get(partBlist.size() / 2));
    }

    public List<Character> partA(List<Character> characters, int i) {
        final int parenthesesPoints = 3;
        final int squareBracketPoints = 57;
        final int curlyBracketPoints = 1197;
        final int arrowPoints = 25137;
        boolean run = true;
        int j = 0;
        while (run && j < array[i].length()) {
            switch (array[i].charAt(j)) {
                case '(':
                    characters.add('(');
                    break;
                case ')':
                    if (characters.get(characters.size() - 1) == '(') {
                        characters.remove(characters.size() - 1);
                    } else {
                        run = false;
                        syntaxErrorScore += parenthesesPoints;
                    }
                    break;
                case '[':
                    characters.add('[');
                    break;
                case ']':
                    if (characters.get(characters.size() - 1) == '[') {
                        characters.remove(characters.size() - 1);
                    } else {
                        run = false;
                        syntaxErrorScore += squareBracketPoints;
                    }
                    break;
                case '{':
                    characters.add('{');
                    break;
                case '}':
                    if (characters.get(characters.size() - 1) == '{') {
                        characters.remove(characters.size() - 1);
                    } else {
                        run = false;
                        syntaxErrorScore += curlyBracketPoints;
                    }
                    break;
                case '<':
                    characters.add('<');
                    break;
                case '>':
                    if (characters.get(characters.size() - 1) == '<') {
                        characters.remove(characters.size() - 1);
                    } else {
                        run = false;
                        syntaxErrorScore += arrowPoints;
                    }
                    break;
                default:
                    System.out.println("Unknown input: " + array[i].charAt(j));
                    System.exit(1);
            }
            j++;
        }
        return characters;
    }

    public long partB(List<Character> characters) {
        long points = 0;
        while (!characters.isEmpty()) {
            points *= 5;
            char lastOpenChar = characters.get(characters.size() - 1);
            switch (lastOpenChar) {
                case '(':
                    points += 1;
                    break;
                case '[':
                    points += 2;
                    break;
                case '{':
                    points += 3;
                    break;
                case '<':
                    points += 4;
                    break;
                default:
                    System.exit(1);
            }
            characters.remove(characters.size() - 1);
        }
        return points;
    }

}
