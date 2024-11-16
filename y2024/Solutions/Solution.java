package y2024.Solutions;

import java.io.IOException;
import Util_Java.*;

public abstract class Solution {

    public Solution() { }

    public abstract void getInput(int day, Parse parser) throws IOException;

    public abstract String partOne();

    public abstract String partTwo();

    public abstract int getDay();
}
