package com.johnwebi.webiimageloaderexample.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.johnwebi.webiimageloader.Utils.WebiImageLoader;
import com.johnwebi.webiimageloaderexample.Activities.OnClickPhotoActivity;
import com.johnwebi.webiimageloaderexample.Models.Photo;
import com.johnwebi.webiimageloaderexample.Models.Urls;
import com.johnwebi.webiimageloaderexample.R;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private List<Photo> photoList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public PhotoAdapter(List<Photo> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder photoViewHolder, int i) {
        final PhotoViewHolder holder = photoViewHolder;
        final Photo model = photoList.get(i);

        new WebiImageLoader.Builder()
                .from(context)
                .load(model.getUrls().getFull())
                .placeHolder(R.drawable.placeholder_img)
                .into(holder.imageView);
                //.build();


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), OnClickPhotoActivity.class);
                myIntent.putExtra("name", model.getUser().getName());
                myIntent.putExtra("created_at", model.getCreatedAt());
                myIntent.putExtra("likes", model.getLikes());
                myIntent.putExtra("url", model.getUrls().getFull());
                myIntent.putExtra("profile_url", model.getUser().getProfileImage().getLarge());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(myIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, desc, author, published_at, source, time;
        ImageView imageView;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;

        public PhotoViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.img);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
