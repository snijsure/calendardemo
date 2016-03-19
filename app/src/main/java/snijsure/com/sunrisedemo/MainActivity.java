package snijsure.com.sunrisedemo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mCalView;
    private RecyclerView mEventView;
    private TextView monthLabel;

    private CalendarInfoAdapter mCalendarAdapter;
    private ApptInfoAdapter mAppointmentsAdapter;
    private ArrayList<DayInfo> mDayInfoList;
    private ArrayList<Appointment> mAppointments;

    private final int NUM_COLUMNS = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sunrise_main);

        final FrameLayout frame1 = (FrameLayout) findViewById(R.id.frame1);
        final FrameLayout frame2 = (FrameLayout) findViewById(R.id.frame2);

        final GridLayoutManager gridLayoutMgr = new GridLayoutManager(this, NUM_COLUMNS);
        final LinearLayoutManager eventLayoutMgr = new LinearLayoutManager(this);

        // Note well:
        // RecyclerView are very "delicate" one can not create them witout proper layout
        // manager and hence they are not specified in layout xml file but are crafted by
        // hand here.
        // mCalView is grid  view that represents current month
        // mEventView is list of appointments.
        mCalView = new RecyclerView(this);
        mEventView = new RecyclerView(this);

        // Set the ids explicitly so espresso can find these views.
        //
        mCalView.setId(R.id.calendar_recycler_view);
        mEventView.setId(R.id.appointment_recycler_view);



        mCalView.setLayoutManager(gridLayoutMgr);
        mEventView.setLayoutManager(eventLayoutMgr);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        frame1.addView(mCalView, params);
        frame2.addView(mEventView, params);

        FloatingActionButton actionButton = (FloatingActionButton)findViewById(R.id.add_new_appointment);
        actionButton.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_input_add,
                null));
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEventFragment dialogFragment = new AddEventFragment();
                dialogFragment.show(getFragmentManager(), "AddEventFragment");
            }
        });

        mDayInfoList = new ArrayList<DayInfo>(42);

        for (int i = 0; i < 42; i++) {
            DayInfo d = new DayInfo(0, 0, 0);
            mDayInfoList.add(d);
        }

        mAppointments = new ArrayList<>();

        Calendar cal = Calendar.getInstance();


        if ( savedInstanceState != null ) {
            int month, year;
            month = savedInstanceState.getInt("month");
            year = savedInstanceState.getInt("year");
            mCalendarAdapter = new CalendarInfoAdapter(mDayInfoList, month,year);
        }
        else {
            mCalendarAdapter = new CalendarInfoAdapter(mDayInfoList,
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.YEAR));
        }

        //genTestAppointments(120);
        readCalendarEvent(this);
        mAppointmentsAdapter = new ApptInfoAdapter(mAppointments);

        mCalendarAdapter.setHasStableIds(true);
        mCalView.setAdapter(mCalendarAdapter);
        mEventView.setAdapter(mAppointmentsAdapter);

        monthLabel = (TextView) findViewById(R.id.monthLabel);
        monthLabel.setText(mCalendarAdapter.getCurrentMonthAndYear());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sunrise_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //int month = savedInstanceState.getInt("month");
        //int year = savedInstanceState.getInt("year");
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("month", mCalendarAdapter.getMonth());
        savedInstanceState.putInt("year",mCalendarAdapter.getYear());
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onNextMonth(@SuppressWarnings("UnusedParameters") View v) {

        mCalendarAdapter.nextMonth();
        mCalendarAdapter.notifyDataSetChanged();
        monthLabel.setText(mCalendarAdapter.getCurrentMonthAndYear());
        readCalendarEvent(this);
    }

    public void onPrevMonth(@SuppressWarnings("UnusedParameters") View v) {

        mCalendarAdapter.prevMonth();
        mCalendarAdapter.notifyDataSetChanged();
        monthLabel.setText(mCalendarAdapter.getCurrentMonthAndYear());
        readCalendarEvent(this);
    }



    public void readCalendarEvent(Context context)  {
        if ( context != null ) {
            ContentResolver cr = context.getContentResolver();
            Calendar startTime = Calendar.getInstance();
            // TODO: Modify mCalendarAdapter interface to return month,year,day of start
            // and end.
            startTime.set(mCalendarAdapter.getYear(), mCalendarAdapter.getMonth(), 0, 0, 0);
            Calendar endTime = Calendar.getInstance();
            endTime.set(mCalendarAdapter.getYear(), mCalendarAdapter.getMonth(), 31, 23, 59);

            String[] projection = new String[]{CalendarContract.Events.CALENDAR_ID,
                    CalendarContract.Events.TITLE,
                    CalendarContract.Events.DESCRIPTION,
                    CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND,
                    CalendarContract.Events.EVENT_LOCATION};
            String sortOrder = CalendarContract.Events.DTSTART + " ASC"; // timeslots.timestart
            String selection = "(( " + CalendarContract.Events.DTSTART + " >= "
                    + startTime.getTimeInMillis() + " ) AND ( "
                    + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() + " ))";
            mAppointments.clear();

            try {
                Cursor cursor = cr.query(CalendarContract.Events.CONTENT_URI, projection, selection, null, sortOrder);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    do {
                        Appointment appt = new Appointment();
                        int calendar_id = cursor.getInt(0);
                        appt.setId(calendar_id);

                        String title = cursor.getString(1);
                        appt.setTitle(title);

                        String description = cursor.getString(2);
                        appt.setDescription(description);

                        long dtstart1 = cursor.getLong(3);
                        appt.setStartDate(dtstart1);

                        long dtend1 = cursor.getLong(4);
                        appt.setEndDate(dtend1);

                        String eventlocation = cursor.getString(5);
                        appt.setLocation(eventlocation);
                        mAppointments.add(appt);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
            } catch (AssertionError ex) {
                ex.printStackTrace();
            } catch (SecurityException ex) {
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mAppointmentsAdapter != null) {
                mAppointmentsAdapter.notifyDataSetChanged();
            }
        }
    }
}
