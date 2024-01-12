package com.example.hello;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.testng.AssertJUnit.assertTrue;

public class Testing {

    @Test
    public void testGenerateRandomWidth() {
        Pillar1 testPillar = new Pillar1(0);
        for (int i = 0; i < 10; i++) {
            int width = testPillar.generateRandomWidth();
            assertTrue("Width should be greater than or equal to minWidth", width >= Pillar1.getMinWidth());
            assertTrue("Width should be less than or equal to maxWidth", width <= Pillar1.getMaxWidth());
        }
    }
    @Test
    public void testGenerateRandomDistance() {
        int distance = Pillar1.generateRandomDistance();
        assertTrue("Distance should be greater than or equal to minDistance", distance >= Pillar1.getMinDistance());
        assertTrue("Distance should be less than or equal to maxDistance", distance <= Pillar1.getMaxDistance());
    }

}