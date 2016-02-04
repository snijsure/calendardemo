package snijsure.com.sunrisedemo;

import java.util.ArrayList;

/*
 * This class holds information about a day.
 * Which include day, year, month and also list of
 * appointments for the day
 */
class DayInfo {
    private int day;
    private int month;
    private int year;
    private ArrayList<Appointment> appointments = new ArrayList<>();


    DayInfo(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public void addEvent(Appointment event){
        appointments.add(event);
    }
    /**
     * Get all the events on the day
     *
     * @return list of events
     */
    public ArrayList<Appointment> getAppointments(){
        return appointments;
    }

    public String toString() {
        return Integer.toString(day + 1) + "/" + Integer.toString(month + 1);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
