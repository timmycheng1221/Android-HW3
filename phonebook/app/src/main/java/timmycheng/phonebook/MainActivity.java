package timmycheng.phonebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbhelper;
    ListView listView;
    ArrayList<String> list_name = new ArrayList<>();
    ArrayList<String> list_phone = new ArrayList<>();
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, InsertActivity.class);
                finish();
                startActivity(intent);
            }
        });

        openDatabase();

        listView = (ListView) (findViewById(R.id.list));
        read_list();
        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list_name);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("name", list_name.get(position));
                intent.putExtra("phone", list_phone.get(position));
                intent.setClass(MainActivity.this, ContactActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void openDatabase(){
        dbhelper = new DatabaseHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void read_list() {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String[] columns = {"name", "phone"};
        Cursor cursor = db.query("contact_table", columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String phone = cursor.getString(1);
            list_name.add(name);
            list_phone.add(phone);
        }
    }
}
