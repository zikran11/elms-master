package com.app.LMS.Employee;

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

public class LeaveStatus extends AppCompatActivity {

    private ListView acceptList, rejectList;
    private ELMSHelper helper;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_status);

        Toolbar toolbar = findViewById(R.id.bar9);
        toolbar.setTitle("Leave Status");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        acceptList = findViewById(R.id.list_of_acceptedRequests);
        rejectList = findViewById(R.id.list_of_rejectedRequests);
        helper     = new ELMSHelper(getApplicationContext());

        String emp_id = getIntent().getStringExtra("empID");

        if (emp_id != null) {

            Cursor cursor1 = helper.checkIntoAcceptedLeaves(emp_id);
            Cursor cursor2 = helper.checkIntoRejectedLeaves(emp_id);

            if (cursor1.getCount() == 0 && cursor2.getCount() == 0) {
                Snackbar.make(acceptList, "No Requests or You requests are under waiting process. Kindly wait for Approval!", Snackbar.LENGTH_SHORT).show();
            }
            else if (cursor1.getCount() == 0) {
                Snackbar.make(acceptList, "No Accepted requests found", Snackbar.LENGTH_SHORT).show();
            }
            else if (cursor2.getCount() == 0) {
                Snackbar.make(rejectList, "No Rejected requests found", Snackbar.LENGTH_SHORT).show();
            }
            LeaveStatusAdapter adapter1 = new LeaveStatusAdapter(this, cursor1, 0);
            adapter1.setStatus("accept");
            acceptList.setAdapter(adapter1);

            LeaveStatusAdapter adapter2 = new LeaveStatusAdapter(this, cursor2, 0);
            adapter2.setStatus("reject");
            rejectList.setAdapter(adapter2);
        }
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
        else if (id == R.id.leave_req) {
            intent = new Intent(getApplicationContext(), RequestLeave.class);
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