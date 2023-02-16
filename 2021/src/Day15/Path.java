package Day15;

public class Path {
    protected int risk;
    protected int x;
    protected int y;

    public Path(int x, int y, int risk) {
        this.x = x;
        this.y = y;
        this.risk = risk;
    }

    public Path() {}
}
