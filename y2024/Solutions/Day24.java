package y2024.Solutions;

import java.io.IOException;
import java.util.*;

import Util_Java.*;

public class Day24 extends Solution {
    Map<String, Boolean> memory;
    List<List<String>> logicGates;

    public Day24() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        List<String> input = parser.readFile(day);
        List<List<String>> blocks = parser.toBlocks(input);
        memory = new HashMap<>();
        logicGates = new ArrayList<>();

        // put "y04: 1" etc in map
        for (String addr : blocks.get(0)) {
            String[] parts = addr.split(": ");
            memory.put(parts[0], parts[1].equals("1"));
        }

        // put "ntg XOR fgs -> mjb" etc split in a list
        for (String line : blocks.get(1)) {
            String[] split = line.split(" ");
            List<String> ls = Arrays.asList(split);
            logicGates.add(ls);
        }
    }

    private void process(List<List<String>> toProcess) {
        List<List<String>> unprocessed = new ArrayList<>();
        for (List<String> instruction : toProcess) {
            if (!(memory.containsKey(instruction.get(0)) && memory.containsKey(instruction.get(2)))) {
                unprocessed.add(instruction);
                continue;
            }

            String address = instruction.get(4);
            Boolean bool1 = memory.get(instruction.get(0));
            Boolean bool2 = memory.get(instruction.get(2));

            switch (instruction.get(1)) {
                case "AND":
                    memory.put(address, bool1 && bool2);
                    break;
                case "OR":
                    memory.put(address, bool1 || bool2);
                    break;
                case "XOR":
                    memory.put(address, !(bool1 && bool2) && (bool1 || bool2));
                    break;
            }
        }
        if (!unprocessed.isEmpty()) {
            process(unprocessed);
        }
    }

    @Override
    public String partOne(){
        process(logicGates);
        StringBuilder binary = new StringBuilder();
        for (int i = 64; i >= 0; i--) {
            String key = String.format("z%02d", i);
            if (!memory.containsKey(key)) {
                continue;
            }

            if (memory.get(key)) {
                binary.append("1");
            } else {
                binary.append("0");
            }
        }
        return String.valueOf(Long.parseLong(binary.toString(), 2));
    }

    @Override
    public String partTwo() {
        return "";
    }

    @Override
    public int getDay() {
        return 24;
    }

    public static void main(String[] args) throws IOException {
        Day24 d = new Day24();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
