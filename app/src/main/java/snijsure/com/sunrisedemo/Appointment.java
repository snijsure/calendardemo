package snijsure.com.sunrisedemo;


import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/*
 * This class represents an appointment
 * with start and end times in milliseconds.
 */
class Appointment implements Comparable<Appointment> {

    private int id;
    private String title;
    private String description;
    private String location;
    private  long startMs; // Start of an appointment in milli-seconds.
    private  long endMs; // End of an appointment in milli-seconds.

    public Appointment(String title, String description,
                       String location,
                       long startMs, long endMs) {
        this.id = 0;
        this.startMs = startMs;
        this.endMs = endMs;
        this.location = location;
        this.title = title;
        this.description = description;
    }

    public Appointment() {
        this.id = 0;
        this.startMs = 0;
        this.endMs = 0;
        this.location = "";
        this.title = "";
        this.description = "";
    }

    public String getStartDate(String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return df.format(startMs);
    }
    public void setStartDate(long inputDate) {
        startMs = inputDate;
    }

    public String getEndDate(String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return df.format(endMs);
    }
    public void setEndDate(long inputDate) {
        endMs = inputDate;
    }

    public long durationInMinutes() {
        return (endMs-startMs)/60000;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String in) { title = in; }

    public long getStartMs() {
        return startMs;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String in) { description = in; }

    public String getLocation() {
        return location;
    }
    public void setLocation(String in ) { location = in; }

    public int compareTo(@NonNull Appointment r) {
        long s1 = r.getStartMs();
        if (s1 < startMs)
            return -1;
        else if (s1 > startMs)
            return 1;
        else
            return 0;
    }
}
