package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ItemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ListFragment mf = new ListFragment();






        Intent intent = getIntent();
        final Bundle bd = intent.getBundleExtra("bundle");
        Log.i("this is what",bd.getString("message"));


       mf.setElements(bd);
        mf.setArguments(bd);
        ft.add(R.id.the_frame,mf);

        ft.commit();
    }
}