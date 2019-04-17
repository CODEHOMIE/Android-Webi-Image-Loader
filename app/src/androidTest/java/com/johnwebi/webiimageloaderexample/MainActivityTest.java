package com.johnwebi.webiimageloaderexample;

import android.support.test.runner.AndroidJUnit4;
import com.johnwebi.webiimageloaderexample.Activities.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    @Test
    public void shouldBeAbleToLaunchMainScreen() {
        //onView()

    }
}
