package com.example.storeme;

import android.os.Bundle;

import com.example.storeme.object.NewObjectDialogFragment;
import com.example.storeme.object.ObjectDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.storeme.ui.main.SectionsPagerAdapter;
import com.example.storeme.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ObjectDataBase myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;
        // creating a new ObjectDataBase class and passing our context to it.
        myDataBase = new ObjectDataBase(this);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getSupportFragmentManager();
                String dialog_name = null;
                if(tabs.getSelectedTabPosition()==1){
                    dialog_name = "Add new media";
                }else{
                    dialog_name = "Add new object";
                }
                NewObjectDialogFragment addDialogFragment = NewObjectDialogFragment.newInstance(dialog_name);
                addDialogFragment.show(fm, "dialog_newobject");
            }
        });
    }
}