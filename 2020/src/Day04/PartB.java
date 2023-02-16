package Day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartB {
    private static boolean byr;
    private static boolean iyr;
    private static boolean eyr;
    private static boolean hgt;
    private static boolean hcl;
    private static boolean ecl;
    private static boolean pid;
    private static boolean eclDone;


    public static void main(String[] args) {
        //Not 100% working solution, 2 edge cases are slipping through


        int nOfLines = 999;
        BufferedReader reader;
        String[] array = new String[nOfLines];
        try {
            reader = new BufferedReader(new FileReader("2020/src/Day04/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.exit(1);
        }

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
                            try {
                                int year = Integer.parseInt(field[1]);
                                byr = year >= 1920 && year <= 2002 && field[1].length() == 4;
                            } catch (NumberFormatException e) {
                                continue;
                            }
                            break;
                        case "iyr":
                            try {
                                int year = Integer.parseInt(field[1]);
                                iyr = year >= 2010 && year <= 2020 && field[1].length() == 4;
                            } catch (NumberFormatException e) {
                                continue;
                            }
                            break;
                        case "eyr":
                            try {
                                int year = Integer.parseInt(field[1]);
                                eyr = year >= 2020 && year <= 2030 && field[1].length() == 4;
                            } catch (NumberFormatException e) {
                                continue;
                            }
                            eyr = true;
                            break;
                        case "hgt":
                            try {
                                if (field[1].length() == 4 && field[1].charAt(2) == 'i' && field[1].charAt(3) == 'n') {
                                    int height = Integer.parseInt(field[1].substring(0, 2));
                                    hgt = height >= 59 && height <= 76;
                                } else if (field[1].length() == 5 && field[1].charAt(3) == 'c' && field[1].charAt(4) == 'm') {
                                    int height = Integer.parseInt(field[1].substring(0, 3));
                                    hgt = height >= 150 && height <= 193;
                                }
                            } catch (NumberFormatException e) {
                                continue;
                            }
                            break;
                        case "hcl":
                            Pattern p = Pattern.compile("#[0-9a-fA-f]+");
                            Matcher m = p.matcher(field[1]);
                            hcl = m.matches() && field[1].length() == 7;
                            break;
                        case "ecl":
                            if (eclDone) {
                                ecl = false;
                            } else {
                                ecl = field[1].equals("amb") || field[1].equals("blu") || field[1].equals("brn")
                                        || field[1].equals("gry") || field[1].equals("grn") || field[1].equals("hzl") || field[1].equals("oth");
                                eclDone = ecl;
                            }
                            break;
                        case "pid":
                            try {
                                int number = Integer.parseInt(field[1]);
                                pid = field[1].length() == 9 && number > 0;
                            } catch (NumberFormatException e) {
                                continue;
                            }
                            break;
                    }
                }
            }
        }

        if (byr && iyr && eyr && hgt && hcl && ecl && pid) {
            valid++;
        }

        System.out.println(valid);
    }

    public static void reset() {
        byr = false;
        iyr = false;
        eyr = false;
        hgt = false;
        hcl = false;
        ecl = false;
        pid = false;
        eclDone = false;
    }

}
