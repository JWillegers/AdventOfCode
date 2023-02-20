package Day22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class testing {
    @Test
    void casefour() {
        PartB partB = new PartB();
        Cube cube = new Cube(new Cord(10, 10, 10), new Cord(20,20, 20), true);
        List<Cord> cordList = new ArrayList<>();
        for(int x = 13; x < 18; x+= 3) {
            for (int y = 12; y < 17; y += 4) {
                for (int z = 14; z < 17; z += 2) {
                    cordList.add(new Cord(x, y, z));
                }
            }
        }
    }
}
