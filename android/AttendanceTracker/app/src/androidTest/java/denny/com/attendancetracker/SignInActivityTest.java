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
public class SignInActivityTest {

    @Rule
    public ActivityTestRule<SignInActivity> categoryActivityActivityTestRule
            = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void displayWholeUI(){
        Espresso.onView(ViewMatchers.withId(R.id.mainLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.toolbar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.cardView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.linearLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextEmail)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.buttonSignIn)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void SignInWithEmptyAccount(){
        Espresso.onView(ViewMatchers.withId(R.id.editTextEmail)).perform(typeText(""));
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword)).perform(typeText(""));
        Espresso.onView(ViewMatchers.withId(R.id.buttonSignIn)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void SignInWithValidAccount(){
        Espresso.onView(ViewMatchers.withId(R.id.editTextEmail)).perform(typeText("denny@deni.com"));
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword)).perform(typeText("asd"));
        Espresso.onView(ViewMatchers.withId(R.id.buttonSignIn)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void SignInWithInvalidAccount(){
        Espresso.onView(ViewMatchers.withId(R.id.editTextEmail)).perform(typeText("de@deni.com"));
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword)).perform(typeText("asd"));
        Espresso.onView(ViewMatchers.withId(R.id.buttonSignIn)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}