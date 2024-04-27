package com.example.goforth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringEndsWith.endsWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class JoshSystemTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Checks to see that troop training is working and adding troops to the DB correctly
     */
    @Test
    public void testAddTroop() {
        // Launch TroopManagementActivity using ActivityScenario
        ActivityScenario<TroopManagementActivity> troopManagementScenario = ActivityScenario.launch(TroopManagementActivity.class);

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        // Access the initial state of TroopManagementActivity using the scenario
        final int[] initialTroopCount = new int[1];
        troopManagementScenario.onActivity(activity -> {
            // Assuming you have a method to get the current troop count
            initialTroopCount[0] = activity.archersCount;
        });

        // Perform UI actions in TroopManagementActivity
        onView(withId(R.id.archersCheckbox)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.trainOneButton)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.confirmTrainingButton)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}

        // Calculate the expected final troop count based on the initial state
        int expectedFinalTroopCount = initialTroopCount[0] + 1;

        // Validate the final troop count
        troopManagementScenario.onActivity(activity -> {
            int currentTroopCount = activity.archersCount; // Verify current troop count after interactions
            assert currentTroopCount == expectedFinalTroopCount; // Assert the expected and current troop count are equal
        });
    }

    /**
     * Checks to see that we are calculating the troop training time correctly
     */
    @Test
    public void testTroopTrainingTime() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.userID = 1;
        });

        onView(withId(R.id.troopManagementButton)).perform(click());

        String time = "00:09";

        onView(withId(R.id.archersCheckbox)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.trainTenButton)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.confirmTrainingButton)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.trainingTimeValue)).check(matches(withText(endsWith(time))));
    }

    /**
     * Checks to see that fights on the overworld work correctly
     */
    @Test
    public void testFight() {
        ActivityScenario<OverworldActivity> overworldScenario = ActivityScenario.launch(OverworldActivity.class);

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        final int[] initialPower = new int[1];
        overworldScenario.onActivity(activity -> {
            initialPower[0] = activity.power;
        });

        onView(withId(R.id.arrow_right)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.arrow_down)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.arrow_down)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.arrow_down)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.fightButton)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}

        // Calculate the expected final troop count based on the initial state
        int expectedFinalPower = initialPower[0] - 15;

        // Validate the final troop count
        overworldScenario.onActivity(activity -> {
            int currentPower = activity.power; // Verify current troop count after interactions
            assert currentPower == expectedFinalPower; // Assert the expected and current troop count are equal
        });
    }

    /**
     * Checks to see that moving the base works correctly
     */
    @Test
    public void testMoveBase() {
        ActivityScenario<OverworldActivity> overworldScenario = ActivityScenario.launch(OverworldActivity.class);

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        final int[] initialWood = new int[1];
        final int[] initialStone = new int[1];
        overworldScenario.onActivity(activity -> {
            initialWood[0] = activity.wood;
            initialStone[0] = activity.stone;
        });

        onView(withId(R.id.arrow_right)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.arrow_right)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.moveButton)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

        onView(withId(R.id.arrow_left)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.arrow_left)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.moveButton)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}


        int expectedFinalWood = initialWood[0] - 10000;
        int expectedFinalStone = initialStone[0] - 10000;

        // Validate the final troop count
        overworldScenario.onActivity(activity -> {
            int currentWood = activity.wood;
            int currentStone = activity.stone;

            assert currentWood == expectedFinalWood && currentStone == expectedFinalStone;
        });
    }
}
