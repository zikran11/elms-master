package com.app.LMS.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.app.LMS.Database.ELMSHelper;
import com.app.LMS.R;
import com.google.android.material.snackbar.Snackbar;

public class EvaluateLeave extends AppCompatActivity {

    private ListView listView;
    private ELMSHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_leave);

        Toolbar toolbar = findViewById(R.id.bar8);
        toolbar.setTitle("Leaves");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.list_of_leaves_requests);
        helper   = new ELMSHelper(getApplicationContext());

        Cursor cursor = helper.getLeaveRequests();

        if (cursor.getCount() == 0) {
            Snackbar.make(listView, "No leave requests found", Snackbar.LENGTH_SHORT).show();
        }
        else {
            LeaveRequestAdapter adapter = new LeaveRequestAdapter(getApplicationContext(), cursor, 0);
            listView.setAdapter(adapter);
        }
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
        else if (id == R.id.add_employee) {
            startActivity(new Intent(getApplicationContext(), AddEmployee.class));
        }
        else if(id == R.id.view_employee) {
            startActivity(new Intent(getApplicationContext(), ViewEmployee.class));
        }
        else if (id == R.id.m_logout) {
            startActivity(new Intent(getApplicationContext(), ManagerLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        return super.onOptionsItemSelected(item);
    }
}