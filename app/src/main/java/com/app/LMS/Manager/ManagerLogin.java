package com.app.LMS.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.LMS.Database.ELMSHelper;
import com.app.LMS.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class ManagerLogin extends AppCompatActivity {

    private ELMSHelper helper;
    private EditText user_name, pass;
    private Button login;
    private TextView signup;
    private TextInputLayout passwordLayout, nameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        Toolbar toolbar = findViewById(R.id.bar2);
        toolbar.setTitle("Manager");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user_name = findViewById(R.id.m_user);
        pass      = findViewById(R.id.m_pass);
        signup    = findViewById(R.id.addManager);
        login     = findViewById(R.id.m_login);
        helper    = new ELMSHelper(getApplicationContext());

        nameLayout     = findViewById(R.id.nameLayout);
        passwordLayout = findViewById(R.id.passwordLayout);

        nameLayout.requestFocus();
        signup.setOnClickListener(v -> {
            String userName = user_name.getText().toString().trim();
            String password = pass.getText().toString();

            if (user_name.length() == 0) {
                showError(nameLayout, "username cannot be empty");
                passwordLayout.setErrorEnabled(false);
            }
            else if(pass.length() == 0) {
                showError(passwordLayout, "password cannot be empty");
                nameLayout.setErrorEnabled(false);
            }
            else if (helper.checkManagerName(userName)) {
                showError(nameLayout, "username already taken");
                passwordLayout.setErrorEnabled(false);
            }
            else if(password.length() < 4) {
                showError(passwordLayout, "password must contain at least 4 characters");
                nameLayout.setErrorEnabled(false);
            }
            else if(!Pattern.compile("^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,15}$").matcher(password).matches()) {
                showError(passwordLayout, "password must contain at least 1 lowercase, 1 uppercase letter and a digit and a symbol");
                nameLayout.setErrorEnabled(false);
            }
            else {
                if(helper.addManager(userName, password)){
                    Toast.makeText(getApplicationContext(),"signed up successfully",Toast.LENGTH_SHORT).show();
                    nameLayout.setErrorEnabled(false);
                    passwordLayout.setErrorEnabled(false);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Error: Please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(v -> {
            String userName = user_name.getText().toString().trim();
            String password = pass.getText().toString();

            if(user_name.length() == 0) {
                showError(nameLayout, "username cannot be empty");
                passwordLayout.setErrorEnabled(false);
            }
            else if(pass.length() == 0) {
                showError(passwordLayout, "password cannot be empty");
                nameLayout.setErrorEnabled(false);
            }
            else if(helper.checkManagerLogin(userName,password)) {
                nameLayout.setErrorEnabled(false);
                passwordLayout.setErrorEnabled(false);
                Toast.makeText(ManagerLogin.this,"you are logged in successfully",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(), ManagerPanel.class);
                i.putExtra("managerName",userName);
                startActivity(i);
            }
            else if(!helper.checkManagerName(userName)){
                showError(nameLayout, "username is incorrect");
                passwordLayout.setErrorEnabled(false);
            }
            else {
                showError(passwordLayout, "password is incorrect");
                nameLayout.setErrorEnabled(false);
            }
        });

        pass.setOnClickListener(v -> {
            passwordLayout.setErrorEnabled(false);
        });
    }

    public void showError(TextInputLayout view, String errorMsg) {
        view.setError(errorMsg);
        view.requestFocus();
    }

    @Override
    protected void onRestart() {
        user_name.setText("");
        pass.setText("");
        nameLayout.requestFocus();
        super.onRestart();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}