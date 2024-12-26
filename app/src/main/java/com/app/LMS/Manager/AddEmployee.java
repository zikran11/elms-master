package com.app.LMS.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.app.LMS.Database.ELMSHelper;
import com.app.LMS.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddEmployee extends AppCompatActivity {

    private EditText e_fname, e_lname, e_mail, e_phone, e_password, e_address, e_dept, e_salary, join_date, e_leaves;
    private RadioButton generic;
    private RadioGroup e_gender;
    private Button addEmp;
    private ELMSHelper helper;
    private DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        Toolbar toolbar = findViewById(R.id.bar6);
        toolbar.setTitle("Add Employee");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        e_fname    = findViewById(R.id.e_fname);
        e_lname    = findViewById(R.id.e_lname);
        e_mail     = findViewById(R.id.e_mail);
        e_phone    = findViewById(R.id.e_phone);
        e_password = findViewById(R.id.e_password);
        e_address  = findViewById(R.id.address);
        e_dept     = findViewById(R.id.e_dept);
        e_salary   = findViewById(R.id.e_salary);
        join_date  = findViewById(R.id.e_joindate);
        e_leaves   = findViewById(R.id.e_leaves);
        e_gender   = findViewById(R.id.e_gender);
        addEmp     = findViewById(R.id.add_emp);

        helper = new ELMSHelper(getApplicationContext());

        join_date.setOnClickListener(v -> {

            final Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            datePicker = new DatePickerDialog(AddEmployee.this,
                    (view, year1, monthOfYear, dayOfMonth) -> join_date.setText((monthOfYear+1) + "-" + dayOfMonth + "-" + year1), year, month, day);
            datePicker.show();
        });

        addEmp.setOnClickListener(v -> {

            String fullName = "", firstName = "", lastName = "", empMail = "", empPhone = "", empPass = "", empAddress = "", empDept = "", joinDate = "", empGender = "";
            int leaves = 0, id = 0;
            double salary = 0.0;

            if(e_fname.length() == 0 || e_mail.length() == 0 || e_password.length() == 0 || e_salary.length() == 0 || e_dept.length() == 0
                || e_lname.length() == 0 || e_phone.length() == 0 || e_leaves.length() == 0 || join_date.length() == 0){
                Snackbar.make(e_fname, "Please Enter all fields", Snackbar.LENGTH_SHORT).show();
            }
            else {
                firstName  = e_fname.getText().toString().trim();
                lastName   = e_lname.getText().toString().trim();
                empMail    = e_mail.getText().toString().trim();
                empPass    = e_password.getText().toString();
                empPhone   = e_phone.getText().toString();
                empAddress = e_address.getText().toString();
                empDept    = e_dept.getText().toString();
                joinDate   = join_date.getText().toString();
                leaves     = Integer.parseInt(e_leaves.getText().toString());
                salary     = Double.parseDouble(e_salary.getText().toString());
                generic    = findViewById(e_gender.getCheckedRadioButtonId());
                empGender  = generic.getText().toString();
                fullName   = firstName + " " + lastName;

                if(helper.checkEmployeeLogin(empMail.toLowerCase(), empPass).getCount() > 0) {
                    Snackbar.make(e_fname, "Cannot Add! Employee already added", Snackbar.LENGTH_SHORT).show();
                }
                else if(helper.addEmployee(fullName, empMail.toLowerCase(), empPass, empPhone, empAddress, empGender, empDept, salary, joinDate, leaves)){
                    Snackbar.make(e_fname,"Employee added successfully",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error: Please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manager_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        else if (id == R.id.view_employee) {
            startActivity(new Intent(getApplicationContext(), ViewEmployee.class));
        }
        else if (id == R.id.m_evalLeaveRequest) {
            startActivity(new Intent(getApplicationContext(), EvaluateLeave.class));
        }
        else if (id == R.id.m_logout) {
            startActivity(new Intent(getApplicationContext(), ManagerLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        }

        return super.onOptionsItemSelected(item);
    }
}