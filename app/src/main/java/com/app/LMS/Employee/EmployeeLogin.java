package com.app.LMS.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;

import com.app.LMS.Database.ELMSHelper;
import com.app.LMS.R;
import com.google.android.material.textfield.TextInputLayout;

public class EmployeeLogin extends AppCompatActivity {

    private EditText user_name, pass;
    private Button login;
    private ELMSHelper helper;
    private Intent i;
    private TextInputLayout nameLayout, passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);

        Toolbar toolbar = findViewById(R.id.bar3);
        toolbar.setTitle("Employee");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user_name = findViewById(R.id.e_user);
        pass      = findViewById(R.id.e_pass);
        login     = findViewById(R.id.e_login);
        helper    = new ELMSHelper(getApplicationContext());
        nameLayout = findViewById(R.id.e_nameLayout);
        passwordLayout = findViewById(R.id.e_passLayout);

        nameLayout.requestFocus();
        login.setOnClickListener(v -> {

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
            else {
                Cursor cursor= helper.checkEmployeeLogin(userName,password);

                i= new Intent(getApplicationContext(),EmployeePanel.class);
                while (cursor.moveToNext()){
                    i.putExtra("empID", cursor.getString(0));
                    i.putExtra("empName", cursor.getString(1));
                }
                if (cursor.getCount() > 0) {
                    Toast.makeText(getApplicationContext(), "you are logged in successfully", Toast.LENGTH_SHORT).show();
                    nameLayout.setErrorEnabled(false);
                    passwordLayout.setErrorEnabled(false);
                    startActivity(i);
                }
                else if (!helper.checkEmployeeName(userName)){
                    showError(nameLayout, "user name is incorrect");
                    passwordLayout.setErrorEnabled(false);
                }
                else {
                    showError(passwordLayout, "password is incorrect");
                    nameLayout.setErrorEnabled(false);
                }
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