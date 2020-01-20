package com.tiptoptips.xl.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.model.Cell;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckboxViewHolder extends AbstractViewHolder {

    @BindView(R.id.cell_checkbox)
    AppCompatCheckBox checkBox;

    public CheckboxViewHolder(@NonNull View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(@NonNull Cell cell) {

        String object = Objects.requireNonNull(cell.getData()).toString();
        boolean value = object.equals("true");

        if (value) {

            checkBox.setChecked(true);

        } else {

            checkBox.setChecked(false);
        }
    }
}
