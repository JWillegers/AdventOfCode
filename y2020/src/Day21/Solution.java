package y2020.src.Day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {
    HashMap<String, String> mappedAllergens;
    List<Label> labels;

    public static void main(String[] args) {
        y2020.src.Day21.Solution sol = new y2020.src.Day21.Solution();
        sol.readFile();
        sol.mapAllergens();
        sol.solutionPartOne();
        sol.solutionPartTwo();
    }

    private void readFile() {
        BufferedReader reader = null;

        // read input
        try {
            reader = new BufferedReader(new FileReader("y2020/src/Day21/input.txt"));
            String line = reader.readLine();
            mappedAllergens = new HashMap<>();
            labels = new ArrayList<>();

            while (line != null) {
                String[] split = line.split("contains ");
                List<String> ingredients = new ArrayList<>(Arrays.asList(split[0].split(" ")));
                ingredients.remove(ingredients.size() - 1);
                List<String> allergens = new ArrayList<>(Arrays.asList(split[1].split(", ")));
                String lastAllergen = allergens.remove(allergens.size() - 1);
                lastAllergen = lastAllergen.replace(")", "");
                allergens.add(lastAllergen);

                for (String allergen : allergens) {
                    if (!mappedAllergens.containsKey(allergen)) {
                        mappedAllergens.put(allergen, null);
                    }
                }

                labels.add(new Label(allergens, ingredients));

                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void mapAllergens() {
        while (mappedAllergens.containsValue(null)) {
            for (String allergen : mappedAllergens.keySet()) {
                if (mappedAllergens.get(allergen) == null) {
                    mappedAllergens.put(allergen, mapAllergenToLabel(allergen));
                }
            }
        }
    }

    private String mapAllergenToLabel(String allergen) {
        List<String> possibleIngredients = null;
        for (Label label : labels) {
            if (label.allergens.contains(allergen)) {
                if (possibleIngredients == null) {
                    possibleIngredients = new ArrayList<>();
                    possibleIngredients.addAll(label.ingredients);
                    for (String ingredient : mappedAllergens.values()) {
                        possibleIngredients.remove(ingredient);
                    }
                } else {
                    possibleIngredients = keepDuplicates(possibleIngredients, label);
                }
            }
        }
        if (possibleIngredients == null) {
            System.out.println("No allergens");
            System.exit(1);
        } else if (possibleIngredients.size() == 1) {
            return possibleIngredients.get(0);
        }
        return null;
    }

    private List<String> keepDuplicates(List<String> possibleIngredients, Label label) {
        List<String> toRemove = new ArrayList<>();
        for (String ingredient : possibleIngredients) {
            if (!label.ingredients.contains(ingredient)) {
                toRemove.add(ingredient);
            }
        }
        for (String ingredient : toRemove) {
            possibleIngredients.remove(ingredient);
        }
        return possibleIngredients;
    }

    private void solutionPartOne() {
        int counter = 0;
        for (Label label : labels) {
            for (String ingredient : label.ingredients) {
                if (!mappedAllergens.values().contains(ingredient)) {
                    counter++;
                }
            }
        }
        System.out.println(mappedAllergens);
        System.out.println("Part 1: " + counter);
    }

    private void solutionPartTwo() {
        String solution = "";
        List<String> allergens = new ArrayList<>(List.copyOf(mappedAllergens.keySet()));
        Collections.sort(allergens);
        for (String allergen : allergens) {
            if (!solution.equals("")) {
                solution = solution + ",";
            }
            solution = solution + mappedAllergens.get(allergen);
        }
        System.out.println("Part 2: " + solution);
    }
}
