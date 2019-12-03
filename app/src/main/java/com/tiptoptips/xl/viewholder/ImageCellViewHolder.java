package com.tiptoptips.xl.viewholder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.viewmodel.TableViewModel;

public class ImageCellViewHolder extends AbstractViewHolder {
    @NonNull
    public final ImageView cell_image;

    public ImageCellViewHolder(@NonNull View itemView) {
        super(itemView);
        cell_image = itemView.findViewById(R.id.cell_image);
    }

    public void setData(Object data) {

        int mood = (int) data;
        int moodDrawable = mood == TableViewModel.HAPPY ? R.drawable.ic_happy : R.drawable.ic_sad;

        cell_image.setImageResource(moodDrawable);
    }
}