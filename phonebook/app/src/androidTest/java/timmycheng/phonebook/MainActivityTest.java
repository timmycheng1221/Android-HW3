package timmycheng.phonebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.test.ActivityUnitTestCase;
import android.widget.ListView;

/**
 * Created by timmy on 2016/5/2.
 */
public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
    private Intent mMainIntent;
    DatabaseHelper dbhelper;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mMainIntent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
    }

    public void testListView() {
        startActivity(mMainIntent, null, null);

        final ListView listView = (ListView) getActivity().findViewById(R.id.list);
        String name = listView.getAdapter().getItem(0).toString();

        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String[] columns = {"name"};
        Cursor cursor = db.query("contact_table", columns, "_ID" + "=" + 1 , null, null, null, null);
        String sql_name = cursor.getString(0);

        assertEquals("name(list) not equal to name(sql)", name, sql_name);
    }

    public void testFloatingActionButton() {
        startActivity(mMainIntent, null, null);

        final FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add);
        fab.performClick();
        final Intent intent = getStartedActivityIntent();

        assertNotNull("Intent was null", intent);

        assertTrue(isFinishCalled());
    }
}