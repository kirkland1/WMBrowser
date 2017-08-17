package com.walmart.tools.wmproductsapp;


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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProductListActivityTest {

    @Rule
    public ActivityTestRule<ProductListActivity> mActivityTestRule = new ActivityTestRule<>(ProductListActivity.class);

    @Test
    public void productListActivityTest() {
        ViewInteraction relativeLayout = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.product_list),
                                0),
                        0),
                        isDisplayed()));
        relativeLayout.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.thumbnail),
                        childAtPosition(
                                allOf(withId(R.id.linearLayoutTop),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                1),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.itemId), withText("107"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayoutTop),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("107")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.name), withText("Rose Cottage Girls' Hunter Green  Jacket Dress"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Rose Cottage Girls' Hunter Green  Jacket Dress")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.product_list),
                        withParent(withId(R.id.frameLayout)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.price), withText("Sale price: 16.99"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.product_detail_container),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Sale price: 16.99")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.msrp), withText("Reg price: 20.0"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.product_detail_container),
                                        0),
                                2),
                        isDisplayed()));
        textView4.check(matches(withText("Reg price: 20.0")));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.largeImageView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.product_detail_container),
                                        0),
                                3),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.product_detail), withText("&lt;ul&gt;&lt;li&gt;Solid cotton tee shirt&lt;/li&gt;&lt;li&gt;Graphic framed King Tiger logo&lt;/li&gt;&lt;li&gt;Crew neck&lt;/li&gt;&lt;li&gt;Short sleeves&lt;/li&gt;&lt;/ul&gt;"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.product_detail_container),
                                        0),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("&lt;ul&gt;&lt;li&gt;Solid cotton tee shirt&lt;/li&gt;&lt;li&gt;Graphic framed King Tiger logo&lt;/li&gt;&lt;li&gt;Crew neck&lt;/li&gt;&lt;li&gt;Short sleeves&lt;/li&gt;&lt;/ul&gt;")));

        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.detail_toolbar),
                                        childAtPosition(
                                                withId(R.id.toolbar_layout),
                                                0)),
                                0),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.detail_toolbar),
                                withParent(withId(R.id.toolbar_layout)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.product_list),
                        withParent(withId(R.id.frameLayout)),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(98, click()));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.detail_toolbar),
                                withParent(withId(R.id.toolbar_layout)))),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

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
