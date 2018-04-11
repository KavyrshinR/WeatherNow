package ru.kavyrshin.weathernow.util;

import org.junit.Assert;
import org.junit.Test;


public class MiPerHourFromMeterPerSecTest {


    @Test
    public void checkMiPerHourFromMeterPerSec() throws Exception {
        Assert.assertEquals(Utils.getMiPerHourFromMeterPerSec(0), 0, 0.01);
        Assert.assertEquals(Utils.getMiPerHourFromMeterPerSec(5), 11.1847, 0.01);
        Assert.assertEquals(Utils.getMiPerHourFromMeterPerSec(10), 22.3694, 0.01);
    }

}