package com.example.storeme;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class NewObject extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newobject);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        Spinner typeSpinner = (Spinner) findViewById(R.id.type_spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(NewObject.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.types_media));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(myAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView no_attribute1_Text = (TextView) findViewById(R.id.no_attribute1_label);
                no_attribute1_Text.setText(getResources().getStringArray(R.array.attribute1_string)[position]);
                no_attribute1_Text.setVisibility(View.VISIBLE);
                TextView no_attribute2_Text = (TextView) findViewById(R.id.no_attribute2_label);
                no_attribute2_Text.setText(getResources().getStringArray(R.array.attribute2_string)[position]);
                no_attribute2_Text.setVisibility(View.VISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}