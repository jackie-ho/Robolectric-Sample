package com.adi.ho.jackie.loginscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboSharedPreferences;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.assertj.android.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by JHADI on 5/11/16.
 */

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class SecondActivityUnitTest {

    ActivityController<LoginActivity> controller;
    LoginActivity activity;
    TextView emailTextView;
    CheckBox checkbox2;
    CheckBox checkbox1;
    CheckBox checkbox3;

    @Before
    public void setup() {
        controller = Robolectric.buildActivity(LoginActivity.class);
    }

    private void createWithIntent(String email) {
        Intent intent = new Intent(RuntimeEnvironment.application, LoginActivity.class);
        intent.putExtra("email_account", email);
        activity = controller
                .withIntent(intent)
                .create()
                .start()
                .resume()
                .visible()
                .get();

        emailTextView = (TextView) activity.findViewById(R.id.email_accounttext);
        checkbox2 = (CheckBox) activity.findViewById(R.id.checkbox_2);
        checkbox1 = (CheckBox) activity.findViewById(R.id.checkbox_1);
        checkbox3 = (CheckBox) activity.findViewById(R.id.checkbox_3);
    }


    @Test
    public void accountNameShouldBeTheSameAsLogin() throws Exception {
        String email = "example@gmail.com";
        createWithIntent(email);
        assertThat(emailTextView).hasText(email);
    }

    @Test
    public void emptySharePreferencesOnStart() throws Exception{
        String email = "example@gmail.com";
        createWithIntent(email);
        RoboSharedPreferences preferences = (RoboSharedPreferences) RuntimeEnvironment.application
                .getSharedPreferences(email, Context.MODE_PRIVATE);
        assertThat(checkbox2).isNotChecked().isVisible().isNotGone();
        assertFalse(checkbox1.isChecked());
        assertFalse(checkbox3.isChecked());
    }

    @Test
    public void nonEmptySharedPreferences() throws Exception{
        String email = "ilikethisapp@gmail.com";
        RoboSharedPreferences preferences = (RoboSharedPreferences) RuntimeEnvironment.application
                .getSharedPreferences(email, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(String.valueOf(R.id.checkbox_1), true).commit();
        createWithIntent(email);

        assertThat(checkbox1).isChecked().isVisible();
        assertFalse(checkbox2.isChecked());
        assertFalse(checkbox3.isChecked());

    }

    @Test
    public void rotatePhoneRecreatingActivity() throws Exception{
        Bundle bundle = new Bundle();
        String email = "ilikethisapp@gmail.com";
        RoboSharedPreferences preferences = (RoboSharedPreferences) RuntimeEnvironment.application
                .getSharedPreferences(email, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(String.valueOf(R.id.checkbox_1), true).commit();
        createWithIntent(email);

        // Destroy the original activity
        controller
                .saveInstanceState(bundle)
                .pause()
                .stop()
                .destroy();

        // Bring up a new activity
        controller = Robolectric.buildActivity(LoginActivity.class)
                .create(bundle)
                .start()
                .resume()
                .visible();
        activity = controller.get();

        assertThat(checkbox1)
                .isChecked();
        assertTrue(checkbox2.isChecked());
        assertFalse(checkbox3.isChecked());

    }


}
