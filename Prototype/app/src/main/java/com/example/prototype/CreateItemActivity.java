package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateItemActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "CreateItemActivity";
    private EditText name;
    private EditText cost;
    private EditText description;
    private Button submit;
    private Button Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item_actvity);

        Toolbar toolbar_item = (Toolbar) findViewById(R.id.toolbar_item);
        toolbar_item.setTitle(R.string.new_item);
        setSupportActionBar(toolbar_item);

        final EditText text = findViewById(R.id.editTextTextPersonName);
        Button b = (Button) findViewById(R.id.button);
        name = (EditText) findViewById(R.id.editTextTextPersonName);
        description = (EditText) findViewById(R.id.editTextTextDescription);
        cost= (EditText) findViewById(R.id.editTextTextCost);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Response",text.getText().toString());
                resultIntent.putExtra("Price",cost.getText().toString());
                resultIntent.putExtra("Desc",description.getText().toString());
                setResult(-1, resultIntent);
                Item item = new Item();

                name = findViewById(R.id.editTextTextPersonName);
                cost = findViewById(R.id.editTextTextCost);
                description = findViewById(R.id.editTextTextDescription);

                item.setName(name.getText().toString());
                item.setCost(Integer.valueOf(cost.getText().toString()));
                item.setDescription(description.getText().toString());

                String listKey = getIntent().getExtras().getString("listKey");
                new FirebaseDatabaseHelper().addItem(item, listKey, new FirebaseDatabaseHelper.ItemDataStatus(){
                    @Override
                    public void DataIsLoaded(ArrayList<Item> items, ArrayList<String> keys) {
                        Toast.makeText(CreateItemActivity.this, "Item Successfully saved", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsInserted() {
                        Log.i("Data Inserted","data");
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
                finish();

            }
        });

        Cancel = (Button) findViewById(R.id.button2);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_create, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.About:
                LayoutInflater inflater = CreateItemActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.custom_dialog,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateItemActivity.this);
                builder.setView(view);
                builder.setTitle("Help");
                TextView instruction = view.findViewById(R.id.instruction) ;
                instruction.setText("Instrction:\nInput the name,description and price for your group list \n" +
                        "Click SUBMIT to save the information\nClick CANCEL to go back ");
                TextView version = view.findViewById(R.id.version) ;
                String version_name = com.google.firebase.BuildConfig.VERSION_NAME;
                int version_code = BuildConfig.VERSION_CODE;
                version.setText("versionName:"+version_name+" versionCode:"+version_code);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    };

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    };

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    };

    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    };

    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    };
}