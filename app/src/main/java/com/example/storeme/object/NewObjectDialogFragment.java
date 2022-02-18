package com.example.storeme.object;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.storeme.R;
import com.example.storeme.ui.main.PlaceholderFragment;

import java.util.List;

public class NewObjectDialogFragment extends DialogFragment {
    //Variables
    private ObjectDataBase myDataBase;
    private TextView dialog_title;
    private Spinner typeSpinner;
    private TextView no_attribute1_Text;
    private TextView no_attribute2_Text;
    private Button addObjectButton;
    private EditText attribute1_editText;
    private EditText attribute2_editText;
    private String objectCategory_DB;
    private String objectType_DB;
    private String objectAtt1_BD;
    private String objectAtt2_BD;

    //Constructor
    public static NewObjectDialogFragment newInstance(String category,String title) {
        NewObjectDialogFragment frag = new NewObjectDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("category",category);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialogfragment_newobject, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Add new object");
        dialog_title = view.findViewById(R.id.newobject_label);
        dialog_title.setText(title);

        // creating a new ObjectDataBase class and passing our context to it.
        myDataBase = new ObjectDataBase(view.getContext());

        //Access spinner from activity_newobject
        typeSpinner = (Spinner) view.findViewById(R.id.no_type_spinner);
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
        addObjectButton = (Button) view.findViewById(R.id.no_add_button);
        attribute1_editText = (EditText) view.findViewById(R.id.no_attibute1_edit);
        attribute2_editText = (EditText) view.findViewById(R.id.no_attibute2_edit);
        //Button click listener
        addObjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!getArguments().getString("category").equals("All")){
                    objectCategory_DB = getArguments().getString("category");
                }else{
                    objectCategory_DB = "Media"; //TO BE CHANGED
                }
                objectType_DB = typeSpinner.getSelectedItem().toString();
                objectAtt1_BD = attribute1_editText.getText().toString();
                objectAtt2_BD = attribute2_editText.getText().toString();

                // validating if the text fields are empty or not.
                if (objectAtt1_BD.isEmpty() && objectAtt2_BD.isEmpty()) {
                    Toast.makeText(view.getContext(), "Please enter all the data", Toast.LENGTH_SHORT).show();
                    return;
                }
                // check if object already exists in database
                if (myDataBase.checkIfObjectExists(getArguments().getString("category"),objectType_DB, objectAtt1_BD, objectAtt2_BD)){
                    Toast.makeText(view.getContext(), "Object already exists", Toast.LENGTH_SHORT).show();
                }else{

                    // on below line we are calling a method to add new
                    // course to sqlite data and pass all our values to it.
                    myDataBase.addNewObject(objectCategory_DB,objectType_DB, objectAtt1_BD, objectAtt2_BD);

                    // after adding the data we are displaying a toast message.
                    Toast.makeText(view.getContext(), "Object has been added", Toast.LENGTH_SHORT).show();
                    attribute1_editText.setText("");
                    attribute2_editText.setText("");

                    //update fragments with new added object
                    List<Fragment> updFrag = getParentFragmentManager().getFragments();
                    for(Fragment fr : updFrag){
                        if(fr.getClass().equals(PlaceholderFragment.class)){
                            getParentFragmentManager().beginTransaction().detach(fr).commit();
                            getParentFragmentManager().beginTransaction().attach(fr).commit();
                        }
                    }
                }
            }
        });

    }
}