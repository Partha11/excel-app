package com.tiptoptips.xl.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.tiptoptips.xl.R;
import com.tiptoptips.xl.viewmodel.TableViewModel;

public class GenderCellViewHolder extends ImageCellViewHolder {

    public GenderCellViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data) {

        int gender = (int) data;
        int genderDrawable = gender == TableViewModel.BOY ? R.drawable.ic_male : R.drawable.ic_female;

        cell_image.setImageResource(genderDrawable);
    }
}
