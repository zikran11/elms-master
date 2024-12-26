package com.app.LMS.Manager;

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

public class ManagerPanel extends AppCompatActivity {

    private TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_panel);

        Toolbar toolbar = findViewById(R.id.bar4);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        msg = findViewById(R.id.m_welcome);

        TimeOfDay time = new TimeOfDay(getIntent().getStringExtra("managerName"));

        msg.setText(time.getManagerMessage());
        Snackbar.make(msg, time.getSnackMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manager_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_employee) {
            startActivity(new Intent(getApplicationContext(), AddEmployee.class));
        }
        else if(id == R.id.view_employee) {
            startActivity(new Intent(getApplicationContext(), ViewEmployee.class));
        }
        else if(id == R.id.m_evalLeaveRequest) {
            startActivity(new Intent(getApplicationContext(), EvaluateLeave.class));
        }
        else if (id == R.id.m_logout) {
            startActivity(new Intent(getApplicationContext(), ManagerLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        return super.onOptionsItemSelected(item);
    }
}