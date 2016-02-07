package snijsure.com.sunrisedemo;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SunriseDemoTest extends TestCase {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    void doUiActions() {

        for ( int i = 0 ; i < 4; i++ ) {
            onView(withId(R.id.nextButton)).perform(click());
        }
        for ( int i = 0; i < 8; i++ ) {
            onView(withId(R.id.prevButton)).perform(click());
        }
    }

    @Test
    public void testBasicNavigation() {
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());

        doUiActions();

        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());

        doUiActions();

    }

}
