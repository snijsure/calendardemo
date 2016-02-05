package snijsure.com.sunrisedemo;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


class CalendarInfoAdapter extends RecyclerView.Adapter<CalendarInfoAdapter.TextViewHolder> {

    private static final String TAG="Adapter";
    private static final int ITEM_VIEW_TYPE_DAY_NAME = 0;
    private static final int ITEM_VIEW_TYPE_DATE = 1;
    private final List<DayInfo> dayInfoList;
    private Calendar mCalendarToday;
    private int mMonth;
    private int mYear;
    public CalendarInfoAdapter(ArrayList<DayInfo> list, int month, int year) {
        mMonth = month;
        mYear = year;
        mCalendarToday = Calendar.getInstance();
        dayInfoList = list;
        populateMonth();
    }
    public int getMonth() {
        return mMonth;
    }
    public int getYear() {
        return mYear;
    }

    private synchronized void populateMonth() {
        int prevMonthEndDate;
        int forYear = mYear;
        int mDaysAdded = 0;
        int i;
        DayInfo dayInfo;


        GregorianCalendar mCalendar = new GregorianCalendar(mYear, mMonth, 1);
        int firstDay = getFirstDayOfTheWeek(mCalendar.get(Calendar.DAY_OF_WEEK)) -1;

        if (mMonth == 0) {
            forYear = mYear - 1;
            prevMonthEndDate = daysInMonth(11,forYear);
        }
        else {
            prevMonthEndDate = daysInMonth(mMonth - 1,mYear);
        }

        while ( firstDay > 0 && mDaysAdded < 42) {
            dayInfo  = dayInfoList.get(mDaysAdded);
            dayInfo.setDay(prevMonthEndDate-firstDay);
            dayInfo.setMonth(mMonth-1);
            dayInfo.setYear(forYear);
            mDaysAdded++;
            firstDay--;
        }

        int maxDays = daysInMonth(mMonth,mYear);
        for ( i = 0; i < maxDays && mDaysAdded < 42; i++) {
            dayInfo  = dayInfoList.get(mDaysAdded);
            dayInfo.setDay(i);
            dayInfo.setMonth(mMonth);
            dayInfo.setYear(mYear);

            mDaysAdded++;
        }

        if ( mMonth == 11 ) {
            forYear = mYear + 1;
        }

        i = 0;
        while (mDaysAdded < 42) {
            dayInfo  = dayInfoList.get(mDaysAdded);
            dayInfo.setDay(i);
            dayInfo.setMonth(mMonth+1);
            dayInfo.setYear(forYear);
            mDaysAdded++;
            i++;
        }
    }

    private int daysInMonth(int month,int year) {
        Calendar cal = new GregorianCalendar(year, month, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    private int getFirstDayOfTheWeek(int day) {
        switch (day) {
            case Calendar.SUNDAY:
                return 1;
            case Calendar.MONDAY:
                return 2;
            case Calendar.TUESDAY:
                return 3;
            case Calendar.WEDNESDAY:
                return 4;
            case Calendar.THURSDAY:
                return 5;
            case Calendar.FRIDAY:
                return 6;
            case Calendar.SATURDAY:
                return 7;
            default:
                return 0;
        }
    }

    private boolean isToday(int day, int month, int year) {
        return mCalendarToday.get(Calendar.MONTH) == month
                && mCalendarToday.get(Calendar.YEAR) == year
                && mCalendarToday.get(Calendar.DAY_OF_MONTH) == day;
    }


    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if ( viewType == ITEM_VIEW_TYPE_DAY_NAME )
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_name, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TextViewHolder holder, final int position) {
        switch (position) {
            case 0:
                holder.textView.setText("Sun");
                break;
            case 1:
                holder.textView.setText("Mon");
                break;
            case 2:
                holder.textView.setText("Tue");
                break;
            case 3:
                holder.textView.setText("Wed");
                break;
            case 4:
                holder.textView.setText("Thus");
                break;
            case 5:
                holder.textView.setText("Fri");
                break;
            case 6:
                holder.textView.setText("Sat");
                break;
            default:
                DayInfo d = dayInfoList.get(position-7);
                if ( d.getMonth() != mMonth )
                    holder.textView.setBackgroundColor(Color.LTGRAY);
                else {
                    if ( isToday(d.getDay(),d.getMonth(),d.getYear() ) )
                        holder.textView.setBackgroundColor(Color.RED);
                    else
                        holder.textView.setBackgroundColor(Color.TRANSPARENT);
                }
                final String label = Integer.toString(d.getDay()+1);
                holder.textView.setText(label);
                /*
                  If the last row will have only days from next month just hide
                  those items.
                  Test this with July 2005, May 2010 those represent
                  interesting case. Write unit test to test these out...
                  http://www.timeanddate.com/calendar/weekday-saturday-1
                 */
                if ( position > 41 && d.getMonth() != mMonth )
                    holder.textView.setVisibility(View.GONE);

                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(
                                holder.textView.getContext(), label, Toast.LENGTH_SHORT).show();
                    }
                });

        }
    }

    @Override
    public int getItemViewType(int position) {
        if ( position <= 6 ) {
            return ITEM_VIEW_TYPE_DAY_NAME;
        }
        else
            return ITEM_VIEW_TYPE_DATE;
    }

    @Override
    public int getItemCount() {
        return dayInfoList.size()+ 7;
    }

    public void nextMonth() {

        if( mMonth == 11 ) {
            mMonth = 1;
            mYear += 1;
        }
        else {
            mMonth += 1;
        }
        populateMonth();
    }

    public void prevMonth() {

        if( mMonth == 0 ) {
            mMonth = 11;
            mYear -= 1;
        }
        else {
            mMonth -= 1;
        }
        populateMonth();
    }
    public String getCurrentMonthAndYear() {

        String monthName = new DateFormatSymbols().getMonths()[mMonth];
        String yearName = Integer.toString(mYear);
        return monthName + " " + yearName;

    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
