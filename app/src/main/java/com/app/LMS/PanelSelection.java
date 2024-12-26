package com.app.LMS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.app.LMS.Employee.EmployeeLogin;
import com.app.LMS.Manager.ManagerLogin;

public class PanelSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_selection);

        Toolbar toolbar = findViewById(R.id.bar1);
        toolbar.setTitle("LMS");
        setSupportActionBar(toolbar);
    }

    public void onModeClick(View v){
        int id = v.getId();

        if(id == R.id.ManagerLogin){
            startActivity(new Intent(this, ManagerLogin.class));
        }
        else if(id == R.id.EmpLogin){
            startActivity(new Intent(this, EmployeeLogin.class));
        }
    }
}