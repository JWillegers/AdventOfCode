package y2021.src.Day16;

import java.util.List;

public class Packet {
    protected int version;
    protected int typeID;
    protected long value;
    protected int operator;
    protected int label;
    protected List<Packet> innerPackets;
}
