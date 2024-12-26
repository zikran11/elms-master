package com.app.LMS.Employee;

import android.content.Context;
import android.database.Cursor;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.app.LMS.R;

public class LeaveStatusAdapter extends CursorAdapter {

    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public LeaveStatusAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_show_leave_status, parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView leave_id   = view.findViewById(R.id.id_of_leave);
        TextView reason     = view.findViewById(R.id.res_of_reason);
        TextView emp_salary = view.findViewById(R.id.salary_of_emp);

        leave_id.setText(cursor.getString(0));
        reason.setText(cursor.getString(1));
        emp_salary.setText(cursor.getString(2));

        if (status.equals("accept")) {
            designAccept(leave_id, context);
            designAccept(reason, context);
            designAccept(emp_salary, context);
        }
        else if (status.equals("reject")) {
            designReject(leave_id, context);
            designReject(reason, context);
            designReject(emp_salary, context);
        }
    }

    public void designAccept(TextView view, Context context) {
        view.setTextColor(context.getResources().getColor(R.color.accept));
        view.setGravity(Gravity.CENTER);
    }

    public void designReject(TextView view, Context context) {
        view.setTextColor(context.getResources().getColor(R.color.reject));
        view.setGravity(Gravity.CENTER);
    }
}
