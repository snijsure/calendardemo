package snijsure.com.sunrisedemo;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DateInfoTest extends TestCase {
    CalendarInfoAdapter calInfo;
    private ArrayList<DayInfo> mDayInfoList;

    @Before
    public void setUp() {
        mDayInfoList = new ArrayList<DayInfo>(42);
        for (int i = 0; i < 42; i++) {
            DayInfo d = new DayInfo(0, 0, 0);
            mDayInfoList.add(d);
        }
    }

    @Test
    public void testCurrentMonth() {
        int i = 0;
        calInfo = new CalendarInfoAdapter(mDayInfoList, 1, 2016);
        assertNotNull(calInfo);
        String[] feb2016 = {"31/1", "1/2", "2/2", "3/2", "4/2", "5/2", "6/2", "7/2",
                "8/2", "9/2", "10/2", "11/2", "12/2", "13/2", "14/2", "15/2",
                "16/2", "17/2", "18/2", "19/2", "20/2", "21/2", "22/2", "23/2",
                "24/2", "25/2", "26/2", "27/2", "28/2", "29/2", "1/3", "2/3",
                "3/3", "4/3", "5/3", "6/3", "7/3", "8/3", "9/3", "10/3",
                "11/3", "12/3"};

        String[] array = new String[mDayInfoList.size()];
        for (DayInfo d : mDayInfoList) {
            array[i] = new String(d.toString());
            i++;
        }
        assertTrue(array.length == feb2016.length);
        assertTrue(Arrays.asList(array).equals(Arrays.asList(feb2016)));
    }

    @Test
    public void testFirstDaySaturday() {
        int i = 0;
        calInfo = new CalendarInfoAdapter(mDayInfoList, 1, 2020);
        assertNotNull(calInfo);

        // First day happens to be Satuday.
        String[] feb2020 = {"26/1", "27/1", "28/1", "29/1", "30/1", "31/1", "1/2", "2/2",
                "3/2", "4/2", "5/2", "6/2", "7/2", "8/2", "9/2", "10/2",
                "11/2", "12/2", "13/2", "14/2", "15/2", "16/2", "17/2", "18/2",
                "19/2", "20/2", "21/2", "22/2", "23/2", "24/2", "25/2", "26/2",
                "27/2", "28/2", "29/2", "1/3", "2/3", "3/3", "4/3", "5/3",
                "6/3", "7/3"};


        String[] array = new String[mDayInfoList.size()];
        for (DayInfo d : mDayInfoList) {
            array[i] = new String(d.toString());
            i++;
        }
        assertTrue(array.length == feb2020.length);
        assertTrue(Arrays.asList(array).equals(Arrays.asList(feb2020)));
    }

    @Test
    public void testFirstDaySunday() {
        int i = 0;
        calInfo = new CalendarInfoAdapter(mDayInfoList, 7, 2010);
        assertNotNull(calInfo);

        // First day happens to be Sunday
        String[] aug2010 = {"1/8", "2/8", "3/8", "4/8", "5/8", "6/8", "7/8", "8/8",
                "9/8", "10/8", "11/8", "12/8", "13/8", "14/8", "15/8", "16/8",
                "17/8", "18/8", "19/8", "20/8", "21/8", "22/8", "23/8", "24/8",
                "25/8", "26/8", "27/8", "28/8", "29/8", "30/8", "31/8", "1/9",
                "2/9", "3/9", "4/9", "5/9", "6/9", "7/9", "8/9", "9/9",
                "10/9", "11/9"
        };

        String[] array = new String[mDayInfoList.size()];
        for (DayInfo d : mDayInfoList) {
            array[i] = new String(d.toString());
            i++;
        }
        assertTrue(array.length == aug2010.length);
        assertTrue(Arrays.asList(array).equals(Arrays.asList(aug2010)));
    }
}