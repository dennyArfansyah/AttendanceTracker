package denny.com.attendancetracker;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> signUpActivityTestRule
            = new ActivityTestRule<>(SignUpActivity.class);

    @Test
    public void displayWholeUI(){
        onView(withId(R.id.mainLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.toolbar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.linearLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.editTextName)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.editTextEmail)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.editTextPassword)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.linearLayoutDuration)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        onView(withId(R.id.buttonDob)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.spinnerDivision)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        onView(withId(R.id.buttonSignUp)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void signUpWithEmptyData(){
        onView(withId(R.id.editTextName)).perform(typeText(""));
        onView(withId(R.id.editTextEmail)).perform(typeText(""));
        onView(withId(R.id.editTextPassword)).perform(typeText(""));
        onView(withId(R.id.buttonSignUp)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void signUpWithValidData(){
        onView(withId(R.id.editTextName)).perform(typeText("I am Denny"));
        onView(withId(R.id.editTextEmail)).perform(typeText("iam@denny.com"));
        onView(withId(R.id.editTextPassword)).perform(typeText("iam"));
        onView(withId(R.id.spinnerDivision)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Marketing"))).perform(click());
        onView(withId(R.id.spinnerDivision)).check(ViewAssertions.matches(withSpinnerText(containsString("Marketing"))));
        onView(withId(R.id.buttonDob)).perform(click());
        onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2019, 3, 2));
        onView(withId(android.R.id.button1)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.buttonSignUp)).perform(click());
    }

    @Test
    public void signUpWithInvalidData(){
        onView(withId(R.id.editTextName)).perform(typeText("I am Denny"));
        onView(withId(R.id.editTextEmail)).perform(typeText("iamdenny.com"));
        onView(withId(R.id.editTextPassword)).perform(typeText("iam"));
        onView(withId(R.id.spinnerDivision)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Marketing"))).perform(click());
        onView(withId(R.id.spinnerDivision)).check(ViewAssertions.matches(withSpinnerText(containsString("Marketing"))));
        onView(withId(R.id.buttonDob)).perform(click());
        onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2019, 3, 2));
        onView(withId(android.R.id.button1)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.buttonSignUp)).perform(click());
    }


}