package com.abdulrohman.flickerapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class GetFlickerRecyclerView extends RecyclerView.Adapter<GetFlickerRecyclerView.ViewHolderFlicker> {
    private static final String TAG = "GetFlickerRecyclerView";
    private Context context;
    List<Photo> photoList;
    ImageView imageViewBrows;
    TextView photoTitel;
    View layoutInflater;
    interface  IPreventWord {
        boolean prventWord( String word);
    }

    public GetFlickerRecyclerView(Context context, List<Photo> photos) {
        this.context = context;
        this.photoList = photos;
        Log.i(TAG, "GetFlickerRecyclerView: "+this.photoList);

    }


     public  Photo getPhoto (int position){
       return photoList.get(position);
     }
    @NonNull
    @Override
    public ViewHolderFlicker onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.browse, viewGroup,false);
        ViewHolderFlicker viewHolderFlicker = new ViewHolderFlicker(layoutInflater);
        return viewHolderFlicker;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFlicker viewHolderFlicker, int position) {

        if((photoList ==null) || (photoList.size()==0)){
            viewHolderFlicker.photoImg.setImageResource(R.drawable.place);
            viewHolderFlicker.photoTitel.setText(context.getString(R.string.Empty_search));
        }else {
            Picasso.get().load(photoList.get(position).getLink()).into(viewHolderFlicker.photoImg);
            viewHolderFlicker.photoTitel.setText(photoList.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return photoList != null ? photoList.size():1;
    }

    public void loadNewData(List<Photo> photoList){
        Log.i(TAG, "loadNewData:  start ");
        this.photoList=photoList;
        notifyDataSetChanged();
        Log.i(TAG, "loadNewData: end");
    }

    public class ViewHolderFlicker extends RecyclerView.ViewHolder {
        private TextView photoTitel;
        private ImageView photoImg;

        public ViewHolderFlicker(@NonNull View itemView) {
            super(itemView);
            photoTitel = itemView.findViewById(R.id.phoBrowsTitel);
            photoImg= itemView.findViewById(R.id.imgBrows);
        }
    }
}
