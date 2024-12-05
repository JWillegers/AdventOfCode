package y2024.Solutions;


import Util_Java.Parse;

import java.io.IOException;
import java.util.*;

public class Day5 extends Solution {
    Map<Integer, List<Integer>> rules;
    List<List<Integer>> page_numbers;
    List<List<Integer>> incorrect_page_numbers;


    public Day5() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        List<String> input = parser.readFile(day);
        List<List<String>> blocks = parser.toBlocks(input);

        // block 0: all the x|y rules
        // parse those into a hashmap
        rules = new HashMap<>();
        for (String rule : blocks.get(0)) {
            String[] rule_split = rule.split("\\|");
            int first = Integer.parseInt(rule_split[0]);
            int second = Integer.parseInt(rule_split[1]);

            // if first is a key, update its list
            if (rules.containsKey(first)) {
                rules.get(first).add(second);
            } else {
                // key doesn't exist, add key
                rules.put(first, new ArrayList<>(List.of(second)));
            }

        }

        // block 1: all the page numbers
        page_numbers = parser.toIntCommaSeparated(blocks.get(1));
    }

    /**
     * Check if update is valid by the X|Y rule
     * under the assumption, either X|Y exists or Y|X exists in the raw input
     * @param update List of Integers
     * @return if update is valid
     */
    private boolean checkValidityUpdate(List<Integer> update) {
        // loop over every number in update
        for (int i = 0; i < update.size(); i++) {
            int former = update.get(i);
            // loop over all subsequent numbers
            for (int j = i + 1; j < update.size(); j++) {
                int latter = update.get(j);

                // check if former|latter exists
                if (rules.get(former) != null && !rules.get(former).contains(latter)) {
                    return false;
                }

                // check if latter|former doesn't exist
                if (rules.get(latter) != null && rules.get(latter).contains(former)) {
                    return false;
                }

            }
        }
        return true;
    }

    @Override
    public String partOne() {
        int sum_middle_page_number = 0;
        List<Integer> valid_index = new ArrayList<>();
        // loop over all pages
        for (int k = 0; k < page_numbers.size(); k++) {
            List<Integer> update = page_numbers.get(k);
            // find the middle page number and add to total
            if (checkValidityUpdate(update)) {
                sum_middle_page_number += findMiddleValue(update);
                valid_index.add(k);
            }
        }
        addAllIncorrectPages(valid_index);
        return String.valueOf(sum_middle_page_number);
    }

    private int findMiddleValue(List<Integer> update) {
        return update.get((update.size() - 1) / 2);
    }

    /**
     * prep for part 2 by removing all valid indexes
     *
     * @param valid_index List of indexes of valid updates
     */
    private void addAllIncorrectPages(List<Integer> valid_index) {
        incorrect_page_numbers = new ArrayList<>();
        for (int i = 0; i < page_numbers.size(); i++) {
            if (!valid_index.contains(i)) {
                incorrect_page_numbers.add(page_numbers.get(i));
            }
        }
    }

    @Override
    public String partTwo() {
        int sum_middle_page_number = 0;
        for (List<Integer> update : incorrect_page_numbers) {
            // the tactic is to create a new list that is ordered correctly
            List<Integer> new_order = new ArrayList<>();
            for (int page_number : update) {
                // base case: first number to be added to new list
                if (new_order.isEmpty()) {
                    new_order.add(page_number);
                } else {
                    // loop of new order
                    for (int index = 0; index < new_order.size(); index++) {
                        boolean add_here = true;
                        // for all subsequent numbers
                        for (int k = index; k < new_order.size(); k++) {
                            // check if we can insert before those numbers
                            if (rules.get(new_order.get(k)) != null && rules.get(new_order.get(k)).contains(page_number)) {
                                add_here = false;
                                break;
                            }
                        }

                        if (add_here) {
                            // case: add at beginning or somewhere in the middle
                            new_order.add(index, page_number);
                            break;
                        } else if (index + 1 == new_order.size()) {
                            // case: add at the end
                            new_order.add(page_number);
                            break;
                        }
                    }
                }
            }
            sum_middle_page_number += findMiddleValue(new_order);
        }
        return String.valueOf(sum_middle_page_number);
    }

    @Override
    public int getDay() {
        return 5;
    }

    public static void main(String[] args) throws IOException {
        Day5 d = new Day5();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
