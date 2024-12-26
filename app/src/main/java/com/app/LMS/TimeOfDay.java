package com.app.LMS;

import java.util.Calendar;

public class TimeOfDay {

    private String name, time;
    private Calendar c;

    public TimeOfDay(String name) {
        this.name = name;
        c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >=0 && timeOfDay < 12)
            time = "Morning";
        else if(timeOfDay >= 12 && timeOfDay < 16)
            time = "Afternoon";
        else if(timeOfDay >= 16 && timeOfDay < 21)
            time = "Evening";
        else
            time = "Night";
    }

    public String getEmployeeMessage() {
        return "Good " + time + " " + name + ".\nPlease navigate to menu for further options...\nPer Leave Deduction: 200 PKR/-";
    }
    public String getManagerMessage() {
        return "Good " + time + " " + name + ".\nPlease navigate to menu for further options...";
    }
    public String getSnackMessage() {
        return "Welcome " + name + "...!";
    }
}
