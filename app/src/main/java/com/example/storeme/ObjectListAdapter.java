package com.example.storeme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ObjectListAdapter extends RecyclerView.Adapter<ObjectListAdapter.ViewHolder>{

    //Variables
    private ArrayList<StoreMeObject> mObjectsArrayList;
    private Context mcontext;

    //Constructor
    public ObjectListAdapter (ArrayList<StoreMeObject> objectsArrayList, Context context){
        this.mObjectsArrayList = objectsArrayList;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_objectlist, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position){
        StoreMeObject object = mObjectsArrayList.get(position);
        holder.objectTypeCard.setText(object.getObjectType());
        holder.objectAtt1Card.setText(object.getObjectAttribute1());
        holder.objectAtt2Card.setText(object.getObjectAttribute2());
    }

    @Override
    public int getItemCount(){
        return mObjectsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Variables
        private TextView objectTypeCard, objectAtt1Card, objectAtt2Card;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            objectTypeCard = itemView.findViewById(R.id.cardObjectType);
            objectAtt1Card = itemView.findViewById(R.id.cardObjectAttribute1);
            objectAtt2Card = itemView.findViewById(R.id.cardObjectAttribute2);
        }

    }
}
