package snijsure.com.sunrisedemo;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.EditText;

import junit.framework.TestCase;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

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
    public void testActivityRecreateTest() {

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());
        onView(withId(R.id.nextButton)).perform(click()); 
        onView(withId(R.id.nextButton)).perform(click()); 
        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
        // Ref: http://qathread.blogspot.com/2014/01/discovering-espresso-for-android.html
        onView(withId(R.id.monthLabel)).check(
                matches(withText(containsString("2016"))));
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

    @Test
    public void testAppointmentView() {
        onView(withId(R.id.appointment_recycler_view)).perform(swipeDown());
        onView(withId(R.id.appointment_recycler_view)).perform(swipeDown());
        onView(withId(R.id.appointment_recycler_view)).perform(swipeUp());
        onView(withId(R.id.appointment_recycler_view)).perform(swipeUp());
    }


    @Test
    public void testCalendarView() {
        onView(withId(R.id.calendar_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withId(R.id.calendar_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        // Check that Toast is displayed?
        //onView(withText("6,Februray")).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));


        onView(withId(R.id.calendar_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(13, click()));
        onView(withId(R.id.calendar_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(20, click()));
        onView(withId(R.id.calendar_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(27, click()));

    }


    private static Matcher<View> withError(final String expected) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof EditText)) {
                    return false;
                }
                EditText editText = (EditText) view;
                return editText.getError().toString().equals(expected);
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }


}
