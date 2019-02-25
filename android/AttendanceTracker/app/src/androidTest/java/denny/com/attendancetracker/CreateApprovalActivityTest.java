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
public class CreateApprovalActivityTest {

    @Rule
    public ActivityTestRule<CreateApprovalActivity> createApprovalActivityTestRule
            = new ActivityTestRule<>(CreateApprovalActivity.class);

    @Test
    public void displayWholeUI(){
        Espresso.onView(ViewMatchers.withId(R.id.mainLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.toolbar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.textLeaveType)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.spinnerLeaveType)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.textLeaveReason)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextLeaveReason)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.textLeaveDuration)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.linearLayoutDuration)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.buttonSinceDuration)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.buttonUntilDuration)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.buttonSubmit)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createApprovalWithEmptyData(){
        onView(withId(R.id.editTextLeaveReason)).perform(typeText(""));
        onView(withId(R.id.buttonSubmit)).perform(click());
    }


    @Test
    public void signUpWithValidData(){
        onView(withId(R.id.spinnerLeaveType)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Annual"))).perform(click());
        onView(withId(R.id.spinnerLeaveType)).check(ViewAssertions.matches(withSpinnerText(containsString("Annual"))));

        onView(withId(R.id.editTextLeaveReason)).perform(typeText("I just wanna leaves"));

        onView(withId(R.id.buttonSinceDuration)).perform(click());
        onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2019, 3, 2));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.buttonUntilDuration)).perform(click());
        onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2019, 3, 5));
        onView(withId(android.R.id.button1)).perform(click());

        Espresso.onView(ViewMatchers.withId(R.id.buttonSubmit)).perform(click());
    }

}