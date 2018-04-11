package ru.kavyrshin.weathernow.util;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class CelsiusFromKelvinTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void checkCelsiusFromKelvin() throws Exception {
        Assert.assertEquals(Utils.getCelsiusFromKelvin(100), -173.15, 0.01);
        Assert.assertEquals(Utils.getCelsiusFromKelvin(250), -23.15, 0.01);
        Assert.assertEquals(Utils.getCelsiusFromKelvin(300), 26.85, 0.01);
    }

    @Test
    public void checkIllegalKelvinArgument() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("value kelvin is negative");

        Assert.assertEquals(Utils.getCelsiusFromKelvin(-1), -274.15, 0.01);
    }

}