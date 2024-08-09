package y2020.src.Day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PartB {
    public static void main(String[] args) {
        new PartB().solution();
    }

    public void solution() {
        int nOfLines = 594;
        BufferedReader reader;
        String[] array = new String[nOfLines];

        try {
            reader = new BufferedReader(new FileReader("y2020/src/Day07/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Map<String, Integer> bagsToBeProcessed = new HashMap<>();
        bagsToBeProcessed.put("shiny gold", 1);
        int sum = 0;

        for (int a = 0; a < 5; a++) {
            for (int i = 0; i < nOfLines; i++) {
                String[] split1 = array[i].split(",");
                boolean delete = false;
                boolean alreadyProcessed = false;
                String outerbag = split1[0].split(" ")[0] + " " + split1[0].split(" ")[1];
                for (int j = 0; j < split1.length; j++) {
                    String[] split2 = split1[j].split(" ");
                    String innerbag = split2[split2.length - 3] + " " + split2[split2.length - 2];
                    if (bagsToBeProcessed.containsKey(outerbag)) {
                        if (split2[split2.length - 3].equals("no")) {
                            sum += bagsToBeProcessed.get(outerbag);
                            alreadyProcessed = true;
                        } else {
                            alreadyProcessed = false;
                            try {
                                int amount = Integer.parseInt(split2[split2.length - 4]) * bagsToBeProcessed.get(outerbag);
                                if (bagsToBeProcessed.containsKey(innerbag)) {
                                    bagsToBeProcessed.replace(innerbag, bagsToBeProcessed.get(innerbag) + amount);
                                } else {
                                    bagsToBeProcessed.put(innerbag, amount);
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                System.exit(1);
                            }


                        }
                        delete = true;
                    }
                }
                if (delete) {
                    if (!alreadyProcessed && !outerbag.equals("shiny gold")) {
                        sum += bagsToBeProcessed.get(outerbag);
                    }
                    bagsToBeProcessed.remove(outerbag);
                }

            }
        }

        System.out.println("Amount of bags to be processed: " + bagsToBeProcessed.size());
        System.out.println("Amount of bags inside my shiny gold bag: " + sum);
    }
}
