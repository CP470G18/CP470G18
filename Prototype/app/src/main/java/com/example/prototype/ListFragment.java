package com.example.prototype;

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
    String messageText;
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
        delete_button = (Button) v.findViewById(R.id.messageDelete);
        the_message = (TextView) v.findViewById(R.id.messageFrag);
//        idText = (TextView) v.findViewById(R.id.idText);
        the_message.setText(messageText);
//        idText.setText((String.valueOf(the_id)));
//        delete_button.setText(R.string.deleteButton);
        delete_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                getActivity().setResult((int) textId);
                // if(the_type=="yes"){
                getActivity().finish();
                //}


            }
        });
        return v;
    }

    public void setElements(Bundle args){
        messageText=args.getString("message");
        textId=args.getLong("the_id");

        the_args=args;
    }


}
