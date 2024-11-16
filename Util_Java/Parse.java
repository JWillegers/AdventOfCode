package Util_Java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Util_Java.*;

public class Parse{
    public Parse() {}


    /**
     * Read lines of a file
     * @param day day (1-25). make sure file names don't have leading zero's
     * @return List of lines of the file
     * @throws IOException if file does not exist
     */
    public List<String> readFile(int day) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(STR."y2024/Input/day\{day}.txt"));
        List<String> lines = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            lines.add(line);
            line = reader.readLine();
        }
        return lines;
    }

    /**
     * @param in List of Strings that contains only digits. Empty lines are not allowed
     * @return List of Integers
     */
    public List<Integer> toInt(List<String> in) {
        List<Integer> out = new ArrayList<>();
        for (String str : in) {
            out.add(Integer.parseInt(str));
        }
        return out;
    }

    /**
     * Add elements to a list until an empty line is found.
     * Then add that list to the Return list and start again.
     * This removes empty lines but keeps the data in blocks
     * @param in List of String
     * @return nested list
     */
    public List<List<String>> toBlocks(List<String> in) {
        List<List<String>> out = new ArrayList<>();
        List<String> partialList = new ArrayList<>();
        for (String str : in) {
            if (str.length() > 1) {
                partialList.add(str);
            } else {
                out.add(partialList);
                partialList.clear();
            }
        }
        out.add(partialList);
        return out;
    }

    /**
     * Change list of Strings into 2D char array
     * @param in List of lines read from the input file
     * @return 2D list of char
     */
    public List<List<Character>> make2DMapChar(List<String> in) {
        List<List<Character>> out = new ArrayList<>();
        for (String str : in) {
            List<Character> partialList = new ArrayList<>();
            for (char c : str.toCharArray()) {
                partialList.add(c);
            }
            out.add(partialList);
        }
        return out;
    }

    /**
     * Change list of Strings into 2D int array
     * @param in List of lines read from the input file. Characters in lines must be only digits
     * @return 2D list of int
     */
    public List<List<Integer>> make2DMapInt(List<String> in) {
        List<List<Integer>> out = new ArrayList<>();
        for (String str : in) {
            List<Integer> partialList = new ArrayList<>();
            for (char c : str.toCharArray()) {
                partialList.add(Character.getNumericValue(c));
            }
            out.add(partialList);
        }
        return out;
    }



    /**
     * Change a 2D array of . # and others into a cells with CellStates
     * @param in List of lines read from the input file
     * @return 2D list of CellStates
     */
    public List<List<CellState>> make2DMapCellState(List<String> in) {
        List<List<CellState>> out = new ArrayList<>();
        for (String str : in) {
            List<CellState> partialList = new ArrayList<>();
            for (char c : str.toCharArray()) {
                partialList.add(char2cellstate(c));
            }
            out.add(partialList);
        }
        return out;
    }

    /**
     * @param c char
     * @return which CellState corresponds to c
     */
    public CellState char2cellstate(char c) {
        return switch (c) {
            case '.' -> CellState.EMPTY;
            case '#' -> CellState.WALL;
            case '^', 'N' -> CellState.UP;
            case 'v', 'S' -> CellState.DOWN;
            case '<', 'W' -> CellState.LEFT;
            case '>', 'E' -> CellState.RIGHT;
            default -> CellState.OCCUPIED;
        };
    }


}

