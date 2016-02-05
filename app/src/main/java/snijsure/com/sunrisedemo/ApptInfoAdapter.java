package snijsure.com.sunrisedemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/* 
* This classs is adapter interface to represent
* all the appointments one may have in the calendar.
* It uses inner class ApptHolder that contains the view
* information to display it on the screen.
*/

public class ApptInfoAdapter extends RecyclerView.Adapter<ApptInfoAdapter.ApptHolder> {

    // Right now appointments are only added to this list
    private ArrayList<Appointment> mAppointments;

    public ApptInfoAdapter(ArrayList<Appointment> list) {
        mAppointments = list;
    }

    @Override
    public ApptHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appt_item, parent, false);
        return new ApptHolder(view);
    }

    @Override
    public void onBindViewHolder(final ApptHolder holder, int pos) {
        Appointment rowItem = mAppointments.get(pos);
        holder.bindAppt(rowItem);
    }


    @Override
    public int getItemCount() {

        return mAppointments.size();
    }


    // This is the class that holds single appointment entry
    class ApptHolder extends RecyclerView.ViewHolder {
        View mHolderView;
        TextView title;
        TextView description;
        TextView startStop;
        TextView location;

        public ApptHolder(View itemView) {
            super(itemView);

            mHolderView = itemView;
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            startStop = (TextView) itemView.findViewById(R.id.startStop);
            location = (TextView) itemView.findViewById(R.id.location);
        }

        public void bindAppt(Appointment apptItem) {
            title.setText(apptItem.getTitle());
            description.setText(apptItem.getDescription());
            // See documentation at
            // https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
            startStop.setText("From " + apptItem.getStartDate("M d y h:mm a") +
                    " To " + apptItem.getEndDate("M d y h:mm a"));
            location.setText(apptItem.getLocation());

        }
    }
}
