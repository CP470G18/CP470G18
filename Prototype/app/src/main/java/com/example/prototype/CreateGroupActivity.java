package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
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

import com.google.firebase.BuildConfig;

import java.util.List;

public class CreateGroupActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "CreateGroupActivity";
    private EditText name;
    private EditText description;
    private Button submit;
    private Button Cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);


        Toolbar toolbar_group = (Toolbar) findViewById(R.id.toolbar_group);
        toolbar_group.setTitle("Create_Group");
        setSupportActionBar(toolbar_group);

        name = findViewById(R.id.editTextTextPersonName);
        description = findViewById(R.id.editTextTextDescription);
        submit = (Button) findViewById(R.id.button1);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Title", name.getText().toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
//                Group group = new Group();
//                group.setName(name.getText().toString());
//                group.setDescription(description.getText().toString());
//
//                new FirebaseDatabaseHelper().addGroup(group, new FirebaseDatabaseHelper.GroupDataStatus() {
//                            @Override
//                            public void DataIsLoaded(List<Group> Groups, List<String> keys) {
//                                Toast.makeText(CreateGroupActivity.this, "Item Successfully saved", Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void DataIsInserted() {
//
//                            }
//
//                            @Override
//                            public void DataIsUpdated() {
//
//                            }
//
//                            @Override
//                            public void DataIsDeleted() {
//
//                            }
//                });
//
//                        finish();

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
                LayoutInflater inflater = CreateGroupActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.custom_dialog,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroupActivity.this);
                builder.setView(view);
                builder.setTitle("Help");
                TextView instruction = view.findViewById(R.id.instruction) ;
                instruction.setText("Instrction:\nInput the name and description for your group list \n" +
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
