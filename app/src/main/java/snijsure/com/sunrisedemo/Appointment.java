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


    private String title;
    private String description;
    private String location;
    private final long startMs; // Start of an appointment in milli-seconds.
    private final long endMs; // End of an appointment in milli-seconds.

    public Appointment(String title, String description,
                       String location,
                       long startMs, long endMs) {
        this.startMs = startMs;
        this.endMs = endMs;
        this.location = location;
        this.title = title;
        this.description = description;
    }

    public String getStartDate(String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return df.format(startMs);
    }

    public String getEndDate(String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return df.format(endMs);

    }
    public long durationInMinutes() {
        return (endMs-startMs)/60000;
    }

    public String getTitle() {
        return title;
    }

    public long getStartMs() {
        return startMs;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }


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
