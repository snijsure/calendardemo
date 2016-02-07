package snijsure.com.sunrisedemo;

import android.os.Bundle;
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


        mCalendarAdapter = new CalendarInfoAdapter(mDayInfoList,
                cal.get(Calendar.MONTH),
                cal.get(Calendar.YEAR));

        genTestAppointments(120);
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

    public void onNextMonth(@SuppressWarnings("UnusedParameters") View v) {

        mCalendarAdapter.nextMonth();
        mCalendarAdapter.notifyDataSetChanged();
        monthLabel.setText(mCalendarAdapter.getCurrentMonthAndYear());

    }

    public void onPrevMonth(@SuppressWarnings("UnusedParameters") View v) {

        mCalendarAdapter.prevMonth();
        mCalendarAdapter.notifyDataSetChanged();
        monthLabel.setText(mCalendarAdapter.getCurrentMonthAndYear());
    }

    /*
     * Generates number of dummy appointments.
     * One could certainly use the CalendarProvider sync adapter to import data
     * for now this should suffice.
     */
    public void genTestAppointments(int numAppts) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date d = formatter.parse("2016-02-06 08:30");
            long timestamp1 = d.getTime();
            long timestamp2 = timestamp1 + 3600000;

            for (int i = 0; i < numAppts; ) {
                Appointment app1 = new Appointment("Meeting with Dan",
                        "About Android eng job",
                        "100 Market", timestamp1, timestamp2);

                timestamp1 += 3600000;
                timestamp2 = timestamp1 + 3600000;
                Appointment app2 = new Appointment("Meeting with Mark",
                        "About cook job",
                        "101 Market Oakland", timestamp1, timestamp2);

                timestamp1 += 3600000;
                timestamp2 = timestamp1 + 1200000;
                Appointment app3 = new Appointment("Meeting with Linda",
                        "Swim lesson",
                        "300 California", timestamp1, timestamp2);

                timestamp1 += 3600000;
                timestamp2 = timestamp1 + 1800000;
                Appointment app4 = new Appointment("Meeting with Ana",
                        "Yoga class",
                        "400 Telegraph Oakland", timestamp1, timestamp2);

                mAppointments.add(app1);
                mAppointments.add(app2);
                mAppointments.add(app3);
                mAppointments.add(app4);
                i += 4;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error generating test data");
        }
        Collections.sort(mAppointments);

    }
}
