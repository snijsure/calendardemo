package snijsure.com.sunrisedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private RecyclerView mCalView;
    private RecyclerView mEventView;
    private CalendarInfoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sunrise_main);

        final FrameLayout frame1 = (FrameLayout) findViewById(R.id.frame1);
        final FrameLayout frame2 = (FrameLayout) findViewById(R.id.frame2);

        final GridLayoutManager gridLayoutMgr = new GridLayoutManager(this,7);
        final LinearLayoutManager eventLayoutMgr = new LinearLayoutManager(this);

        mCalView = new RecyclerView(this);
        mEventView = new RecyclerView(this);

        mCalView.setLayoutManager(gridLayoutMgr);
        mEventView.setLayoutManager(eventLayoutMgr);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        frame1.addView(mCalView,params);
        frame2.addView(mEventView,params);

        adapter = new CalendarInfoAdapter(1,2016);

        adapter.setHasStableIds(true);
        mCalView.setAdapter(adapter);
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
        int month = adapter.getMonth();
        int year = adapter.getYear();
        if( month == 11 ) {
            month = 1;
            year += 1;
        }
        else {
            month += 1;
        }
        adapter = new CalendarInfoAdapter(month,year);
        mCalView.swapAdapter(adapter,false);
    }

    public void onPrevMonth(@SuppressWarnings("UnusedParameters") View v) {
        int month = adapter.getMonth();
        int year = adapter.getYear();
        if( month == 0 ) {
            month = 11;
            year -= 1;
            if ( year == 2015 )
                year = 2016;
        }
        else {
            month -= 1;
        }
        adapter = new CalendarInfoAdapter(month,year);
        mCalView.swapAdapter(adapter,false);
    }
}
