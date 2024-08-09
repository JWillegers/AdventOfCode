package y2020.src.Day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    List<String> messages;
    Map<Integer, String> rules;

    public static void main(String[] args) {
        Solution sol = new Solution();
        sol.run();
    }

    private void run() {
        BufferedReader reader;
        boolean mess = false;
        messages = new ArrayList<>();
        rules = new HashMap<>();

        // read input
        try {
            reader = new BufferedReader(new FileReader("y2020/src/Day19/input.txt"));
            String line = reader.readLine();

            while (line != null) {
                if (mess) {
                    messages.add(line);
                } else if (line.equals("")) {
                    mess = true;
                } else {
                    String[] s = line.split(": ");
                    rules.put(Integer.parseInt(s[0]), s[1]);
                }

                // read next line
                line = reader.readLine();
            }
            reader.close();

            // process all rules
            Map<Integer, List<String>> rules_processed = new HashMap<>();
            // while rule 0 is not fully complete
            while (!rules_processed.containsKey(0)) {
                // loop over all rules
                for (Map.Entry<Integer, String> item : rules.entrySet()) {
                    // check if they are processed
                    if (!rules_processed.containsKey(item.getKey())) {
                        rules_processed = process(item, rules_processed);
                    }
                }
            }

            int part1_counter = 0;
            for (String message: messages) {
                if (rules_processed.get(0).contains(message)) {
                    part1_counter++;
                }
            }


            System.out.println("Part1: " + part1_counter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Map<Integer, List<String>> process(Map.Entry<Integer, String> item, Map<Integer, List<String>> rules_processed) {
        List<String> newList = new ArrayList<>();
        // check if rule x is "a" or "b"
        if (item.getValue().equals("\"a\"") || item.getValue().equals("\"b\"")) {
            newList.add(item.getValue().replace("\"", ""));
            rules_processed.put(item.getKey(), newList);
        } else {
            //check if all numbers are in rules_processed
            String[] split = item.getValue().split(" ");
            List<Integer> indices = new ArrayList<>();
            boolean allNumber = true;
            for (String s : split) {
                if (!s.equals("|")) {
                    int i = Integer.parseInt(s);
                    indices.add(i);
                    allNumber = rules_processed.containsKey(i);
                    if (!allNumber) {
                        break;
                    }
                }
            }
            if (allNumber) {
                List<String> newSentence = rules_processed.get(indices.get(0));
                if (item.getValue().contains("|")) {
                    if (split.length == 5) {
                        //looks like this: a: b c | d e
                        // find b c
                        newSentence = appendAll(rules_processed.get(indices.get(0)),
                                rules_processed.get(indices.get(1)));
                        // find d e
                        newSentence.addAll(appendAll(rules_processed.get(indices.get(2)),
                                rules_processed.get(indices.get(3))));
                    } else if (split.length == 3) {
                        // looks like this: a: b | c
                        newSentence.addAll(rules_processed.get(indices.get(1)));
                    } else {
                        System.out.println("Implement split with length:" + split.length + " at index: " + item.getKey());
                        System.exit(1);
                    }
                } else {
                    for (int i = 1; i < indices.size(); i++) {
                        newSentence = appendAll(newSentence, rules_processed.get(indices.get(i)));

                    }
                }
                rules_processed.put(item.getKey(), newSentence);
            }
        }
        return rules_processed;
    }


    private List<String> appendAll(List<String> oldSentence, List<String> append_this) {
        List<String> returnList = new ArrayList<>();
        for (String sentence : oldSentence) {
            for (String add : append_this) {
                returnList.add(sentence + add);
            }
        }
        return returnList;
    }
}
