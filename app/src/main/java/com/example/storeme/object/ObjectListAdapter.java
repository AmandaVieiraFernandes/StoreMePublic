package com.example.storeme.object;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeme.R;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_object, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position){
        StoreMeObject object = mObjectsArrayList.get(position);
        holder.objectTypeCard.setText(object.getObjectType());
        holder.objectAtt1Card.setText(object.getObjectAttribute1());
        holder.objectAtt2Card.setText(object.getObjectAttribute2());

        CardView card = (CardView) holder.itemView.findViewById(R.id.cardview);

        if (position%2!=0){
            card.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.StoreMe_pinkcard));
        }

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

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int id = getAdapterPosition();
                    FragmentActivity activity = (FragmentActivity)(mcontext);
                    FragmentManager fm = activity.getSupportFragmentManager();
                    StoreMeObject obj = mObjectsArrayList.get(id);
                    ViewObjectDialogFragment viewDialogFragment = ViewObjectDialogFragment.newInstance(obj);
                    viewDialogFragment.show(fm, "dialog_viewobject");
                }
            });
        }

    }
}
