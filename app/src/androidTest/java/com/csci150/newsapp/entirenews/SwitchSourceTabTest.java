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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SwitchSourceTabTest {

    @Rule
    public ActivityTestRule<BottomNavActivity> mActivityTestRule = new ActivityTestRule<>(BottomNavActivity.class);

    @Test
    public void switchSourceTabTest() {

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction tabView = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction viewPager = onView(
                allOf(withId(R.id.container_tabs),
                        childAtPosition(
                                allOf(withId(R.id.coordinator_layout),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                1),
                        isDisplayed()));
        viewPager.perform(swipeLeft());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction tabView2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        1),
                        isDisplayed()));
        tabView2.perform(click());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction tabView3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        2),
                        isDisplayed()));
        tabView3.perform(click());

        ViewInteraction viewPager2 = onView(
                allOf(withId(R.id.container_tabs),
                        childAtPosition(
                                allOf(withId(R.id.coordinator_layout),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                1),
                        isDisplayed()));
        viewPager2.perform(swipeLeft());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction tabView4 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        1),
                        isDisplayed()));
        tabView4.perform(click());

        ViewInteraction viewPager3 = onView(
                allOf(withId(R.id.container_tabs),
                        childAtPosition(
                                allOf(withId(R.id.coordinator_layout),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                1),
                        isDisplayed()));
        viewPager3.perform(swipeRight());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction tabView5 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        0),
                        isDisplayed()));
        tabView5.perform(click());

        ViewInteraction viewPager4 = onView(
                allOf(withId(R.id.container_tabs),
                        childAtPosition(
                                allOf(withId(R.id.coordinator_layout),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                1),
                        isDisplayed()));
        viewPager4.perform(swipeRight());

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
