package y2020.src.Day19;

import java.util.ArrayList;
import java.util.List;

public class Node {
    String value;
    List<Node> options;



    public Node(String value) {
        this.value = value;
        this.options = new ArrayList<>();
    }
    public String getValue() {
        return value;
    }

    public List<Node> getOptions() {
        return options;
    }

    public void addOption(Node n) {
        this.options.add(n);
    }
}
