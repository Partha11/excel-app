package com.tiptoptips.xl.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.model.Cell;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageCellViewHolder extends AbstractViewHolder {

    @BindView(R.id.cell_image)
    AppCompatImageView cell_image;

    public ImageCellViewHolder(@NonNull View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(Cell data) {

        String url = Objects.requireNonNull(data.getData()).toString();

        if (!TextUtils.isEmpty(url)) {

            Glide.with(cell_image.getRootView())
                    .load(url)
                    .into(cell_image);
        }
    }
}