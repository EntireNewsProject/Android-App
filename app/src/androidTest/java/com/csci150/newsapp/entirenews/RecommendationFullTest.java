package com.csci150.newsapp.entirenews;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecommendationFullTest {

    @Rule
    public ActivityTestRule<BottomNavActivity> mActivityTestRule = new ActivityTestRule<>(BottomNavActivity.class);

    @Test
    public void recommendationFullTest() {

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_recommended),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.title), withText("Log In/Register"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        textView.perform(click());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction editText = onView(
                allOf(withId(R.id.etUsername),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        editText.perform(replaceText("thunder"), closeSoftKeyboard());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.etPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        editText2.perform(replaceText("123456"), closeSoftKeyboard());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction button = onView(
                allOf(withId(R.id.btLogIn), withText("Log In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.coordinator_layout),
                                        0),
                                3),
                        isDisplayed()));
        button.perform(click());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_recommended),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction viewGroup = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.list),
                                childAtPosition(
                                        withId(R.id.swipe_layout),
                                        0)),
                        0),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction viewGroup2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.list),
                                childAtPosition(
                                        withId(R.id.swipe_layout),
                                        0)),
                        1),
                        isDisplayed()));
        viewGroup2.check(matches(isDisplayed()));

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction viewGroup3 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.list),
                                childAtPosition(
                                        withId(R.id.swipe_layout),
                                        0)),
                        2),
                        isDisplayed()));
        viewGroup3.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
