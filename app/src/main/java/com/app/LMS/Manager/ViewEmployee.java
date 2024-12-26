package com.app.LMS.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.app.LMS.Database.ELMSHelper;
import com.app.LMS.R;
import com.google.android.material.snackbar.Snackbar;

public class ViewEmployee extends AppCompatActivity {

    private ELMSHelper helper;
    private TableLayout tableLayout;
    private TableRow.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);

        Toolbar toolbar = findViewById(R.id.bar7);
        toolbar.setTitle("View Employees");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper      = new ELMSHelper(getApplicationContext());
        tableLayout = findViewById(R.id.view_employee_table);

        Cursor cursor = helper.viewEmployees();

        int row_id = 1;

        if (cursor.getCount() == 0) {
            Snackbar.make(tableLayout, "No Employees added", Snackbar.LENGTH_SHORT).show();
        }
        else {

            while (cursor.moveToNext()) {
                addEmployeeRow(cursor, row_id);
                row_id++;
            }
        }
    }

    public void addEmployeeRow(Cursor cursor, int row_id) {

        TextView name   = new TextView(getApplicationContext());
        TextView leaves = new TextView(getApplicationContext());
        TextView dept   = new TextView(getApplicationContext());
        TextView salary = new TextView(getApplicationContext());

        TableRow row = new TableRow(getApplicationContext());
        params = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f);

        designTextView(name, cursor.getString(1));
        designTextView(leaves, cursor.getString(10));
        designTextView(dept, cursor.getString(7));
        designTextView(salary, cursor.getString(8));

        row.setId(row_id);
        row.addView(name);
        row.addView(leaves);
        row.addView(dept);
        row.addView(salary);

        tableLayout.addView(row);
    }

    public void designTextView(TextView view, String data) {
        view.setText(data);
        view.setGravity(Gravity.START);
        view.setTextSize(15);
        view.setLayoutParams(params);
        view.setTypeface(ResourcesCompat.getFont(this, R.font.andika));
        view.setTextColor(getResources().getColor(R.color.purple_500));
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
        else if(id == R.id.m_evalLeaveRequest) {
            startActivity(new Intent(getApplicationContext(), EvaluateLeave.class));
        }
        else if (id == R.id.m_logout) {
            startActivity(new Intent(getApplicationContext(), ManagerLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        return super.onOptionsItemSelected(item);
    }
}