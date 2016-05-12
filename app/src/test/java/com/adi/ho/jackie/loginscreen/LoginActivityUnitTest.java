package com.adi.ho.jackie.loginscreen;

import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.assertj.android.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by JHADI on 5/10/16.
 */
@Config (constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class LoginActivityUnitTest {

    MainActivity activity;
    EditText passwordEditText;
    EditText emailEditText;
    Button loginButton;

    @Before
    public void setup(){
        activity = Robolectric.setupActivity(MainActivity.class);
        passwordEditText = (EditText)activity.findViewById(R.id.password_edittext);
        emailEditText = (EditText)activity.findViewById(R.id.email_edittext);
        loginButton = (Button)activity.findViewById(R.id.login_button);
    }

    @Test
    public void loginSuccessful() throws Exception{
        String email = "testemail@google.com";
        String password = "PassW0rd";

        emailEditText.setText(email);
        passwordEditText.setText(password);


        Intent expectedIntent = new Intent(activity, LoginActivity.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        loginButton.performClick();
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }


    @Test
    public void loginFailed() throws Exception{
        String email = "testemail@googlecom";
        String password = "PassWord632";

        emailEditText.setText(email);
        passwordEditText.setText(password);
        loginButton.performClick();
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);


        assertThat(shadowActivity.getNextStartedActivity()).isNull();
        assertThat(emailEditText).hasError();
        assertEquals("Enter a valid email.", emailEditText.getError());
    }

    @Test
    public void noLoginFailed() throws Exception{
        String email = "   ";
        String password = " ";

        emailEditText.setText(email);
        passwordEditText.setText(password);
        loginButton.performClick();
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);

        assertThat(shadowActivity.getNextStartedActivity()).isNull();
        assertThat(emailEditText).hasError();
        assertEquals("Enter a email.", emailEditText.getError());
        assertEquals(" ", passwordEditText.getText().toString());

    }
}
