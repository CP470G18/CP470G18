package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ItemDetails extends AppCompatActivity {

    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_item_details);
        setContentView(R.layout.activity_item_details);
       // FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
         ListFragment mf = new ListFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();







        Intent intent = getIntent();
        final Bundle bd = intent.getBundleExtra("bundle");
        Log.i("this is what",bd.getString("message"));


        mf.setElements(bd);
        mf.setArguments(bd);
        //ft.add(R.id.the_frame,mf);

        ft.replace(R.id.listfragmentlayout, mf);

        ft.commit();


        edit = (Button) findViewById(R.id.button3);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetails.this,UpdateDeleteItemActivity.class);

                startActivityForResult(intent, 2);
            }
        });

    }
}