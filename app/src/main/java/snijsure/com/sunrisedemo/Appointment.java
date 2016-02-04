package snijsure.com.sunrisedemo;


import java.text.SimpleDateFormat;
import java.util.Locale;
import java.text.DateFormat;

/*
 * This class represents an appointment
 * with start and end times in milliseconds
 */
class Appointment {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String name;
    private String description;
    private String location;
    private final long startMs; // Start of an appointment in milli-seconds.
    private final long endMs; // End of an appointment in milli-seconds.

    public Appointment(String name, String description,
                       String location,
                       long startMs, long endMs) {
        this.startMs = startMs;
        this.endMs = endMs;
        this.location = location;
        this.name = name;
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
}
