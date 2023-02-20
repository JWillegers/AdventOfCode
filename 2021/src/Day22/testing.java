package Day22;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
    void caseFourPlane() {

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

    @Test
    void caseTwoDivideNotZ() {
        PartB partB = new PartB();
        Cube cube = new Cube(false);
        cube.generatePerfectCube(0, 10);
        List<Cord> toBeRemoved = new ArrayList<>();
        for (Cord c : cube.corners) {
            if (c.z == 10 && c.y == 10) {
                toBeRemoved.add(c);
            }
        }
        for (Cord c: toBeRemoved) {
            cube.removeCorner(c);
        }
        List<Cube> returnList = partB.caseTwoDivide(cube, false);
        assertEquals(2, returnList.size());
        assertEquals(2, returnList.get(0).corners.size());
        assertEquals(4, returnList.get(1).corners.size());
        assertFalse(returnList.get(0).on);
        assertFalse(returnList.get(0).on);
        for (Cord c: returnList.get(0).corners) {
            assertEquals(10, c.z);
        }
        for (Cord c: returnList.get(1).corners) {
            assertEquals(0, c.z);
        }
    }

    @Test
    void caseTwoDivideZ() {
        PartB partB = new PartB();
        Cube cube = new Cube(true);
        cube.generatePerfectCube(0, 10);
        List<Cord> toBeRemoved = new ArrayList<>();
        for (Cord c : cube.corners) {
            if (c.x == 10 && c.y == 0) {
                toBeRemoved.add(c);
            }
        }
        for (Cord c: toBeRemoved) {
            cube.removeCorner(c);
        }
        List<Cube> returnList = partB.caseTwoDivide(cube, true);
        assertEquals(2, returnList.size());
        assertEquals(2, returnList.get(0).corners.size());
        assertEquals(4, returnList.get(1).corners.size());
        assertTrue(returnList.get(0).on);
        assertTrue(returnList.get(1).on);
        for (Cord c: returnList.get(0).corners) {
            assertEquals(0, c.y);
        }
        for (Cord c: returnList.get(1).corners) {
            assertEquals(10, c.y);
        }
    }

    @Test
    void caseTwoXline() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(true);
        cube1.generatePerfectCube(0, 10);
        List<Cord> toBeRemoved = new ArrayList<>();
        for (Cord c : cube1.corners) {
            if (c.z == 10 && c.y == 10) {
                toBeRemoved.add(c);
            }
        }
        Cube cube2 = new Cube(true);
        cube2.generateOffsetCube(-5,5,5,20);
        List<Cube> returnList = partB.caseTwo(cube1, toBeRemoved, cube2);
        assertEquals(2, returnList.size());
        Map<String, Cord> minmax0 = partB.findMinMaxOfCube(returnList.get(0));
        String min0 = partB.minimum;
        String max0 = partB.maximum;
        assertTrue(returnList.get(0).on);
        assertEquals(0, minmax0.get(min0).x);
        assertEquals(0, minmax0.get(min0).y);
        assertEquals(0, minmax0.get(min0).z);
        assertEquals(10, minmax0.get(max0).x);
        assertEquals(10, minmax0.get(max0).y);
        assertEquals(4, minmax0.get(max0).z);
        Map<String, Cord> minmax1 = partB.findMinMaxOfCube(returnList.get(1));
        String min1 = partB.minimum;
        String max1 = partB.maximum;
        assertTrue(returnList.get(0).on);
        assertEquals(0, minmax1.get(min1).x);
        assertEquals(0, minmax1.get(min1).y);
        assertEquals(5, minmax1.get(min1).z);
        assertEquals(10, minmax1.get(max1).x);
        assertEquals(4, minmax1.get(max1).y);
        assertEquals(10, minmax1.get(max1).z);
    }

    @Test
    void caseTwoYline() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(false);
        cube1.generatePerfectCube(0, 10);
        List<Cord> toBeRemoved = new ArrayList<>();
        for (Cord c : cube1.corners) {
            if (c.z == 0 && c.x == 10) {
                toBeRemoved.add(c);
            }
        }
        Cube cube2 = new Cube(false);
        cube2.generateOffsetCube(3,-3,-10,16);
        List<Cube> returnList = partB.caseTwo(cube1, toBeRemoved, cube2);
        assertEquals(2, returnList.size());
        Map<String, Cord> minmax0 = partB.findMinMaxOfCube(returnList.get(0));
        String min0 = partB.minimum;
        String max0 = partB.maximum;
        assertFalse(returnList.get(0).on);
        assertEquals(0, minmax0.get(min0).x);
        assertEquals(0, minmax0.get(min0).y);
        assertEquals(7, minmax0.get(min0).z);
        assertEquals(10, minmax0.get(max0).x);
        assertEquals(10, minmax0.get(max0).y);
        assertEquals(10, minmax0.get(max0).z);
        Map<String, Cord> minmax1 = partB.findMinMaxOfCube(returnList.get(1));
        String min1 = partB.minimum;
        String max1 = partB.maximum;
        assertFalse(returnList.get(1).on);
        assertEquals(0, minmax1.get(min1).x);
        assertEquals(0, minmax1.get(min1).y);
        assertEquals(0, minmax1.get(min1).z);
        assertEquals(2, minmax1.get(max1).x);
        assertEquals(10, minmax1.get(max1).y);
        assertEquals(6, minmax1.get(max1).z);
    }

    @Test
    void caseTwoZline() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(false);
        cube1.generatePerfectCube(0, 10);
        List<Cord> toBeRemoved = new ArrayList<>();
        for (Cord c : cube1.corners) {
            if (c.y == 0 && c.x == 10) {
                toBeRemoved.add(c);
            }
        }
        Cube cube2 = new Cube(true);
        cube2.generateOffsetCube(6,-12,-6,18);
        List<Cube> returnList = partB.caseTwo(cube1, toBeRemoved, cube2);
        assertEquals(2, returnList.size());
        Map<String, Cord> minmax0 = partB.findMinMaxOfCube(returnList.get(0));
        String min0 = partB.minimum;
        String max0 = partB.maximum;
        assertFalse(returnList.get(0).on);
        assertEquals(0, minmax0.get(min0).x);
        assertEquals(7, minmax0.get(min0).y);
        assertEquals(0, minmax0.get(min0).z);
        assertEquals(10, minmax0.get(max0).x);
        assertEquals(10, minmax0.get(max0).y);
        assertEquals(10, minmax0.get(max0).z);
        Map<String, Cord> minmax1 = partB.findMinMaxOfCube(returnList.get(1));
        String min1 = partB.minimum;
        String max1 = partB.maximum;
        assertFalse(returnList.get(1).on);
        assertEquals(0, minmax1.get(min1).x);
        assertEquals(0, minmax1.get(min1).y);
        assertEquals(0, minmax1.get(min1).z);
        assertEquals(5, minmax1.get(max1).x);
        assertEquals(6, minmax1.get(max1).y);
        assertEquals(10, minmax1.get(max1).z);
    }

    @Test
    void caseTwoProcess() {
        PartB partB = new PartB();
        Cube cube1 = new Cube(true);
        cube1.generatePerfectCube(0, 10);
        List<Cord> toBeRemoved = new ArrayList<>();
        for (Cord c : cube1.corners) {
            if (c.y == 0 && c.x == 10) {
                toBeRemoved.add(c);
            }
        }
        Cube cube2 = new Cube(true);
        cube2.generateOffsetCube(6,-14,-6,18);
        List<Cube> pc = new ArrayList<>();
        pc.add(cube2);
        List<Cube> returnList = partB.process(cube1, pc);
        assertEquals(2, returnList.size());
        Map<String, Cord> minmax0 = partB.findMinMaxOfCube(returnList.get(0));
        String min0 = partB.minimum;
        String max0 = partB.maximum;
        assertTrue(returnList.get(0).on);
        assertEquals(0, minmax0.get(min0).x);
        assertEquals(5, minmax0.get(min0).y);
        assertEquals(0, minmax0.get(min0).z);
        assertEquals(10, minmax0.get(max0).x);
        assertEquals(10, minmax0.get(max0).y);
        assertEquals(10, minmax0.get(max0).z);
        Map<String, Cord> minmax1 = partB.findMinMaxOfCube(returnList.get(1));
        String min1 = partB.minimum;
        String max1 = partB.maximum;
        assertTrue(returnList.get(1).on);
        assertEquals(0, minmax1.get(min1).x);
        assertEquals(0, minmax1.get(min1).y);
        assertEquals(0, minmax1.get(min1).z);
        assertEquals(5, minmax1.get(max1).x);
        assertEquals(4, minmax1.get(max1).y);
        assertEquals(10, minmax1.get(max1).z);
    }
}
