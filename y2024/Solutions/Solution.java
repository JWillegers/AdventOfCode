package y2024.Solutions;

import java.io.IOException;

public abstract class Solution {

    public Solution() { }

    public abstract void getInput(int day) throws IOException;

    public abstract String partOne();

    public abstract String partTwo();

    public abstract int getDay();
}
