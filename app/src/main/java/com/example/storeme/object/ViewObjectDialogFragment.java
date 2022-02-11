package com.example.storeme.object;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.storeme.R;
import com.example.storeme.ui.main.PlaceholderFragment;

import java.util.Arrays;
import java.util.List;

public class ViewObjectDialogFragment extends DialogFragment {
    //Variables
    private TextView object_attribute1_Text;
    private TextView object_attribute2_Text;
    private ImageButton editButton;
    private ImageButton deleteButton;
    private EditText type_editText;
    private EditText attribute1_editText;
    private EditText attribute2_editText;

    //Constructor
    public static ViewObjectDialogFragment newInstance(StoreMeObject object) {
        ViewObjectDialogFragment frag = new ViewObjectDialogFragment();
        Bundle args = new Bundle();
        args.putString("type", object.getObjectType());
        args.putString("att1", object.getObjectAttribute1());
        args.putString("att2", object.getObjectAttribute2());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialogfragment_viewobject, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get labels from layout and fetch arguments from bundle
        object_attribute1_Text = view.findViewById(R.id.object_att1_label);
        object_attribute2_Text = view.findViewById(R.id.object_att2_label);
        List<String> list = Arrays.asList(getResources().getStringArray(R.array.types_media));
        int index = list.indexOf(getArguments().getString("type")) + 1;
        object_attribute1_Text.setText(getResources().getStringArray(R.array.attribute1_string)[index]);
        object_attribute2_Text.setText(getResources().getStringArray(R.array.attribute2_string)[index]);

        // get edit texts from layout and fetch arguments from bundle
        type_editText = view.findViewById(R.id.object_type_editText);
        type_editText.setText(getArguments().getString("type"));
        attribute1_editText = view.findViewById(R.id.object_att1_editText);
        attribute1_editText.setText(getArguments().getString("att1"));
        attribute2_editText = view.findViewById(R.id.object_att2_editText);
        attribute2_editText.setText(getArguments().getString("att2"));

        //Access buttons from layout
        editButton = view.findViewById(R.id.object_edit_button);
        deleteButton = view.findViewById(R.id.object_delete_button);

        //Button click listener
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Do something here", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Do something here", Toast.LENGTH_SHORT).show();
            }
        });

    }
}