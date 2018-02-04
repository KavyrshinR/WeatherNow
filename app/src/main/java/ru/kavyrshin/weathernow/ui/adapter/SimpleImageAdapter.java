package ru.kavyrshin.weathernow.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ru.kavyrshin.weathernow.R;

public class SimpleImageAdapter extends RecyclerView.Adapter<SimpleImageAdapter.SimpleImageViewHolder> {

    private int[] imageResourceIds;

    public SimpleImageAdapter(int[] imageResourceIds) {
        this.imageResourceIds = imageResourceIds;
    }

    @Override
    public SimpleImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View imageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_image, parent, false);
        return new SimpleImageViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(SimpleImageViewHolder holder, int position) {
        holder.imageView.setImageResource(imageResourceIds[position]);
    }

    @Override
    public int getItemCount() {
        return imageResourceIds.length;
    }

    static class SimpleImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public SimpleImageViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView;
        }
    }
}