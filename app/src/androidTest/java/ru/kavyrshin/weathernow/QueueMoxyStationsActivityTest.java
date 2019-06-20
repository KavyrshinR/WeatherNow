package ru.kavyrshin.weathernow;

import android.content.pm.ActivityInfo;
import androidx.test.filters.RequiresDevice;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.kavyrshin.weathernow.ui.MyStationsActivity;

@RunWith(AndroidJUnit4.class)
public class QueueMoxyStationsActivityTest {

    @Rule
    public ActivityTestRule<MyStationsActivity> myStationsActivityTestRule
            = new ActivityTestRule<MyStationsActivity>(MyStationsActivity.class, true, true);

    @Test
    @RequiresDevice
    public void testScreenOrientation() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            myStationsActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            Thread.sleep(1000);

            myStationsActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            Thread.sleep(1000);

        }

        Assert.assertNull(null);
    }
}
