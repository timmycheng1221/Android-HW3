package timmycheng.phonebook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by timmy on 2016/4/30.
 */
public class UpdateActivity extends AppCompatActivity{

    DatabaseHelper dbhelper;
    EditText name_input, phone_input;
    String old_name, old_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name", old_name);
                intent.putExtra("phone", old_phone);
                intent.setClass(UpdateActivity.this, ContactActivity.class);
                finish();
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        name_input = (EditText) (findViewById(R.id.name_input));
        phone_input = (EditText) (findViewById(R.id.phone_input));
        name_input.setText(bundle.getString("name"));
        phone_input.setText(bundle.getString("phone"));
        old_name = bundle.getString("name");
        old_phone = bundle.getString("phone");

        openDatabase();
    }

    private void openDatabase(){
        dbhelper = new DatabaseHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save:
                save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void save() {
        if (name_input.getText().toString().isEmpty() || phone_input.getText().toString().isEmpty() ) {
            Toast toast = Toast.makeText(this, "請輸入姓名和電話", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            String[] columns = {"name"};
            Cursor cursor = db.query("contact_table", columns, "name='" + name_input.getText().toString() + "'", null, null, null, null);
            if (cursor.getCount() == 0) {
                insert();
            } else {
                update();
            }

            Intent intent = new Intent();
            intent.setClass(UpdateActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }

    public void insert() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete("contact_table", "name" + "='" + old_name + "'", null);
        ContentValues values = new ContentValues();
        values.put("name", name_input.getText().toString());
        values.put("phone", phone_input.getText().toString());
        db.insert("contact_table", null, values);
    }

    public void update() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", phone_input.getText().toString());
        db.update("contact_table", values, "name" + "='" + name_input.getText().toString() + "'", null);
    }

}
