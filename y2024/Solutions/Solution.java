package y2024.Solutions;

import java.io.IOException;
import y2024.Util.*;

public abstract class Solution {

    public Solution() { }

    public abstract void getInput(int day, Parse p) throws IOException;

    public abstract String partOne();

    public abstract String partTwo();

    public abstract int getDay();
}
