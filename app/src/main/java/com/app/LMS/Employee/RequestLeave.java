package com.app.LMS.Employee;

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
import android.widget.Toast;

import com.app.LMS.Database.ELMSHelper;
import com.app.LMS.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class RequestLeave extends AppCompatActivity {

    private EditText num_leaves, reason, from_date, to_date;
    private Button submit;
    private ELMSHelper helper;
    private Intent intent;
    private DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_leave);

        Toolbar toolbar = findViewById(R.id.bar10);
        toolbar.setTitle("Request Leave");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        num_leaves = findViewById(R.id.no_of_leaves);
        reason     = findViewById(R.id.reason);
        from_date  = findViewById(R.id.fromDate);
        to_date    = findViewById(R.id.toDate);
        helper     = new ELMSHelper(getApplicationContext());
        submit     = findViewById(R.id.submit_request);

        final Calendar cal = Calendar.getInstance();
        int day   = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year  = cal.get(Calendar.YEAR);

        from_date.setOnClickListener(v -> {
            datePicker = new DatePickerDialog(RequestLeave.this,
                    (view, year1, monthOfYear, dayOfMonth) -> from_date.setText((monthOfYear+1) + "-" + dayOfMonth + "-" + year1), year, month, day);
            datePicker.show();
        });

        to_date.setOnClickListener(v -> {
            datePicker = new DatePickerDialog(RequestLeave.this,
                    (view, year1, monthOfYear, dayOfMonth) -> to_date.setText((monthOfYear+1) + "-" + dayOfMonth + "-" + year1), year, month, day);
            datePicker.show();
        });

        submit.setOnClickListener(v -> {
            if(submit.isEnabled()){

                if (num_leaves.length() == 0 || reason.length() == 0 || from_date.length() == 0 || to_date.length() == 0) {
                    Snackbar.make(from_date, "Please Enter all fields", Snackbar.LENGTH_SHORT).show();
                    submit.setEnabled(true);
                }
                else{

                    String id = getIntent().getStringExtra("empID");
                    String reasonOfLeave = reason.getText().toString();
                    int numOfLeaves = Integer.parseInt(num_leaves.getText().toString());
                    String fDate = from_date.getText().toString();
                    String tDate = to_date.getText().toString();

                    if(helper.addLeave(Integer.parseInt(id), reasonOfLeave, numOfLeaves, fDate, tDate)) {
                        Snackbar.make(from_date, "Request submitted successfully", Snackbar.LENGTH_SHORT).show();
                        submit.setEnabled(false);
                    }
                    else {
                        Snackbar.make(from_date, "Cannot submit request at the moment, try again!", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        else if(id == R.id.leave_status) {
            intent = new Intent(getApplicationContext(), LeaveStatus.class);
            intent.putExtra("empID", getIntent().getStringExtra("empID"));
            startActivity(intent);
        }
        else if (id == R.id.e_logout) {
            startActivity(new Intent(getApplicationContext(), EmployeeLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        return super.onOptionsItemSelected(item);
    }
}