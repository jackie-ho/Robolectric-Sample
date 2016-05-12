package com.adi.ho.jackie.loginscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText passwordEdit;
    private EditText emailEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passwordEdit = (EditText) findViewById(R.id.password_edittext);
        emailEdit = (EditText) findViewById(R.id.email_edittext);

    }


    public void loginClick(View view) {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);

        boolean startActivityFlag = true;

        if (emailEdit.getText().toString().trim().isEmpty()) {
            emailEdit.setError("Enter a email.");
            startActivityFlag = false;
        }
        if (passwordEdit.getText().toString().isEmpty()) {
            passwordEdit.setError("Enter a password.");
            return;
        }

        if (!isEmailValid(emailEdit.getText().toString()) && startActivityFlag) {
            emailEdit.setError("Enter a valid email.");
            startActivityFlag = false;
        }

        if (startActivityFlag) {
            loginIntent.putExtra("email_account", emailEdit.getText().toString());
            emailEdit.setText("");
            passwordEdit.setText("");
            startActivity(loginIntent);
        }
    }


    public static boolean isEmailValid(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {

            isValid = true;

        }

        return isValid;

    }
}

