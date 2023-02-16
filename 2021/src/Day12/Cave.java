package Day12;

import java.util.ArrayList;
import java.util.List;

public class Cave {
    protected String name;
    protected List<String> neighbours;

    public Cave(String name, List<String> neighbours) {
        this.neighbours = new ArrayList<>();
        this.neighbours = neighbours;
        this.name = name;
    }
}
