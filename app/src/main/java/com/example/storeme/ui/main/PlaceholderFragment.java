package com.example.storeme.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeme.R;
import com.example.storeme.object.ObjectDataBase;
import com.example.storeme.object.ObjectListAdapter;
import com.example.storeme.object.StoreMeObject;
import com.example.storeme.databinding.FragmentMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    // This event is triggered soon after onCreateView()
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstance){
        ObjectDataBase myDataBase = new ObjectDataBase(view.getContext());

        String cat = getResources().getStringArray(R.array.tabs_string)[getArguments().getInt(ARG_SECTION_NUMBER)-1];
        ArrayList<StoreMeObject> objectList = myDataBase.getObjectsByCategory(cat);

        ObjectListAdapter objectListAdapter = new ObjectListAdapter(objectList,view.getContext());
        RecyclerView objectRecyclerView = binding.objectList;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        objectRecyclerView.setLayoutManager(linearLayoutManager);

        objectRecyclerView.setAdapter(objectListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}