package com.app.LMS.Manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.app.LMS.Database.ELMSHelper;
import com.app.LMS.R;

public class LeaveRequestAdapter extends CursorAdapter {

    private ELMSHelper helper;

    public LeaveRequestAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_show_leave_requests, parent,false);
    }
    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {

        TextView name   = view.findViewById(R.id.show_name);
        TextView reason = view.findViewById(R.id.show_reason);
        TextView days   = view.findViewById(R.id.show_leaves);
        Button accept   = view.findViewById(R.id.accept);
        Button reject   = view.findViewById(R.id.reject);
        helper          = new ELMSHelper(context);

        String name_val   = cursor.getString(0);
        String reason_val = cursor.getString(1);
        String days_val   = cursor.getString(2);

        int init_leaves   = Integer.parseInt(cursor.getString(3));
        double salary     = Double.parseDouble(cursor.getString(4));

        final String emp_id   = cursor.getString(5);
        final String leave_id = cursor.getString(6);

        int req_leaves       = Integer.parseInt(days_val);
        double total_salary  = salary - (req_leaves * 200);
        int leaves_to_update = init_leaves + req_leaves;

        String reason_txt = reason_val + "\nSalary: " + total_salary + " PKR/-";

        name.setText(name_val);
        reason.setText(reason_txt);
        days.setText(days_val);

        designView(context, name);
        designView(context, reason);
        designView(context, days);

        final int pos = cursor.getPosition();

        accept.setOnClickListener(v -> {

            helper.updateLeaves(emp_id, leaves_to_update);
            helper.addToAcceptedLeaves(emp_id, leave_id, reason_val);
            helper.deleteLeaveRequest(leave_id);

            cursor.moveToPosition(pos);
            notifyDataSetChanged();
            Toast.makeText(context,"Updated successfully",Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, EvaluateLeave.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION));
        });

        reject.setOnClickListener(v -> {

            helper.addToRejectedLeaves(emp_id, leave_id, reason_val);
            helper.deleteLeaveRequest(leave_id);

            cursor.moveToPosition(pos);
            notifyDataSetChanged();
            Toast.makeText(context,"Leave request rejected",Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, EvaluateLeave.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION));
        });
    }

    public void designView(Context context, TextView view) {
        view.setTypeface(ResourcesCompat.getFont(context, R.font.andika));
        view.setTextSize(15);
    }
}
