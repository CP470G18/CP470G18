package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateListActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "CreateGroupActivity";
    private EditText name;
    private EditText description;
    private Button submit;
    private Button Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        name = findViewById(R.id.editTextTextPersonName);
        description = findViewById(R.id.editTextTextDescription);
        submit = (Button) findViewById(R.id.button1);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                List list = new List();
                list.setName(name.getText().toString());
                list.setDescription(description.getText().toString());

                new FirebaseDatabaseHelper().addList(list, new FirebaseDatabaseHelper.listDataStatus(){
                    @Override
                    public void DataIsLoaded(ArrayList<List> list, ArrayList<String> keys) {
                        Toast.makeText(CreateListActivity.this, "Item Successfully saved", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsInserted() {

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

    };

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
