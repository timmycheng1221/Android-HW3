package timmycheng.phonebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by timmy on 2016/4/30.
 */
public class ContactActivity extends AppCompatActivity{

    DatabaseHelper dbhelper;
    TextView name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ContactActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        name = (TextView) (findViewById(R.id.name));
        phone = (TextView) (findViewById(R.id.phone));
        name.setText(bundle.getString("name"));
        phone.setText(bundle.getString("phone"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_call);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dial = new Intent();
                dial.setAction("android.intent.action.DIAL");
                dial.setData(Uri.parse("tel:" + phone.getText()));
                startActivity(dial);
            }
        });

        openDatabase();
    }

    private void openDatabase(){
        dbhelper = new DatabaseHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent();
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("phone", phone.getText().toString());
                intent.setClass(this, UpdateActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.action_delete:
                AlertDialog.Builder delete = new AlertDialog.Builder(this);
                delete.setMessage(R.string.text_delete);
                delete.setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        SQLiteDatabase db = dbhelper.getWritableDatabase();
                        db.delete("contact_table", "name" + "='" + name.getText().toString() + "'", null);
                        Intent intent = new Intent();
                        intent.setClass(ContactActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
                delete.setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                });
                delete.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
