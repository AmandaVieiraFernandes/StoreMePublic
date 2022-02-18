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
    private ObjectDataBase myDataBase;
    private TextView object_attribute1_Text;
    private TextView object_attribute2_Text;
    private ImageButton editButton;
    private ImageButton deleteButton;
    private Button saveButton;
    private Button cancelButton;
    private EditText type_editText;
    private EditText attribute1_editText;
    private EditText attribute2_editText;

    //Constructor
    public static ViewObjectDialogFragment newInstance(StoreMeObject object) {
        ViewObjectDialogFragment frag = new ViewObjectDialogFragment();
        Bundle args = new Bundle();
        args.putInt("id", object.getObjectId());
        args.putString("category", object.getObjectCategory());
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
        int index = list.indexOf(getArguments().getString("type"));
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
        saveButton = view.findViewById(R.id.vosave_button);
        cancelButton = view.findViewById(R.id.vocancel_button);

        // creating a new ObjectDataBase class and passing our context to it.
        myDataBase = new ObjectDataBase(view.getContext());

        //Button click listener
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_editText.setEnabled(true);
                attribute1_editText.setEnabled(true);
                attribute2_editText.setEnabled(true);
                saveButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);

                saveButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(myDataBase.updateObject(getArguments().getInt("id"),getArguments().getString("category"),type_editText.getText().toString(),attribute1_editText.getText().toString(),attribute2_editText.getText().toString())){
                            //update fragments with deleted object
                            List<Fragment> updFrag = getParentFragmentManager().getFragments();
                            for(Fragment fr : updFrag) {
                                if (fr.getClass().equals(PlaceholderFragment.class)) {
                                    getParentFragmentManager().beginTransaction().detach(fr).commit();
                                    getParentFragmentManager().beginTransaction().attach(fr).commit();
                                }
                            }

                            type_editText.setEnabled(false);
                            attribute1_editText.setEnabled(false);
                            attribute2_editText.setEnabled(false);
                            saveButton.setVisibility(View.INVISIBLE);
                            cancelButton.setVisibility(View.INVISIBLE);

                            Toast.makeText(v.getContext(), "Object updated successfully", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(v.getContext(), "Problem when updating object", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        type_editText.setEnabled(false);
                        attribute1_editText.setEnabled(false);
                        attribute2_editText.setEnabled(false);
                        saveButton.setVisibility(View.INVISIBLE);
                        cancelButton.setVisibility(View.INVISIBLE);

                        Toast.makeText(v.getContext(), "Object edit cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myDataBase.deleteObject(getArguments().getInt("id"))){
                    //update fragments with deleted object
                    List<Fragment> updFrag = getParentFragmentManager().getFragments();
                    for(Fragment fr : updFrag) {
                        if (fr.getClass().equals(PlaceholderFragment.class)) {
                            getParentFragmentManager().beginTransaction().detach(fr).commit();
                            getParentFragmentManager().beginTransaction().attach(fr).commit();
                        }
                    }
                    Toast.makeText(v.getContext(), "Object deleted successfully", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else{
                    Toast.makeText(v.getContext(), "Problem in deleting object", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}