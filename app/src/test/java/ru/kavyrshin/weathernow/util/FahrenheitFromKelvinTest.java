package ru.kavyrshin.weathernow.util;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class FahrenheitFromKelvinTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void checkFahrenheitFromKelvin() throws Exception {
        Assert.assertEquals(Utils.getFahrenheitFromKelvin(100), -279.67, 0.01);
        Assert.assertEquals(Utils.getFahrenheitFromKelvin(250), -9.67, 0.01);
        Assert.assertEquals(Utils.getFahrenheitFromKelvin(350), 170.33, 0.01);
    }

    @Test
    public void checkIllegalKelvinArgument() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("value kelvin is negative");

        Assert.assertEquals(Utils.getCelsiusFromKelvin(-1), -274.15, 0.01);
    }
}