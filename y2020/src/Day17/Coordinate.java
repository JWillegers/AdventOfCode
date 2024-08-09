package y2020.src.Day17;

public class Coordinate {
    protected int x;
    protected int y;
    protected int z;
    protected boolean active;
    public Coordinate(int x, int y, int z, boolean active) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.active = active;
    }

    public Coordinate(Coordinate cord) {
        this.x = cord.x;
        this.y = cord.y;
        this.z = cord.z;
        this.active = cord.active;
    }
}