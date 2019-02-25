package denny.com.attendancetracker;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;

@RunWith(AndroidJUnit4.class)
public class InputBaseUrlActivityTest {

    @Rule
    public ActivityTestRule<InputBaseUrlActivity> categoryActivityActivityTestRule
            = new ActivityTestRule<>(InputBaseUrlActivity.class);

    @Test
    public void displayWholeUICheckUIWithEmptyInput(){
        Espresso.onView(ViewMatchers.withId(R.id.mainLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.cardView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.linearLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextBaseUrl)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.buttonCheckServerConnection)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.editTextBaseUrl)).perform(typeText(""));
        Espresso.onView(ViewMatchers.withId(R.id.buttonCheckServerConnection)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void displayWholeUICheckUIWithValidIp(){
        Espresso.onView(ViewMatchers.withId(R.id.mainLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.cardView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.linearLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextBaseUrl)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.buttonCheckServerConnection)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.editTextBaseUrl)).perform(typeText("192.168.100.26"));
        Espresso.onView(ViewMatchers.withId(R.id.buttonCheckServerConnection)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void displayWholeUICheckUIWithInvalidIp(){
        Espresso.onView(ViewMatchers.withId(R.id.mainLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.cardView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.linearLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextBaseUrl)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.buttonCheckServerConnection)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.editTextBaseUrl)).perform(typeText("192.168.10.26"));
        Espresso.onView(ViewMatchers.withId(R.id.buttonCheckServerConnection)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}