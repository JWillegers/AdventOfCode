package y2020.src.Day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartA {
    public static void main(String[] args) {
        new PartA().solution();
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

        List<String> bags = new ArrayList<>();

        for (int a = 0; a < 6; a++) {
            for (int i = 0; i < nOfLines; i++) {
                String[] split1 = array[i].split(",");
                for (int j = 0; j < split1.length; j++) {
                    String[] split2 = split1[j].split(" ");
                    if (!split2[split2.length - 3].equals("no")) { //x bag contain at least 1 bag
                        String innerbag = split2[split2.length - 3] + " " + split2[split2.length - 2];
                        String outerbag = split1[0].split(" ")[0] + " " + split1[0].split(" ")[1];
                        if (innerbag.equals("shiny gold")) {
                            if (!bags.contains(outerbag)) {
                                bags.add(outerbag);
                            }
                        } else {
                            if (bags.contains(innerbag) && !bags.contains(outerbag)) {
                                bags.add(outerbag);
                            }
                        }
                    }
                }
            }
        }

        System.out.println(bags.size());
    }
}
