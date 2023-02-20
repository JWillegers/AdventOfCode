package Day22;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class testing {

    @Test
    void minMax() {
        PartB partB = new PartB();
        String min = partB.minimum;
        String max = partB.maximum;
        Cube cube = new Cube(true);
        cube.generateOffsetCube(0, 30, -15, 20);
        Map<String, Cord> mm = partB.findMinMaxOfCube(cube);
        assertEquals(0, mm.get(min).x);
        assertEquals(30, mm.get(min).y);
        assertEquals(-15, mm.get(min).z);
        assertEquals(20, mm.get(max).x);
        assertEquals(50, mm.get(max).y);
        assertEquals(5, mm.get(max).z);
    }

    @Test
    void calculateVolume() {
        PartB partB = new PartB();
        Cube cube = new Cube(true);
        cube.generatePerfectCube(-5, 10);
        assertEquals(1000, partB.calculateVolume(cube));
    }

    @Test
    void overlap0() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(true);
        cube1.generatePerfectCube(-5, 10);
        Cube cube2 = new Cube(true);
        cube2.generatePerfectCube(60, 10);
        assertEquals(0, partB.findOverlap(cube1, cube2).size());
    }

    @Test
    void overlap1() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(true);
        cube1.generatePerfectCube(-5, 10);
        Cube cube2 = new Cube(true);
        cube2.generatePerfectCube(0, 10);
        assertEquals(1, partB.findOverlap(cube1, cube2).size());
    }

    @Test
    void overlap2() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(true);
        cube1.generatePerfectCube(-5, 10);
        Cube cube2 = new Cube(true);
        cube2.generateOffsetCube(0, 0, -15, 20);
        assertEquals(2, partB.findOverlap(cube1, cube2).size());
    }

    @Test
    void overlap4() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(true);
        cube1.generatePerfectCube(-5, 10);
        Cube cube2 = new Cube(true);
        cube2.generateOffsetCube(0, -15, -15, 20);
        assertEquals(4, partB.findOverlap(cube1, cube2).size());
    }

    @Test
    void overlap8() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(true);
        cube1.generatePerfectCube(-5, 10);
        Cube cube2 = new Cube(true);
        cube2.generatePerfectCube(-20, 30);
        assertEquals(8, partB.findOverlap(cube1, cube2).size());
    }

    @Test
    void overlapfull8() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(true);
        cube1.generatePerfectCube(-5, 10);
        Cube cube2 = new Cube(true);
        cube2.generatePerfectCube(-5, 10);
        assertEquals(8, partB.findOverlap(cube1, cube2).size());
    }

    @Test
    void overlapAlmostIdentical() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(true);
        cube1.generatePerfectCube(-5, 10);
        Cube cube2 = new Cube(true);
        cube2.generatePerfectCube(-5, 9);
        assertEquals(1, partB.findOverlap(cube1, cube2).size());
    }

    @Test
    void caseFour() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(true);
        cube1.generatePerfectCube(0, 10);
        List<Cord> toBeRemoved = new ArrayList<>();
        for (Cord c : cube1.corners) {
            if (c.z == 0) {
                toBeRemoved.add(c);
            }
        }
        Cube cube2 = new Cube(true);
        cube2.generateOffsetCube(-1,-1,-15,20);
        Cube returnCube = partB.caseFour(cube1, toBeRemoved, cube2);
        Map<String, Cord> minmax = partB.findMinMaxOfCube(returnCube);
        String min = partB.minimum;
        String max = partB.maximum;
        assertEquals(0, minmax.get(min).x);
        assertEquals(0, minmax.get(min).y);
        assertEquals(6, minmax.get(min).z);
        assertEquals(10, minmax.get(max).x);
        assertEquals(10, minmax.get(max).y);
        assertEquals(10, minmax.get(max).z);
    }

    @Test
    void processCaseFour() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(true);
        cube1.generatePerfectCube(0, 10);
        List<Cord> toBeRemoved = new ArrayList<>();
        for (Cord c : cube1.corners) {
            if (c.z == 0) {
                toBeRemoved.add(c);
            }
        }
        Cube cube2 = new Cube(true);
        cube2.generateOffsetCube(-1,-1,-15,20);
        List<Cube> pc = new ArrayList<>();
        pc.add(cube2);
        List<Cube> returnList = partB.process(cube1, pc);
        assertEquals(1, returnList.size());
        Map<String, Cord> minmax = partB.findMinMaxOfCube(returnList.get(0));
        String min = partB.minimum;
        String max = partB.maximum;
        assertEquals(0, minmax.get(min).x);
        assertEquals(0, minmax.get(min).y);
        assertEquals(6, minmax.get(min).z);
        assertEquals(10, minmax.get(max).x);
        assertEquals(10, minmax.get(max).y);
        assertEquals(10, minmax.get(max).z);
    }
}
