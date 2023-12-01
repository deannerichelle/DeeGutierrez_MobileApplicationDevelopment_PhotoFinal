package com.zybooks.deegutierrez_photofinal;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context context;
    private List<PhotoModel> photoList;

    public PhotoAdapter(Context context, List<PhotoModel> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhotoModel photoModel = photoList.get(position);

        // Convert byte array to Bitmap
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(
                photoModel.getPhotoData(), 0, photoModel.getPhotoData().length));
        holder.textViewTag.setText(photoModel.getPhotoTag());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTag = itemView.findViewById(R.id.textViewTag);
        }
    }
    public void setData(List<PhotoModel> newData) {
        this.photoList = newData;
    }
}

