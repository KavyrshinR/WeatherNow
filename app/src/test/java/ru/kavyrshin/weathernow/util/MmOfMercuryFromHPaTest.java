package ru.kavyrshin.weathernow.util;


import junit.framework.Assert;

import org.junit.Test;

public class MmOfMercuryFromHPaTest {

    @Test
    public void checkMmOfMercuryFromHPa() throws Exception {
        Assert.assertEquals(Utils.getMmOfMercuryFromHpa(100), 75.0062, 0.01);
        Assert.assertEquals(Utils.getMmOfMercuryFromHpa(500), 375.031, 0.01);
        Assert.assertEquals(Utils.getMmOfMercuryFromHpa(900), 675.055, 0.01);
    }
}
