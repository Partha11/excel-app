package com.tiptoptips.xl.viewholder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.tiptoptips.xl.R;

public class ImageCellViewHolder extends AbstractViewHolder {

    @NonNull
    final ImageView cell_image;

    public ImageCellViewHolder(@NonNull View itemView) {

        super(itemView);
        cell_image = itemView.findViewById(R.id.cell_image);
    }

    public void setData(Object data) {

        String url = "https://tiptoptips.info/android/resultsys/uploads/temp.jpg";

        Glide.with(cell_image.getRootView())
                .load(url)
                .into(cell_image);
    }
}