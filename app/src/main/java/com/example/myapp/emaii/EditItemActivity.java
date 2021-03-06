package com.example.myapp.emaii;

/**
 * Created by mark22 on 05/09/2015.
 */

//import android.support.v7.app.ActionBarActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        final String itemID = getIntent().getStringExtra("id");
        makeText(getApplicationContext(), "EditItem\nonCreate\nitemID=" +itemID, LENGTH_LONG).show();

        TextView edit_item_datetime = (TextView) findViewById (R.id.edit_item_datetime);
        final TextView edit_item_title = (TextView) findViewById (R.id.edit_item_title);
        final TextView edit_item_content = (TextView) findViewById (R.id.edit_item_content);

        final DataBaseHelper db=new DataBaseHelper(this);
        db.open();
        Cursor c=db.getItem(itemID);
        if (c.moveToFirst()) {
            String text = "\n"+c.getString(0)
            +"\n"+c.getString(1)
            +"\n"+c.getString(2)
            +"\n"+c.getString(3);
            makeText(getApplicationContext(),"EditItem\n_id="+itemID+" found:"+text, LENGTH_LONG).show();
            edit_item_datetime.setText(c.getString(0));
            edit_item_title.setText(c.getString(1));
            edit_item_content.setText(c.getString(2));
        }
        else makeText(getApplicationContext(),"EditItem\n_id="+itemID+" not found", LENGTH_LONG).show();
        db.close();

        Button button_edit_item_cancel=(Button)findViewById(R.id.button_edit_item_cancel);
        button_edit_item_cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();}
        });


        Button button_edit_item_save=(Button)findViewById(R.id.button_edit_item_save);
        button_edit_item_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.open();
                db.updateItem(itemID, edit_item_title.getText().toString(), edit_item_content.getText().toString());
                db.close();
                finish();
            }
        });

        Button button_edit_item_delete=(Button)findViewById(R.id.button_edit_item_delete);
        button_edit_item_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeText(getApplicationContext(), "SQLite\nDelete Item\n"+itemID, LENGTH_LONG).show();
                db.open();
                db.deleteItem(itemID);
                db.close();
                finish();
            }
        });
    }

    public void sendItem(View v){
        String[] to={"apps@brobyn.com"};
        sendEmail(to,"Test","This is a test");
    }

    private void sendEmail(String[] emailAddresses, String subject, String message){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        String[] to = emailAddresses;
        emailIntent.putExtra(Intent.EXTRA_EMAIL,to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT,message);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "email"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
