package com.app.LMS.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.LMS.R;
import com.app.LMS.TimeOfDay;
import com.google.android.material.snackbar.Snackbar;

public class EmployeePanel extends AppCompatActivity {

    private TextView msg;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_panel);

        Toolbar toolbar = findViewById(R.id.bar5);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        msg = findViewById(R.id.e_welcome);

        TimeOfDay time = new TimeOfDay(getIntent().getStringExtra("empName"));

        msg.setText(time.getEmployeeMessage());
        Snackbar.make(msg, time.getSnackMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.leave_req) {
            intent = new Intent(getApplicationContext(), RequestLeave.class);
            intent.putExtra("empID", getIntent().getStringExtra("empID"));
            startActivity(intent);
        }
        else if(id == R.id.leave_status) {
            intent = new Intent(getApplicationContext(), LeaveStatus.class);
            intent.putExtra("empID", getIntent().getStringExtra("empID"));
            startActivity(intent);
        }
        else if (id == R.id.e_logout) {
            intent = new Intent(getApplicationContext(), EmployeeLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}