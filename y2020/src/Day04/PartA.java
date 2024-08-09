package y2020.src.Day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartA {
    private static boolean byr;
    private static boolean iyr;
    private static boolean eyr;
    private static boolean hgt;
    private static boolean hcl;
    private static boolean ecl;
    private static boolean pid;

    public static void main(String[] args) {
        try {
            int nOfLines = 999;
            BufferedReader reader = new BufferedReader(new FileReader("y2020/src/Day04/input.txt"));
            String[] array = new String[nOfLines];
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
            reset();
            int valid = 0;
            for (int i = 0; i < nOfLines; i++) {
                if (array[i].isEmpty()) {
                    if (byr && iyr && eyr && hgt && hcl && ecl && pid) {
                        valid++;
                    }
                    reset();
                } else {
                    String[] split = array[i].split(" ");
                    for (int j = 0; j < split.length; j++) {
                        String[] field = split[j].split(":");
                        switch (field[0]) {
                            case "byr":
                                byr = true;
                                break;
                            case "iyr":
                                iyr = true;
                                break;
                            case "eyr":
                                eyr = true;
                                break;
                            case "hgt":
                                hgt = true;
                                break;
                            case "hcl":
                                hcl = true;
                                break;
                            case "ecl":
                                ecl = true;
                                break;
                            case "pid":
                                pid = true;
                                break;
                        }
                    }
                }
            }

            if (byr && iyr && eyr && hgt && hcl && ecl && pid) {
                valid++;
            }

            System.out.println(valid);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void reset() {
        byr = false;
        iyr = false;
        eyr = false;
        hgt = false;
        hcl = false;
        ecl = false;
        pid = false;
    }


}
