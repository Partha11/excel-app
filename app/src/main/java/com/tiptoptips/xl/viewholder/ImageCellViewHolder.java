package com.tiptoptips.xl.viewholder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.tiptoptips.xl.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageCellViewHolder extends AbstractViewHolder {

    @BindView(R.id.cell_image)
    AppCompatImageView cell_image;

    public ImageCellViewHolder(@NonNull View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(Object data) {

        String url = "https://tiptoptips.info/android/resultsys/uploads/temp.jpg";

        Glide.with(cell_image.getRootView())
                .load(url)
                .into(cell_image);
    }
}