package y2024.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parse{
    public Parse() {}


    /**
     * Read lines of a file
     * @param day day. make sure file names don't have leading zero's
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
}
