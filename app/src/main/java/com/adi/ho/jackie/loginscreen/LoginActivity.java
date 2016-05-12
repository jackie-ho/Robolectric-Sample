package com.adi.ho.jackie.loginscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class LoginActivity extends AppCompatActivity {

    TextView emailTextView;
    String email = "email";
    Toolbar toolbar;
    List<Integer> checkBoxList;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailTextView = (TextView)findViewById(R.id.email_accounttext);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        setSupportActionBar(toolbar);
        email = getIntent().getStringExtra("email_account");
        checkBoxList = new ArrayList<>();
        checkBoxList.add(R.id.checkbox_1);
        checkBoxList.add(R.id.checkbox_2);
        checkBoxList.add(R.id.checkbox_3);

    }

    @Override
    protected void onStart() {
        super.onStart();
        emailTextView.setText(email);
        checkIfOptionsAreChecked();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(LoginActivity.this, "Settings Launched", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_logout:
                onBackPressed();
                break;
        }
        return true;
    }

    public void checkIfOptionsAreChecked(){
        SharedPreferences sharePref = getSharedPreferences(email, Context.MODE_PRIVATE);
       for (Integer checkboxId : checkBoxList){
           if (sharePref.getBoolean(String.valueOf(checkboxId), false)){
               CheckBox checkbox = (CheckBox)findViewById(checkboxId);
               checkbox.setChecked(true);
           }
       }
    }

    public void saveOptionToSharedPreferences(View view){
        CheckBox checkbox = (CheckBox)view;
        SharedPreferences sharedPreferences = getSharedPreferences(email, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!checkbox.isChecked()){
            checkbox.setChecked(false);
            editor.putBoolean(String.valueOf(view.getId()), false);
            editor.commit();
        } else {
            checkbox.setChecked(true);
            editor.putBoolean(String.valueOf(view.getId()), true);
            editor.commit();
        }
    }
}
