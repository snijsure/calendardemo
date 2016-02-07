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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SunriseDemoTest extends TestCase {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    void doUiActions() {

        for ( int i = 0 ; i < 2; i++ ) {
            onView(withId(R.id.nextButton)).perform(click());
        }
        for ( int i = 0; i < 2; i++ ) {
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
    /*
     * Use this as resource:
     * https://github.com/vgrec/EspressoExamples
     * https://github.com/vgrec/EspressoExamples/tree/master/app/src/androidTest/java/com/vgrec/espressoexamples
     */
    @Test
    public void testAddNewEventOkAction() {

        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
        onView(withId(R.id.add_new_appointment)).perform(click());

        // Check that dialog is displayed.

        onView(withText(R.string.AddEvent_Label)).check(matches(isDisplayed()));
        // android.R.id.button1 = positive button
        onView(withId(android.R.id.button1)).perform(click());
    }
    @Test
    public void testAddNewEventCancelAction() {

        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
        onView(withId(R.id.add_new_appointment)).perform(click());

        onView(withText(R.string.AddEvent_Label)).check(matches(isDisplayed()));

        // android.R.id.button2 = negative button
        onView(withId(android.R.id.button2)).perform(click());
    }


    @Test
    public void testDateTimePickers() {

        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
        onView(withId(R.id.add_new_appointment)).perform(click());

        onView(withId(R.id.to_date)).perform(click());

        // TODO: How do you drive the date time picker.
        //onView(withClassName(Matchers.equalTo(AddEventFragment.class.getName()))).perform(updateToTime(12,30));
        onView(withText("Done")).perform(click());

        onView(withId(R.id.to_time)).perform(click());
        onView(withText("Done")).perform(click());


        /* TODO: Investigate how to "pause" things with Espresso
        http://blog.sqisland.com/2015/06/espresso-elapsed-time.html
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(15);
        Espresso.registerIdlingResources(idlingResource);

        IdlingPolicies.setMasterPolicyTimeout(60, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(30, TimeUnit.MILLISECONDS);
        SystemClock.sleep(15000);
        */

        onView(withId(R.id.from_date)).perform(click());
        onView(withText("Done")).perform(click());

        onView(withId(R.id.from_time)).perform(click());
        onView(withText("Done")).perform(click());
    }

}
