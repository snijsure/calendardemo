package snijsure.com.sunrisedemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

class CalendarInfoAdapter extends RecyclerView.Adapter<TextViewHolder> {

    private static final String TAG="Adapter";
    private static final int ITEM_VIEW_TYPE_DAY_NAME = 0;
    private static final int ITEM_VIEW_TYPE_DATE = 1;
    private ArrayList<DayInfo> dayInfoList;
    private Calendar mCalendarToday;
    private int mMonth;
    private int mYear;
    public CalendarInfoAdapter(int month, int year) {
        mMonth = month;
        mYear = year;
        mCalendarToday = Calendar.getInstance();
        dayInfoList = new ArrayList<>();
        populateMonth();
    }
    public int getMonth() {
        return mMonth;
    }
    public int getYear() {
        return mYear;
    }

    private void populateMonth() {
        int prevMonthEndDate;
        int forYear = mYear;
        int mDaysAdded = 0;
        int i;

        dayInfoList.clear();

        GregorianCalendar mCalendar = new GregorianCalendar(mYear, mMonth, 1);
        int firstDay = getFirstDayOfTheWeek(mCalendar.get(Calendar.DAY_OF_WEEK)) -1;

        if (mMonth == 0) {
            forYear = mYear - 1;
            prevMonthEndDate = daysInMonth(11,forYear);
        }
        else {
            prevMonthEndDate = daysInMonth(mMonth - 1,mYear);
        }

        while ( firstDay > 0 ) {
            DayInfo d = new DayInfo((prevMonthEndDate-firstDay),mMonth-1,forYear);

            dayInfoList.add(d);

            mDaysAdded++;
            firstDay--;
        }

        int maxDays = daysInMonth(mMonth,mYear);
        for ( i = 0; i < maxDays; i++) {
            DayInfo d = new DayInfo(i,mMonth,mYear);
            dayInfoList.add(d);
            mDaysAdded++;
        }

        if ( mMonth == 11 ) {
            forYear = mYear + 1;
        }

        i = 0;
        while (mDaysAdded != 35) {
            DayInfo d = new DayInfo(i,mMonth+1,forYear);
            dayInfoList.add(d);
            mDaysAdded++;
            i++;
        }
    }

    private int daysInMonth(int month,int year) {
        Calendar cal = new GregorianCalendar(year, month, 1);
        int ret = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return ret;
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
                int mCellType = ITEM_VIEW_TYPE_DAY_NAME;
                holder.textView.setText("Sun");
                break;
            case 1:
                mCellType = ITEM_VIEW_TYPE_DAY_NAME;
                holder.textView.setText("Mon");
                break;
            case 2:
                mCellType = ITEM_VIEW_TYPE_DAY_NAME;
                holder.textView.setText("Tue");
                break;
            case 3:
                mCellType = ITEM_VIEW_TYPE_DAY_NAME;
                holder.textView.setText("Wed");
                break;
            case 4:
                mCellType = ITEM_VIEW_TYPE_DAY_NAME;
                holder.textView.setText("Thus");
                break;
            case 5:
                mCellType = ITEM_VIEW_TYPE_DAY_NAME;
                holder.textView.setText("Fri");
                break;
            case 6:
                mCellType = ITEM_VIEW_TYPE_DAY_NAME;
                holder.textView.setText("Sat");
                break;
            default:
                mCellType = ITEM_VIEW_TYPE_DATE;
                final String label = Integer.toString(dayInfoList.get(position-7).getDay()+1);
                holder.textView.setText(label);
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


}
