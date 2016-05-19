package timmycheng.phonebook;

import android.content.ContentValues;
import android.content.Intent;
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
 * Created by timmy on 2016/5/2.
 */
public class InsertActivity extends AppCompatActivity {

    DatabaseHelper dbhelper;
    EditText name_input, phone_input;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(InsertActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        name_input = (EditText) (findViewById(R.id.name_input));
        phone_input = (EditText) (findViewById(R.id.phone_input));

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
            insert();
            Intent intent = new Intent();
            intent.setClass(InsertActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }

    public void insert() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name_input.getText().toString());
        values.put("phone", phone_input.getText().toString());
        db.insert("contact_table", null, values);
    }

}
