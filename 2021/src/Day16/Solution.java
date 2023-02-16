package Day16;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private String binaryInput;
    private int counter;

    public static void main(String[] args) {
        Solution part = new Solution();
        part.toBinary();
        part.solution();
    }

    public void toBinary() {
        String input = ""; //PUT YOUR INPUT HERE
        binaryInput = "";
        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case '0':
                    binaryInput += "0000";
                    break;
                case '1':
                    binaryInput += "0001";
                    break;
                case '2':
                    binaryInput += "0010";
                    break;
                case '3':
                    binaryInput += "0011";
                    break;
                case '4':
                    binaryInput += "0100";
                    break;
                case '5':
                    binaryInput += "0101";
                    break;
                case '6':
                    binaryInput += "0110";
                    break;
                case '7':
                    binaryInput += "0111";
                    break;
                case '8':
                    binaryInput += "1000";
                    break;
                case '9':
                    binaryInput += "1001";
                    break;
                case 'A':
                    binaryInput += "1010";
                    break;
                case 'B':
                    binaryInput += "1011";
                    break;
                case 'C':
                    binaryInput += "1100";
                    break;
                case 'D':
                    binaryInput += "1101";
                    break;
                case 'E':
                    binaryInput += "1110";
                    break;
                case 'F':
                    binaryInput += "1111";
                    break;
                default:
                    System.exit(1);
            }
        }
    }

    public void solution() {
        counter = 0;
        Packet packet = packets();
        System.out.println("Part1: " + sumVersion(packet));
        System.out.println("Part2: " + evaluate(packet));
    }

    public int sumVersion(Packet packet) {
        int sum = packet.version;
        if (packet.innerPackets != null) {
            for (Packet p : packet.innerPackets) {
                sum += sumVersion(p);
            }
        }
        return sum;
    }

    public long evaluate(Packet p) {
        long result = p.value;
        List<Long> innerResult = new ArrayList<>();
        if (p.innerPackets != null) {
            for (Packet packet : p.innerPackets) {
                innerResult.add(evaluate(packet));
            }
        }
        if (p.typeID < 4) {
            //overwriting the 0 from p.value
            if (p.typeID == 0) {
                result = 0;
            } else if (p.typeID == 1) {
                result = 1;
            } else if (p.typeID == 2) {
                result = 999999999;
            }
            for (long number : innerResult) {
                if (p.typeID == 0) {
                    result += number;
                } else if (p.typeID == 1) {
                    result *= number;
                } else if (p.typeID == 2) {
                    result = Math.min(result, number);
                } else if (p.typeID == 3) {
                    result = Math.max(result, number);
                }
            }
        } else if (p.typeID == 5) {
            result = innerResult.get(0) > innerResult.get(1) ? 1 : 0;
        } else if (p.typeID == 6) {
            result = innerResult.get(0) < innerResult.get(1) ? 1 : 0;
        } else if (p.typeID == 7) {
            result = (long) innerResult.get(0) == (long) innerResult.get(1) ? 1 : 0;
        }
        return result;
    }

    public Packet packets() {
        Packet packet = newPacket();
        if (packet.typeID == 4) {
            packet.value = getNumber();
        } else {
            packet.operator = Integer.parseInt(String.valueOf(binaryInput.charAt(counter)));
            packet.innerPackets = new ArrayList<>();
            counter++;
            if (packet.operator == 1) {
                packet.label = Integer.parseInt(binaryInput.substring(counter, counter + 11), 2);
                counter += 11;
                for (int i = 0; i < packet.label; i++) {
                    packet.innerPackets.add(packets());
                }
            } else {
                packet.label = Integer.parseInt(binaryInput.substring(counter, counter + 15), 2);
                counter += 15;
                int currentCounter = counter;
                while (counter < currentCounter + packet.label) {
                    packet.innerPackets.add(packets());
                }
            }
        }
        return packet;
    }

    public Packet newPacket() {
        Packet packet = new Packet();
        packet.version = Integer.parseInt(binaryInput.substring(counter, counter + 3), 2);
        counter += 3;
        packet.typeID = Integer.parseInt(binaryInput.substring(counter, counter + 3), 2);
        counter += 3;
        return packet;
    }

    public long getNumber() {
        String number = "";
        boolean run;
        do {
            run = binaryInput.charAt(counter) == '1';
            counter++;
            number += binaryInput.substring(counter, counter + 4);
            counter += 4;
        } while (run);
        return Long.parseLong(number, 2);
    }
}
