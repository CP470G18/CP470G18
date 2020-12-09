package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {
    Button delete_button;
    TextView the_message;
    TextView the_price;
    TextView the_desc;
    String messageText;
    String priceText;
    String descText;
    String listName;
    private FirebaseDatabaseHelper dbHelper;
    TextView nt;
    TextView pt;
    TextView dt;
    long textId;
    Bundle the_args;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_fragment, parent, false);
        //delete_button = (Button) v.findViewById(R.id.messageDelete);
        the_message = (TextView) v.findViewById(R.id.messageFrag);
        the_desc= (TextView) v.findViewById(R.id.descFrag);
        the_price= (TextView) v.findViewById(R.id.priceFrag);
        nt= (TextView) v.findViewById(R.id.textView2);
        pt= (TextView) v.findViewById(R.id.textView3);
        dt= (TextView) v.findViewById(R.id.textView5);
        delete_button.setText(R.string.delText);

        nt.setText(R.string.nameText);
        pt.setText(R.string.priceText);
        dt.setText(R.string.descText);
//        idText = (TextView) v.findViewById(R.id.idText);
        the_message.setText(messageText);
        the_desc.setText(descText);
        the_price.setText(priceText);
//        idText.setText((String.valueOf(the_id)));
//        delete_button.setText(R.string.deleteButton);
        dbHelper = new FirebaseDatabaseHelper();
        dbHelper.setList(listName);

        //delete_button.setOnClickListener(new View.OnClickListener(){

           // @Override
            //public void onClick(View v) {
                //dbHelper.deleteItem();
                //getActivity().setResult(((int) textId)+1);
                // if(the_type=="yes"){
                //getActivity().finish();
                //}




            //}
       // });
        return v;
    }

    public void setElements(Bundle args){
        messageText=args.getString("message");
        textId=args.getLong("the_id");
        priceText=args.getString("Price");
        descText=args.getString("Desc");
        listName=args.getString("ListName");
        the_args=args;
    }


}
