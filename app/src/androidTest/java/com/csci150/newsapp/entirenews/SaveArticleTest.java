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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SaveArticleTest {

    @Rule
    public ActivityTestRule<BottomNavActivity> mActivityTestRule = new ActivityTestRule<>(BottomNavActivity.class);

    @Test
    public void saveArticleTest() {

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.ib_save), withContentDescription("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list),
                                        1),
                                4),
                        isDisplayed()));
        imageButton.perform(click());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.ib_save), withContentDescription("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list),
                                        0),
                                4),
                        isDisplayed()));
        imageButton2.perform(click());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_saved),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        ViewInteraction viewGroup = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.list),
                                childAtPosition(
                                        withId(R.id.coordinator_layout),
                                        1)),
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
                                        withId(R.id.coordinator_layout),
                                        1)),
                        1),
                        isDisplayed()));
        viewGroup2.check(matches(isDisplayed()));

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
