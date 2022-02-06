package com.example.storeme;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class NewObject extends DialogFragment {
    //Variables
    private ObjectDataBase myDataBase;
    private Spinner typeSpinner;
    private TextView no_attribute1_Text;
    private TextView no_attribute2_Text;
    private Button addObjectButton;
    private EditText attribute1_editText;
    private EditText attribute2_editText;
    private String objectType_DB;
    private String objectAtt1_BD;
    private String objectAtt2_BD;

    //Constructor
    public static NewObject newInstance(String title) {
        NewObject frag = new NewObject();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_newobject, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // creating a new ObjectDataBase class and passing our context to it.
        myDataBase = new ObjectDataBase(view.getContext());

        //Access spinner from activity_newobject
        typeSpinner = (Spinner) view.findViewById(R.id.type_spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.types_media));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(myAdapter);
        no_attribute1_Text = (TextView) view.findViewById(R.id.no_attribute1_label);
        no_attribute2_Text = (TextView) view.findViewById(R.id.no_attribute2_label);
        //Spinner click listener
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                no_attribute1_Text.setText(getResources().getStringArray(R.array.attribute1_string)[position]);
                no_attribute2_Text.setText(getResources().getStringArray(R.array.attribute2_string)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Access add new object button from activity_newobject
        addObjectButton = (Button) view.findViewById(R.id.object_button);
        attribute1_editText = (EditText) view.findViewById(R.id.attibute1_edit);
        attribute2_editText = (EditText) view.findViewById(R.id.attibute2_edit);
        //Button click listener
        addObjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                objectType_DB = typeSpinner.getSelectedItem().toString();
                objectAtt1_BD = attribute1_editText.getText().toString();
                objectAtt2_BD = attribute2_editText.getText().toString();

                // validating if the text fields are empty or not.
                if (objectAtt1_BD.isEmpty() && objectAtt2_BD.isEmpty()) {
                    Toast.makeText(view.getContext(), "Please enter all the data", Toast.LENGTH_SHORT).show();
                    return;
                }
                // check if object already exists in database
                if (myDataBase.checkIfObjectExists(objectType_DB, objectAtt1_BD, objectAtt2_BD)){
                    Toast.makeText(view.getContext(), "Object already exists", Toast.LENGTH_SHORT).show();
                }else{
                    // on below line we are calling a method to add new
                    // course to sqlite data and pass all our values to it.
                    myDataBase.addNewObject(objectType_DB, objectAtt1_BD, objectAtt2_BD);

                    // after adding the data we are displaying a toast message.
                    Toast.makeText(view.getContext(), "Object has been added", Toast.LENGTH_SHORT).show();
                    attribute1_editText.setText("");
                    attribute2_editText.setText("");
                }

            }
        });
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Add new object");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        typeSpinner.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /*@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newobject);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        // creating a new ObjectDataBase class and passing our context to it.
        myDataBase = new ObjectDataBase(NewObject.this);

        //Access spinner from activity_newobject
        typeSpinner = (Spinner) findViewById(R.id.type_spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(NewObject.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.types_media));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(myAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView no_attribute1_Text = (TextView) findViewById(R.id.no_attribute1_label);
                no_attribute1_Text.setText(getResources().getStringArray(R.array.attribute1_string)[position]);
                TextView no_attribute2_Text = (TextView) findViewById(R.id.no_attribute2_label);
                no_attribute2_Text.setText(getResources().getStringArray(R.array.attribute2_string)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Access add new object button from activity_newobject
        Button addObjectButton = (Button) findViewById(R.id.object_button);

        addObjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText attribute1_editText = (EditText) findViewById(R.id.attibute1_edit);
                EditText attribute2_editText = (EditText) findViewById(R.id.attibute2_edit);

                String objectType_DB = typeSpinner.getSelectedItem().toString();
                String objectAtt1_BD = attribute1_editText.getText().toString();
                String objectAtt2_BD = attribute2_editText.getText().toString();

                // validating if the text fields are empty or not.
                if (objectAtt1_BD.isEmpty() && objectAtt2_BD.isEmpty()) {
                    Toast.makeText(NewObject.this, "Please enter all the data", Toast.LENGTH_SHORT).show();
                    return;
                }
                // check if object already exists in database
                if (myDataBase.checkIfObjectExists(objectType_DB, objectAtt1_BD, objectAtt2_BD)){
                    Toast.makeText(NewObject.this, "Object already exists", Toast.LENGTH_SHORT).show();
                }else{
                    // on below line we are calling a method to add new
                    // course to sqlite data and pass all our values to it.
                    myDataBase.addNewObject(objectType_DB, objectAtt1_BD, objectAtt2_BD);

                    // after adding the data we are displaying a toast message.
                    Toast.makeText(NewObject.this, "Object has been added", Toast.LENGTH_SHORT).show();
                    attribute1_editText.setText("");
                    attribute2_editText.setText("");
                }

            }
        });
    }*/
}