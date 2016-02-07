package snijsure.com.sunrisedemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by subodhnijsure on 1/8/16.
 */
public class AddEventFragment extends DialogFragment {

    // region Properties
    private static final String TAG = "AddEventFragment";

    @Bind(R.id.from_date)
    Button from_date_btn;

    @Bind(R.id.to_date)
    Button to_date_btn;

    @Bind(R.id.from_time)
    Button from_time_btn;
    @Bind(R.id.to_time)
    Button to_time_btn;

    @Bind(R.id.set_from_date)
    TextView set_from_date_text;


    @Bind(R.id.set_to_date)
    TextView set_to_date_text;


    @Bind(R.id.add_event_description)
    TextView add_event_description;
    @Bind(R.id.add_event_location)
    TextView add_event_location;
    @Bind(R.id.add_event_title)
    TextView add_event_title;


    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    private Calendar cal = Calendar.getInstance();
    Calendar fromCal = new GregorianCalendar(cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH));

    Calendar toCal = new GregorianCalendar(cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH));

    public AddEventFragment() {
        // Empty constructor required for DialogFragment
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Context mContext = getActivity();

        View view = inflater.inflate(R.layout.dialog_add_event, null, false);
        ButterKnife.bind(this, view);
        Date fromDate = fromCal.getTime();
        set_from_date_text.setText(df.format(fromDate));

        fromDate = toCal.getTime();
        set_to_date_text.setText(df.format(fromDate));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton(R.string.add_event, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO: If the data is valid insert it into appointment list
                        AddEventFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel_add_event, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddEventFragment.this.getDialog().cancel();
                    }
                });

        AlertDialog mAddFenceDialog = builder.create();

        return mAddFenceDialog;
    }

    // TODO: Validate the data if its valid return true
    private boolean dataIsValid() {
        boolean validData = true;
        return validData;
    }


    @OnClick(R.id.from_date)
    public void pick_from_date(View view) {
        DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                         @Override
                                         public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                                             updateFromDate(year, monthOfYear, dayOfMonth);

                                         }
                                     }, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(),
                "from_date");
    }

    public void updateFromDate(int year, int month, int day) {
        fromCal.set(Calendar.YEAR, year);
        fromCal.set(Calendar.MONTH, month);
        fromCal.set(Calendar.DAY_OF_MONTH, day);
        Date fromDate = fromCal.getTime();
        set_from_date_text.setText(df.format(fromDate));
    }

    public void updateToDate(int year, int month, int day) {
        toCal.set(Calendar.YEAR, year);
        toCal.set(Calendar.MONTH, month);
        toCal.set(Calendar.DAY_OF_MONTH, day);
        Date fromDate = toCal.getTime();
        set_to_date_text.setText(df.format(fromDate));
    }

    public void updateFromTime(int hour, int min) {
        fromCal.set(Calendar.HOUR, hour);
        fromCal.set(Calendar.MINUTE, min);
        Date fromDate = fromCal.getTime();
        set_from_date_text.setText(df.format(fromDate));
    }

    public void updateToTime(int hour, int min) {
        toCal.set(Calendar.HOUR, hour);
        toCal.set(Calendar.MINUTE, min);
        Date fromDate = toCal.getTime();
        set_to_date_text.setText(df.format(fromDate));
    }

    @OnClick(R.id.to_date)
    public void pick_to_date(View view) {
        DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                         @Override
                                         public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                                             updateToDate(year, monthOfYear, dayOfMonth);
                                         }
                                     },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "to_date");
    }

    @OnClick(R.id.from_time)
    public void pick_from_time(View view) {

        TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                         @Override
                                         public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                             updateFromTime(hourOfDay, minute);
                                         }
                                     },
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show(getFragmentManager(),
                "to_time");
    }

    @OnClick(R.id.to_time)
    public void pick_to_time(View view) {
        TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                         @Override
                                         public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                             updateToTime(hourOfDay, minute);
                                         }
                                     },
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show(getFragmentManager(), "from_time");
    }
}
