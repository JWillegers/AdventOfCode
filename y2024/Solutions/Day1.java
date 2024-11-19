package y2024.Solutions;

import java.io.IOException;
import Util_Java.*;

public class Day1 extends  Solution {

    public Day1() {

    }

    @Override
    public void getInput(int day, Parse p) throws IOException {
        p.readFile(day);
    }

    @Override
    public String partOne(){
        return "Hello";
    }

    @Override
    public String partTwo() {
        return "World";
    }

    @Override
    public int getDay() {
        return 1;
    }
}
